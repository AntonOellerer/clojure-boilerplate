(ns persistence.database
  (:require [honeysql.core :as sql]
            [honeysql.helpers :refer :all]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :as psqlh]
            [persistence.connection :as connection]))

(defn find-item-by-id [repository {:keys [id]}]
  (-> (select :*)
      (from :items)
      (where [:= :itemid id])
      sql/format
      (->> (connection/query (:connection repository)))))

(defn insert-item [repository {:keys [name-]}]
  (println repository)
  (-> (insert-into :items)
      (columns :name)
      (values [[name-]])
      sql/format
      (->> (connection/execute! (:connection repository)))))

(defrecord Database [connection])

(defn new-database []
  (map->Database {}))
