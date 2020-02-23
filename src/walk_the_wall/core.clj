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
            [walk-the-wall.config :refer [load-from]]
            [walk-the-wall.tracker.jira.jira :as jira]
            [walk-the-wall.available-projects :refer [available-boards-in]]
            [walk-the-wall.wall :refer [wall-for]]))

(def configs (load-from "wall_config.edn"))

(defroutes all-routes
  (GET "/" [] {:status 200
               :headers {"Content-Type" "text/html"}
               :body (available-boards-in configs)})
  (GET "/projects" [board] {:status 200
                            :headers {"Content-Type" "text/html"}
                            :body (wall-for (jira/stories (->> configs
                                                              (filter #(= board (get % :name)))
                                                              first)))}))

(defn -main []
  (jetty/run-jetty
   (-> (handler/site all-routes)
       (wrap-resource "public")
       (wrap-content-type)
       (wrap-not-modified))
   {:port 3000}))
