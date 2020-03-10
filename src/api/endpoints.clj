(ns api.endpoints
  (:require [middleware :as middleware]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params]
            [compojure.api.sweet :refer :all]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.route :as route]))

(defn app-routes [middleware]
  (api
   (GET "/" [] "<h1>Hello You World</h1>")
   (GET "/:id" []
     :path-params [id]
     (middleware/get-node-no middleware (Integer. id)))
   (POST "/" []
     :form-params [todo]
     (middleware/insert-item middleware todo))
   (route/not-found "<h1>Page not found</h1>")))

(defrecord Handler [middleware]
  component/Lifecycle
  (start [component]
    (if (:handler component)
      component
      (assoc component :handler (app-routes middleware))))

  (stop [component]
    (dissoc component :handler)))

(defn new-handler [] (map->Handler {}))
