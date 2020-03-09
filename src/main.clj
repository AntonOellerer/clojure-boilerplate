(ns main
  (:require [persistence.database :as db]
            [com.stuartsierra.component :as component]))

(defn -main []
  (println "Hello world"))

(defn get-node-no [system id]
  (db/find-item-by-id (:database system) {:id id}))

(defn insert-item [system item]
  (db/insert-item (:database system) {:name- item}))

(defrecord Main [database])

(defn new-main []
  (map->Main {}))
