(ns kafka-example.system
  (:require [com.stuartsierra.component :as component])
  (:import org.apache.kafka.common.serialization.Serdes
           [org.apache.kafka.streams StreamsConfig]))

(def props
  (let [properties (java.util.Properties.)]
    (.put properties StreamsConfig/APPLICATION_ID_CONFIG "kafka-example")
    (.put properties StreamsConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
    (.put properties StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG  (.getName (.getClass (Serdes/String))))
    (.put properties StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG  (.getName (.getClass (Serdes/String))))
    properties))
