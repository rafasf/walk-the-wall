(ns walk-the-wall.wall
  (:require [hiccup.page :refer :all]))

(defn to-card [story]
  [:section {:class "item"}
   [:article {:class "card"}
    [:p (get-in story [:feature :name] "Featureless")]
    [:h1 (story :title)]
    [:h4 [:a {:href (story :view-url) :target "_blank"} (story :id)] ", " (story :assignee)]
    [:span {:class "card-status"} (story :status)]]])

(defn cards-for [stories]
  [:section {:class "cards"}
   (map to-card stories)])

(defn wall-for [stories]
  (html5
   [:head
    [:title "The Wall - Walk the Wall"]
    (include-css "//fonts.googleapis.com/css?family=Roboto:300,300italic,700,700italic")
    (include-css "//cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.css")
    (include-css "screen.css")]
   (cards-for stories)))
