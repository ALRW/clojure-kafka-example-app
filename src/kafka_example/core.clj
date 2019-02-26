(ns kafka-example.core
  (:require [kafka-example.system :refer [base-system]]
            [com.stuartsierra.component :as component]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn -main
  [& args]
  (log/info "Kafka-example App starting")
  (-> (base-system {:input-topic (first args) :output-topic (second args)})
      component/start
      :transformer
      :stream
      .start))
