(ns persistence.database
  (:require [honeysql.core :as sql]
            [honeysql.helpers :refer :all]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :as psqlh]
            [persistence.connection :as connection]))

(defn get-pizza [repository name]
  (-> (select :*)
      (from :pizza)
      (where [:= :name name])
      sql/format
      (->> (connection/query (:connection repository)))))

(defn find-toppings-by-pizza-name [repository name]
  (-> (select :topping_name)
      (from :toppings)
      (where [:= :pizza_name name])
      sql/format
      (->> (connection/query (:connection repository)))))

(defn create-pizza [repository name description]
  (-> (insert-into :pizza)
      (columns :name :description)
      (values [[name description]])
      sql/format
      (->> (connection/execute! (:connection repository)))))

(defn create-toppings [repository name toppings]
  (-> (insert-into :toppings)
      (columns :pizza_name :topping_name)
      (values (map list (repeat name) toppings))
      sql/format
      (->> (connection/execute! (:connection repository)))))

(defrecord Database [connection])

(defn new-database []
  (map->Database {}))
