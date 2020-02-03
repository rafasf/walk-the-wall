(ns walk-the-wall.tracker.jira.client
  (:require [org.httpkit.client :as client]
            [cheshire.core :refer [parse-string]]))

(defn to-issue [response]
  (-> response :body (parse-string true) :issues))

(defn search [client-config criteria fields]
  (let [url (str (client-config :base-url) "/search")]
    (-> @(client/get url
                     {:headers (client-config :headers)
                      :query-params {"jql" criteria
                                     "fields" fields}
                      :accept :json})
        to-issue)))
