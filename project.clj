(defproject kafka-example "0.1.0-SNAPSHOT"
  :description "An example app that is designed to show how to use clojure, component, kafka and spec"
  :url "https://github.com/ALRW/clojure-kafka-example-app"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot kafka-example.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
