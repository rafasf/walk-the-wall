(ns walk-the-wall.core
  (:use compojure.core)
  (:require [clojure.pprint :as pp]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [compojure.handler :as handler]
            [walk-the-wall.tracker.jira.jira :as jira]
            [walk-the-wall.available-projects :refer [available-boards-in]]))

(defn env [variable]
  (System/getenv variable))

(def configs
  (->> (io/resource "wall_config.edn")
       slurp
       edn/read-string
       (map #(update % :token env))))

(defroutes all-routes
  (GET "/" [] {:status 200
               :headers {"Content-Type" "text/html"}
               :body (available-boards-in configs)}))

(defn -main []
  (jetty/run-jetty
   (-> (handler/site all-routes)
       (wrap-resource "public")
       (wrap-content-type)
       (wrap-not-modified))
   {:port 3000}))
