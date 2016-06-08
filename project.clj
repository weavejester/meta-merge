(defproject meta-merge "1.0.0"
  :description "A standalone implementation of Leiningen's meta-merge function"
  :url "https://github.com/weavejester/meta-merge"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-doo "0.1.6"]]
  :aliases {"test-cljs" ["doo" "rhino" "test" "once"]
            "test-all" ["do" ["test"] ["test-cljs"]]}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :profiles {:provided
             {:dependencies [[org.clojure/clojurescript "1.7.189"]]}
             :test
             {:dependencies [[org.mozilla/rhino "1.7.7"]]}}
  :doo {:paths {:rhino "lein run -m org.mozilla.javascript.tools.shell.Main"}}
  :cljsbuild {:builds
              {:test {:source-paths ["src" "test"]
                      :compiler {:output-to "target/test-runner.js"
                                 :output-dir "target"
                                 :main meta-merge.test-runner
                                 :optimizations :simple}}}})
