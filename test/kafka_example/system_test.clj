(ns kafka-example.system-test
  (:require [clojure.test :refer :all]
            [kafka-example.system :refer [props topology]])
  (:import org.apache.kafka.common.serialization.Serdes
           [org.apache.kafka.streams StreamsConfig TopologyTestDriver]
           [org.apache.kafka.streams.test ConsumerRecordFactory]))

(deftest test-topology
  (testing "Test that the Kafka Topology is correctly built"
    (let [topic-in "test-input"
          topic-out "test-output"
          p (props "test-app" "ip:port")
          t (topology topic-in topic-out)
          test-driver (TopologyTestDriver. t p)
          serializer (.serializer (. Serdes String))
          deserializer (.deserializer (. Serdes String))
          factory (ConsumerRecordFactory. serializer serializer)
          input "This is the test string"
          output "---> This is the test string"]
      (.pipeInput test-driver (.create factory topic-in "key" input))
      (is (= output (.value (.readOutput test-driver topic-out deserializer deserializer)))))))
