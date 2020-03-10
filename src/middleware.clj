(ns middleware
  (:require [persistence.mapper :as mapper]))

(defn get-pizza [system name]
  (let [pizza (mapper/get-pizza (:mapper system) name)]
    (println "Pizza: " pizza)
    pizza))

(defn create-pizza [system name pizza]
  (mapper/create-pizza (:mapper system) name pizza))

(defrecord Middleware [mapper])

(defn new-middleware []
  (map->Middleware {}))
