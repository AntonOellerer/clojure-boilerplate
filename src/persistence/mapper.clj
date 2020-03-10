(ns persistence.mapper
  (:require [persistence.database :as db]
            [com.stuartsierra.component :as component]))

(defn get-pizza [system name]
  (let [pizza (first (db/get-pizza (:database system) name))]
    (when (seq pizza)
      (assoc pizza :toppings (mapcat vals (db/find-toppings-by-pizza-name (:database system) name))))))

(defn create-pizza [system name pizza]
  (let [existing-pizza (db/get-pizza (:database system) name)]
    (when (empty? existing-pizza)
      (db/create-pizza (:database system) name (:description pizza))
      (db/create-toppings (:database system) name (:toppings pizza)))))

(defrecord Mapper [database])

(defn new-mapper []
  (map->Mapper {}))
