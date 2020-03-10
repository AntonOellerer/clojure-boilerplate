(ns api.endpoints
  (:require [middleware :as middleware]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params]
            [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.route :as route]))

(s/defschema Pizza
  {(s/optional-key :name) s/Str
   :description s/Str
   :toppings [s/Str]})

(defn print-middleware [handler]
  (fn [request]
    (let [response (handler request)]
      (println response)
      response)))

(defn app-routes [middleware]
  (api
   (GET "/" [] "<h1>Hello You World</h1>")
   (GET "/:name" []
     :path-params [name :- String]
     :return Pizza
     :middleware [print-middleware]
     (if-let [pizza (middleware/get-pizza middleware name)]
        (ok pizza)
        (not-found)))
   (POST "/:name" []
     :path-params [name :- String]
     :body [pizza Pizza]
     (if (seq (middleware/create-pizza middleware name pizza)) (created) (conflict)))
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
