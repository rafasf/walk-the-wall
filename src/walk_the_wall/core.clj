(ns walk-the-wall.core
  (:use compojure.core)
  (:require [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [compojure.handler :as handler]
            [walk-the-wall.config :refer [load-from]]
            [walk-the-wall.tracker.jira.jira :as jira]
            [walk-the-wall.available-projects :refer [available-boards-in]]
            [walk-the-wall.wall :refer [wall-for]])
  (:gen-class))

(defn load-configs []
  (load-from (System/getenv "CONFIG_PATH")))


(defn define-routes [configs]
  (defroutes all-routes
    (GET "/" [] {:status 200
                 :headers {"Content-Type" "text/html"}
                 :body (available-boards-in configs)})
    (GET "/projects" [board] {:status 200
                              :headers {"Content-Type" "text/html"}
                              :body (wall-for (jira/stories (->> configs
                                                                (filter #(= board (get % :name)))
                                                                first)))})))

(defn -main []
  (jetty/run-jetty
   (-> (handler/site (define-routes (load-configs)))
       (wrap-resource "public")
       (wrap-content-type)
       (wrap-not-modified))
   {:port 3000}))
