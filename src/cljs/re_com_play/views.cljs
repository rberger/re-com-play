(ns re-com-play.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [re-com-play.subs :as subs]
   [re-com-play.introduction :as introduction]
   ))


;; home

(defn home-title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name ". This is the Home Page.")
     :level :level1]))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn home-panel []
  [re-com/v-box
   :gap "1em"
   :children [[home-title]
              [link-to-about-page]
              ]])


;; about

(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title]
              [link-to-home-page]]])

(def tabs-definition
  [{:id :introduction           :level :major :label "Introduction"       :panel introduction/panel}])

;; main
(defn- titles [panel-name]
  (case panel-name
    :home-panel home-title
    :about-panel about-title))

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :children [[re-com/h-box :children [[titles @active-panel]] ]
                [re-com/h-box
                 :height "100px"
                 :children [[re-com/box :size "70px" :child "Nav"]
                            [re-com/h-box :size "1" :children [[panels @active-panel]]]]]
                 [re-com/box :child "Footer"]]
    ;; [re-com/v-box
    ;;  :size "100%"
    ;;  :children [[re-com/box :child "Header"]
    ;;             [re-com/h-box
    ;;              :height "100px"
    ;;              :children [[re-com/box :size "70px" :child "Nav"]
    ;;                         [re-com/box :size "1" :children [[panels @active-panel]]]]
    ;;              [re-com/box :child "Footer"]]]
     ]))
