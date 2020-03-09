(ns middleware
  (:require [persistence.database :as db]))

(defn get-node-no [system id]
  (db/find-item-by-id (:database system) {:id id}))

(defn insert-item [system item]
  (db/insert-item (:database system) {:name- item}))

(defrecord Middleware [database])

(defn new-middleware []
  (map->Middleware {}))
