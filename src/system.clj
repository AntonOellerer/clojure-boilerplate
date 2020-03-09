(ns system
  (:require [com.stuartsierra.component :as component]
            [main :as main]
            [api.endpoints :as endpoints]
            [persistence.database :as db]
            [persistence.connection :as connection]))

(defn system [config-options]
  (let [{:keys [host port dbname user password api-config]} config-options]
    (component/system-map
      :connection (connection/new-connection {:host host :port port :dbname dbname :user user :password password})
      :database (component/using (db/new-database) {:connection :connection})
      :middleware (component/using
             (main/new-main)
             {:database :database})
      :web-server (component/using (endpoints/new-web-server api-config) {:middleware :middleware}))))
