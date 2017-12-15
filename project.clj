(defproject musetrap-clj "0.1.0-SNAPSHOT"
  :description "Tool to provide prompts to ignite your inspiration"
  :url "https://github.com/excentris/musetrap-clj"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot musetrap-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
