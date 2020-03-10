(ns main
  (:require [com.stuartsierra.component :as component]
            [system :as app]))

(defn -main []
  (component/start (app/system {:db-config {:host "127.0.0.1" :dbname "ase_test" :port 5432 :user "postgres" :password "12345"} :api-config {:port 8080}})))

