(ns walk-the-wall.config-test
  (:require [clojure.test :refer :all])
  (:require [walk-the-wall.config :refer [load-from status-priority-from]]
            [clojure.java.io :as io]))

(deftest status-priority-from-test
  (testing "creates priority from end to beginning"
    (let [status-flow ["To do" "Dev" "Done"]
          status-priority (status-priority-from status-flow)]
      (is (= {"done" 0 "dev" 1 "to do" 2}
             status-priority)))))

(deftest load-config-from-test
  (testing "config creation"
    (let [configs (load-from (io/resource "walls.edn"))
          config (first configs)]
      (is (= (assoc config :status-priority
                           {"done" 0
                            "dev" 1
                            "ready dev" 2
                            "to do" 3})
             config)))))
