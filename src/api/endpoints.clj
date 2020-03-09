(ns api.endpoints
  (:require [middleware :as middleware]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params]
            [compojure.api.sweet :refer :all]
            [compojure.route :as route]))

(defn app-routes [middleware]
    (api
      (GET "/" [] "<h1>Hello World</h1>")
      (GET "/:id" [] 
           :path-params [id]
           (middleware/get-node-no middleware (Integer. id)))
      (POST "/" [] 
            :form-params [todo]
            (middleware/insert-item middleware todo))
      (route/not-found "<h1>Page not found</h1>")))


(defrecord WebServer [config http-server middleware]
  component/Lifecycle 
  (start [this]
    (assoc this :http-server (jetty/run-jetty (app-routes middleware) config)))

  (stop [this]
    this))

(defn new-web-server [api-config] (map->WebServer {:config api-config}))
