(ns walk-the-wall.tracker.jira.jira
  (:require [clojure.string :refer [trim lower-case]]
            [walk-the-wall.tracker.jira.client :as client]))

(defn- issue-to-story [epic-field-name issue]
  (let [{id :key, {epic-number epic-field-name
                   :keys [summary
                          assignee
                          status]} :fields} issue]
    {:title summary
     :assignee (get assignee :displayName "nobody")
     :feature {:id epic-number}
     :status (status :name)
     :id id}))

(defn- status-name [priorities story]
  (let [name (story :status)]
    (get priorities (lower-case (trim name)))))

(defn- view-url [base-url story]
  (assoc story :view-url (str base-url "/browse/" (story :id))))

(defn ordered-by-status [status-priority stories]
  (sort-by (partial status-name status-priority) stories))

(defn to-story [config]
  (let [story-from-issue (partial issue-to-story (config :epic-field-name))
        with-view-url (partial view-url (config :base-url))]
    (comp with-view-url story-from-issue)))

(defn stories [config]
  (let [http-client-config {:base-url (config :base-url)
                            :headers  {"Authorization" (str "Basic " (config :token))}}
        epic-field-name (client/epic-field-name http-client-config)
        criteria (config :criteria)
        full-config (assoc config :epic-field-name epic-field-name)]
    (->> (client/search http-client-config criteria)
         (map (to-story full-config))
         (ordered-by-status (config :status-priority)))))

