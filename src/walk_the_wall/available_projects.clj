(ns walk-the-wall.available-projects
  (:require [hiccup.page :refer :all]))

(defn to-option [config]
  [:option {:value (config :name)} (config :name)])

(defn available-boards-in [configs]
  (html5
   [:head
    [:title "Walk the Wall"]
    (include-css "//fonts.googleapis.com/css2?family=Roboto:wght@300;700")
    (include-css "//cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.css")
    (include-css "screen.css")]
   [:form {:action "/projects"}
    [:fieldset {:class "projects"}
     [:label {:for "board"} "Select a board"]
     [:select {:id "board" :name "board"}
      (map to-option configs)]
     [:input {:class "button-primary", :type "submit", :value "Go"}]]]))
