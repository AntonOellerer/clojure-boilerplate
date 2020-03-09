(ns user
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.namespace.repl :refer [refresh]]
            [system :as app]))

(def system nil)

(defn init []
  (alter-var-root #'system
                  (constantly (app/system {:host "127.0.0.1" :dbname "ase_test" :port 5432 :user "postgres" :password "12345" :api-config {:port 8080}}))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'user/go))
