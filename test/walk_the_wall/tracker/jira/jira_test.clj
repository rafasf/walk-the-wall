(ns walk-the-wall.tracker.jira.jira-test
  (:require [clojure.test :refer :all]
            [walk-the-wall.tracker.jira.client :refer :all]
            [walk-the-wall.tracker.jira.jira :refer :all]))

(def an-issue
  {:key "PROJ-123"
   :fields {:assignee {:displayName "Bob Jane"}
            :status {:name "In Development"}
            :custom_epic "EPC-100"
            :summary "Story summary here"}})

(deftest stories
  (testing "mapping between issue and story"
    (is (= {:id "PROJ-123"
            :title "Story summary here"
            :assignee "Bob Jane"
            :feature {:id "EPC-100"}
            :status "In Development"}
           (story-from an-issue :custom_epic)))))
