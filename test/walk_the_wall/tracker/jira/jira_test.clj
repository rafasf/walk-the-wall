(ns walk-the-wall.tracker.jira.jira-test
  (:require [clojure.test :refer :all]
            [walk-the-wall.tracker.jira.client :refer :all]
            [walk-the-wall.tracker.jira.jira :refer :all]))

(def an-issue
  {:key "PROJ-123"
   :fields {:assignee {:displayName "Bob Jane"}
            :status {:name "In Development"}
            :customfield_11500 "EPC-100"
            :summary "Story summary here"}})

(deftest project-stories
  (testing "returns stories"
    (with-redefs [search (fn [cfg crit] [an-issue an-issue])]
      (is (= [{:id "PROJ-123"
               :title "Story summary here"
               :assignee "Bob Jane"
               :feature {:id "EPC-100"}
               :status "In Development"}
              {:id "PROJ-123"
               :title "Story summary here"
               :assignee "Bob Jane"
               :feature {:id "EPC-100"}
               :status "In Development"}]
             (stories {}))))))
