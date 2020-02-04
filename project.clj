(defproject walk-the-wall "0.1.0"
  :description "Walk the wall"
  :url ""
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :plugins [[lein-cljfmt "0.6.4"]
            [lein-eftest "0.5.9"]]

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

  :profiles {
             :test {:dependencies [[http-kit.fake "0.2.1"]]}}
  :repl-options {:init-ns walk-the-wall.core})
