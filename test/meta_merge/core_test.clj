(ns meta-merge.core-test
  (:require [clojure.test :refer :all]
            [meta-merge.core :refer :all]))

(deftest test-meta-merge
  (testing "simple merge"
    (is (= (meta-merge {:a 1 :b 2} {:b 3 :c 4})
           {:a 1 :b 3 :c 4})))

  (testing "inner map merge"
    (is (= (meta-merge {:a {:b 1 :c 2}} {:a {:c 3}})
           {:a {:b 1 :c 3}})))

  (testing "inner set merge"
    (is (= (meta-merge {:a #{:b :c}} {:a #{:c :d}})
           {:a #{:b :c :d}})))

  (testing "inner vector merge"
    (is (= (meta-merge {:a [:b :c]} {:a [:d]})
           {:a [:b :c :d]})))

  (testing "meta replace"
    (is (= (meta-merge {:a [:b :c]} {:a ^:replace [:d]})
           {:a [:d]})))

  (testing "meta displace"
    (is (= (meta-merge {:a [:b :c]} {:a ^:displace [:d]})
           {:a [:b :c]})))

  (testing "meta prepend"
    (is (= (meta-merge {:a [:b :c]} {:a ^:prepend [:d]})
           {:a [:d :b :c]})))

  (testing "deep inner merge"
    (is (= (meta-merge {:a {:b {:c [:d]}}} {:a {:b {:c [:e] :f :g}}})
           {:a {:b {:c [:d :e] :f :g}}}))))
