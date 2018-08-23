(ns kafka-example.core
  (:require [kafka-example.system :refer [props]])
  (:import org.apache.kafka.common.serialization.Serdes
           [org.apache.kafka.streams.kstream ValueMapper]
           [org.apache.kafka.streams StreamsConfig KafkaStreams StreamsBuilder])
  (:gen-class))

(defn topology [input-topic output-topic]
  (let [builder (StreamsBuilder.)]
    (-> (.stream builder input-topic)
        (.mapValues (reify ValueMapper
                      (apply [_ v] 
                        (println "INFO " v)
                        (str "---> " v))))
        (.to output-topic))
    (.build builder)))

(defn -main
  [& args]

  (def stream (KafkaStreams. (topology (first args) (second args)) props))

  (.start stream))
