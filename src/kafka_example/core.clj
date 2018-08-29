(ns kafka-example.core
  (:require [kafka-example.system :refer [base-system]]
            [com.stuartsierra.component :as component])
  (:import org.apache.kafka.common.serialization.Serdes
           [org.apache.kafka.streams.kstream ValueMapper]
           [org.apache.kafka.streams StreamsConfig KafkaStreams StreamsBuilder])
  (:gen-class))

(defn -main
  [& args]
  (let [system (component/start (base-system {:input-topic (first args) :output-topic (second args)}))
        stream (:stream system)]
    (.start stream)))
