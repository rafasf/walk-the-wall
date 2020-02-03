(ns walk-the-wall.tracker.jira.jira)

(defn story-from [issue epic-field-name]
  (let [{id :key, {epic-number epic-field-name
                   :keys [summary
                          assignee
                          status]} :fields} issue]
    {:title summary
     :assignee (get assignee :displayName "nobody")
     :feature {:id epic-number}
     :status (status :name)
     :id id}))
