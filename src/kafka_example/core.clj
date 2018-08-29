(ns kafka-example.core
  (:require [kafka-example.system :refer [base-system]]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defn -main
  [& args]
  (let [system (component/start (base-system {:input-topic (first args) :output-topic (second args)}))
        stream (-> system :transformer :stream)]
    (.start stream)))
