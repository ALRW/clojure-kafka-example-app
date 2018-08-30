(ns kafka-example.system
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.logging :as log])
  (:import org.apache.kafka.common.serialization.Serdes
           [org.apache.kafka.streams.kstream ValueMapper]
           [org.apache.kafka.streams StreamsConfig KafkaStreams StreamsBuilder]))

(def props
  (let [properties (java.util.Properties.)]
    (.put properties StreamsConfig/APPLICATION_ID_CONFIG "kafka-example")
    (.put properties StreamsConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
    (.put properties StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG  (.getName (.getClass (Serdes/String))))
    (.put properties StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG  (.getName (.getClass (Serdes/String))))
    properties))

(defn topology [input-topic output-topic]
  (let [builder (StreamsBuilder.)]
    (-> (.stream builder input-topic)
        (.mapValues (reify ValueMapper
                      (apply [_ v]
                        (log/info v)
                        (str "---> " v))))
        (.to output-topic))
    (.build builder)))

(defrecord KafkaTransformer [stream-props input-topic output-topic]
  component/Lifecycle
  (start [component]
    (log/info "Starting KafkaTransformer for" input-topic "and" output-topic)
    (let [stream (KafkaStreams. (topology input-topic output-topic) stream-props)]
      (assoc component :stream stream)))
  (stop [component]
    (log/info "Stopping KafkaTransformer")
    (when-let [stream (:stream component)]
      (.close stream))
    (assoc component :stream nil)))

(defn base-system [config]
  (let [{:keys [input-topic output-topic]} config]
    (component/system-map
      :transformer (->KafkaTransformer props input-topic output-topic))))
