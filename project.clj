(defproject walk-the-wall :lein-v
  :description "Walk the wall"
  :url "https://github.com/rafasf/walk-the-wall"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :plugins [[lein-cljfmt "0.6.4"]
            [lein-eftest "0.5.9"]
            [com.roomkey/lein-v "7.2.0"]]

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [http-kit "2.4.0-alpha4"]
                 [cheshire "5.8.1"]
                 [http-kit.fake "0.2.1"]
                 [cljfmt "0.6.4"]]

  :main walk-the-wall.core

  :resource-paths ["resources"]

  :eftest {:multithread? :false}

  :release-tasks [["vcs" "assert-committed"]
                  ["v" "update"]
                  ["v" "push-tags"]
                  ["deploy"]]

  :profiles {
             :test {:dependencies [[http-kit.fake "0.2.1"]]}}
  :repl-options {:init-ns walk-the-wall.core})
