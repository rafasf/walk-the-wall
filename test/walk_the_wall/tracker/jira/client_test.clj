(ns walk-the-wall.tracker.jira.client-test
  (:use org.httpkit.fake)
  (:require [clojure.test :refer :all]
            [org.httpkit.client :as http]
            [cheshire.core :refer [generate-string]]
            [walk-the-wall.tracker.jira.client :refer :all]))

(def jira-response
  "{ \"issues\": [ { \"fields\": { \"assignee\": { \"displayName\": \"person\" }, \"status\": { \"name\": \"testing\" }, \"summary\": \"summary\" } } ] }")

(def http-client-config
  {:base-url "http://jira.local/rest/api/latest"
   :headers {"Authorization" "Basic token!"}})

(deftest issue-search
  (testing "returns issues for criteria"
    (with-fake-http [{:url "http://jira.local/rest/api/latest/search"
                      :method :get
                      :headers {"Authorization" "Basic token!"}
                      :query-params {"jql" "criteria"
                                     "fields" "selected,fields"}}
                     {:status 200
                      :body jira-response}]
      (let [issues (search http-client-config "criteria" "selected,fields")]
        (is (= [{:fields {:assignee {:displayName "person"}
                          :status {:name "testing"}
                          :summary "summary"}}]
               issues))))))

(deftest epic-field
  (testing "returns field name"
    (with-fake-http [{:url (str (http-client-config :base-url) "/field")
                      :method :get
                      :headers (http-client-config :headers)}
                     {:status 200
                      :body (generate-string [{:id "custom_923" :name "Epic Link"}
                                              {:id "issuetype" :name "Issue Type"}])}]
      (let [field-name (epic-field-name http-client-config)]
        (is (= :custom_923 field-name))))))
