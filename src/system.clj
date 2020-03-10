(ns system
  (:require [com.stuartsierra.component :as component]
            [middleware :as middleware]
            [api.endpoints :as endpoints]
            [persistence.database :as db]
            [ring.component.jetty :refer [jetty-server]]
            [persistence.connection :as connection]))

(defn system [config-options]
  (let [{:keys [db-config api-config]} config-options]
    (component/system-map
     :connection (connection/new-connection db-config)
     :database (component/using (db/new-database) {:connection :connection})
     :middleware (component/using
                  (middleware/new-middleware)
                  {:database :database})
     :handler (component/using (endpoints/new-handler) {:middleware :middleware})
     :http (component/using (jetty-server (:api-config config-options)) {:app :handler}))))