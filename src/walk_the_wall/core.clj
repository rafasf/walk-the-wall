(ns walk-the-wall.core
  (:require [clojure.pprint :as pp]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [walk-the-wall.tracker.jira.jira :as jira]))

(defn env [variable]
  (System/getenv variable))

(def configs
  (->> (io/resource "wall_config.edn")
       slurp
       edn/read-string
       (map #(update % :token env))))

(defn -main []
  (let [stories (jira/stories (first configs))]
    (pp/pprint stories)))
