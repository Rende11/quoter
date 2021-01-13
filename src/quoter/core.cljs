(ns quoter.core
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]
            [ajax.core :refer [GET]]
            [garden.core :refer [css]]
            [clojure.string :as str]))

(defonce app-state
  (r/atom {}))

(defn get-quote! [state]
  (swap! state assoc :loading true)
  (GET "https://quote-garden.herokuapp.com/api/v3/quotes/random"
       {:handler (fn [resp]
                   (swap! state assoc :quote (get-in resp ["data" 0]) :loading false))
        :error-handler (fn [err]
                         (swap! state assoc :loading false)
                         (println "Error " err))}))


(defn get-text [state]
  (get-in state [:quote "quoteText"]))

(defn get-author [state]
  (get-in state [:quote "quoteAuthor"]))

(defn get-twitter-link [state]
  (str "https://twitter.com/intent/tweet?hashtags=quoter&text=" (get-text state) " - " (get-author state)))

(def palette
  {:twitter-blue  "#1b95e0"})

(def styles
  (let [main-color  (:twitter-blue palette)
        hover-color "#009fff"]
    (css [:body {:background-color main-color
                 :font-family      "Verdana, sans-serif"}
          [:h1 {:margin-top "3rem"
                :color      :white
                :text-align :center}]

          [:.quote {:background-color :white
                    :color            main-color
                    :width            "50%"
                    :margin           "0px auto"
                    :padding          "1.5rem"
                    :border-radius    "10px"}
           [:.wrapper-holder {:margin-bottom "1rem"}
            [:.placeholder {:background-color    :lightskyblue
                            :background          "linear-gradient(to right, white, white, lightskyblue, white, white)"
                            :background-size     "380% 100%"
                            :animation           "placeholder-animation 5s linear infinite"
                            :animation-direction "reverse"
                            :margin              "0.5rem 0rem"
                            :height              "1.5rem"}]]
           [:.text {:font-size "1.5rem"}
            [:&:before {:content "'“'"}]
            [:&:after {:content "'”'"}]]
           [:.author {:text-align "right"
                      :font-style :italic
                      :margin     "1rem 0rem"}]
           [:.buttons {:display         :flex
                       :justify-content :space-between}
            [:.btn {:color            :white
                    :text-transform   :uppercase
                    :font-weight      :bold
                    :transition       "background-color 0.2s ease-out"
                    :background-color main-color
                    :border-radius    "4px"
                    :padding          "0.5rem 1rem"
                    :width            :fit-content
                    :cursor           :pointer
                    :text-decoration  :none}
             [:&:hover {:background-color hover-color
                        :transition       "background-color 0.2s ease-in"}]
             [:&.disabled {:pointer-events   :none
                           :background-color :lightskyblue}]]
            ]]])))

(def pure-css
  "@keyframes placeholder-animation {
    0%   {background-position: 0 0;}
    100%  {background-position: 100%}
   }")


(defn build-class [params]
  (str/join "." (keep (fn [[k v]]
                        (when v
                          k)) params)))

(def placeholder
  [:div.wrapper-holder
   [:div.placeholder]
   [:div.placeholder]])


(defn app [state]
  [:div#app
   [:style styles]
   [:style pure-css]
   [:h1 "Random Quote Generator"]
   [:div.quote
    (if-not (get-text @state)
      placeholder
      [:div.quote-component
       [:div.text (get-text @state)]
       [:div.author (str "- " (get-author @state))]])
    [:div.buttons
     [:a.btn.twitter-share-button {:href   (get-twitter-link @state)
                                   :target "_blank"} "Tweet"]
     [:div.btn {:on-click #(get-quote! state)
                :class    (build-class {"disabled" (:loading @state)})} "Next"]]]])

(defn run []
  (get-quote! app-state)
  (rdom/render [app app-state] (js/document.getElementById "root")))

(defn ^:dev/after-load init []
  (run))
