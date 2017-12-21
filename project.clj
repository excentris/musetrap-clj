(defproject musetrap-clj "0.1.0-SNAPSHOT"
  :plugins [[lein-ring "0.12.2"]]
  :ring {:handler musetrap-clj.core/handler}
  :description "Tool to provide prompts to ignite your inspiration"
  :url "https://github.com/excentris/musetrap-clj"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [liberator "0.15.1"]
                 [compojure "1.6.0"]
                 [ring/ring-core "1.6.3"]]
  :main ^:skip-aot musetrap-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
