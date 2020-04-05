(ns walk-the-wall.tracker.jira.client
  (:require [org.httpkit.client :as client]
            [clojure.string :refer [lower-case]]
            [cheshire.core :refer [parse-string]]))

(defn api-url [base-url operation]
  (str base-url "/rest/api/latest" operation))

(defn to-issue [response]
  (-> response :body (parse-string true) :issues))

(defn search [client-config criteria]
  (let [url (api-url (client-config :base-url) "/search")]
    (-> @(client/get url
                     {:headers (client-config :headers)
                      :query-params {"jql" criteria}
                      :accept :json})
        to-issue)))

(defn to-epic-field [all-fields]
  (let [fields (->> all-fields
                    (filter #(= "epic link" (lower-case (get % :name))))
                    (map #(get % :id)))]
    (keyword (first fields))))

(defn epic-field-name [client-config]
  (let [url (api-url (client-config :base-url) "/field")]
    (-> @(client/get url
                     {:headers (client-config :headers)
                      :accept :json})
        :body
        (parse-string true)
        to-epic-field)))
