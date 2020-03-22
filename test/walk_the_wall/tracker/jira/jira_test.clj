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
               :status "In Development"
               :view-url "https://jira.com/browse/PROJ-123"}
              {:id "PROJ-123"
               :title "Story summary here"
               :assignee "Bob Jane"
               :feature {:id "EPC-100"}
               :status "In Development"
               :view-url "https://jira.com/browse/PROJ-123"}]
             (stories {:status-priority {"in development" 0}
                       :base-url "https://jira.com"})))))

  (testing "returns ordered by status"
    (let [stories [{:id "A" :status "In Development"}
                   {:id "B" :status "To do"}
                   {:id "C" :status "In testing"}]]
      (is (= [{:id "C" :status "In testing"}
              {:id "A" :status "In Development"}
              {:id "B" :status "To do"}]
             (ordered {"in testing" 0 "in development" 1 "to do" 2} stories))))))
