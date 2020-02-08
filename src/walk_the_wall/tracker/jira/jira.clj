(ns walk-the-wall.tracker.jira.jira
  (:require [walk-the-wall.tracker.jira.client :as client]))

(defn- to-story [epic-field-name issue]
  (let [{id :key, {epic-number epic-field-name
                   :keys [summary
                          assignee
                          status]} :fields} issue]
    {:title summary
     :assignee (get assignee :displayName "nobody")
     :feature {:id epic-number}
     :status (status :name)
     :id id}))

(defn stories [config]
  (let [http-client-config {:base-url (config :base-url)
                            :headers {"Authorization" (str "Basic " (config :token))}}
        criteria (config :criteria)]
    (->> (client/search http-client-config criteria)
         (map (partial to-story :customfield_11500)))))
