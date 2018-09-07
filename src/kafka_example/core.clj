(ns kafka-example.core
  (:require [kafka-example.system :refer [base-system]]
            [com.stuartsierra.component :as component]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn -main
  [& args]
  (log/info "Kafka-example App starting")
  (let [system (component/start
                 (base-system
                   {:input-topic (first args)
                    :output-topic (second args)}))
        stream (-> system :transformer :stream)]
    (.start stream)))
