(ns persistence.connection 
  (:require [com.stuartsierra.component :as component]
            [clojure.java.jdbc :as jdbc]))

(defrecord Connection [dbtype host port dbname user password])

(defn new-connection [{:keys [host port dbname user password]}]
  (map->Connection {:dbtype "postgresql"
                    :host host
                    :port port
                    :dbname dbname
                    :user user
                    :password password}))

(defn query [connection query]
  (println query)
  (jdbc/query connection query))

(defn execute! [connection query]
  (println query)
  (jdbc/execute! connection query))
