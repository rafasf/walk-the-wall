(ns walk-the-wall.config
  (:require [clojure.string :refer [lower-case trim]]
            [clojure.edn :as edn]))

(defn status-priority-from [status-flow]
  (->> status-flow
       reverse
       (map (comp lower-case trim))
       (map-indexed (fn [index status] [status index]))
       (into {})))

(defn- read-from-env [variable]
  (System/getenv variable))

(defn load-from [path]
  (->> path
       slurp
       edn/read-string
       (map #(update % :token read-from-env))
       (map #(assoc % :status-priority (status-priority-from (get % :status-flow))))))
