(ns meta-merge.core
  (:require [clojure.set :as set]))

(defn- metadatable?
  [obj]
  #?(:clj (instance? clojure.lang.IObj obj)
     :cljs ((some-fn coll? symbol?) obj)))

(defn- meta*
  "Returns the metadata of an object, or nil if the object cannot hold
  metadata."
  [obj]
  (if (metadatable? obj)
    (meta obj)
    nil))

(defn- with-meta*
  "Returns an object of the same type and value as obj, with map m as its
  metadata if the object can hold metadata."
  [obj m]
  (if (metadatable? obj)
    (with-meta obj m)
    obj))

(defn- displace?
  "Returns true if the object is marked as displaceable"
  [obj]
  (-> obj meta* :displace))

(defn- replace?
  "Returns true if the object is marked as replaceable"
  [obj]
  (-> obj meta* :replace))

(defn- top-displace?
  "Returns true if the object is marked as top-displaceable"
  [obj]
  (-> obj meta* :top-displace))

(defn- different-priority?
  "Returns true if either left has a higher priority than right or vice versa."
  [left right]
  (boolean
   (or (some (some-fn nil? displace? replace?) [left right])
       (top-displace? left))))

(defn- remove-top-displace [obj]
  (if-not (top-displace? obj)
    obj
    (vary-meta obj dissoc :top-displace)))

(defn- pick-prioritized
  "Picks the highest prioritized element of left and right and merge their
  metadata."
  [left right]
  (cond (nil? left) right
        (nil? right) (remove-top-displace left)

        (top-displace? left) right

        (and (displace? left)   ;; Pick the rightmost
             (displace? right)) ;; if both are marked as displaceable
        (with-meta* right
          (merge (meta* left) (meta* right)))

        (and (replace? left)    ;; Pick the rightmost
             (replace? right))  ;; if both are marked as replaceable
        (with-meta* right
          (merge (meta* left) (meta* right)))

        (or (displace? left)
            (replace? right))
        (with-meta* right
          (merge (-> left meta* (dissoc :displace))
                 (-> right meta* (dissoc :replace))))

        (or (replace? left)
            (displace? right))
        (with-meta* left
          (merge (-> right meta* (dissoc :displace))
                 (-> left meta* (dissoc :replace))))))

(defn meta-merge
  "Recursively merge values based on the information in their metadata."
  ([] {})
  ([left] left)
  ([left right]
   (cond (different-priority? left right)
         (pick-prioritized left right)

         (and (map? left) (map? right))
         (merge-with meta-merge left right)

         (and (set? left) (set? right))
         (set/union right left)

         (and (coll? left) (coll? right))
         (if (or (-> left meta :prepend)
                 (-> right meta :prepend))
           (-> (into (empty left) (concat right left))
             (with-meta (merge (meta left)
                               (select-keys (meta right) [:displace]))))
           (into (empty left) (concat left right)))

         :else right))
  ([left right & more]
   (reduce meta-merge left (cons right more))))
