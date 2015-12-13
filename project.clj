(defproject meta-merge "0.1.1"
  :description "A standalone implementation of Leiningen's meta-merge function"
  :url "https://github.com/weavejester/meta-merge"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.189"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-doo "0.1.6"]]
  :aliases {"test-all" ["do" "clean," "test," "doo" "phantom" "test" "once"]}
  :profiles {:1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}}
  :cljsbuild {:builds
              {:test {:source-paths ["src" "test"]
                      :compiler {:output-to "target/unit-test.js"
                                 :main 'meta-merge.runner
                                 :optimizations :simple}}}})
