(defproject meta-merge "0.1.1"
  :description "A standalone implementation of Leiningen's meta-merge function"
  :url "https://github.com/weavejester/meta-merge"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :aliases {"test-all" ["with-profile" "default:+1.6" "test"]}
  :profiles {:1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}})
