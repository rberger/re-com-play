(ns re-com-play.views
  (:require
   [goog.events :as gevents]
   [re-com-play.events :as events]
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [re-com.core :refer [h-box v-box box gap line scroller border label p title alert-box h-split hyperlink-href] :refer-macros [handler-fn]]
   [re-com.util :refer [get-element-by-id item-for-id]]
   [re-com-play.subs :as subs]
   [re-com-play.introduction :as introduction]
   [re-com-play.about :as about]
   [secretary.core :as    secretary]
   [re-com-play.utils :refer [panel-title scroll-to-top]]
   [goog.history.EventType        :as    EventType]
   )
  (:import [goog History]))

(enable-console-print!)

(def tabs-definition
  [{:id :introduction           :level :major :label "Introduction"       :panel introduction/panel}
   {:id :about           :level :major :label "About"       :panel about/panel}])

(defn set-mouse-over [value]
  (js/console.log (str "set-mouse-over: " value))
  (re-frame/dispatch [::events/mouse-over? value]))

(defn set-active-panel [tab-id]
  (js/console.log  (str "tab: " tab-id))
  (re-frame/dispatch [::events/set-active-panel tab-id]))

(defn nav-item
  []
  (let [foo "hello"]
    (fn [tab selected-tab-id]
      (let [selected?   (= @selected-tab-id (:id tab))
            is-major?  (= (:level tab) :major)
            mouse-over? (re-frame/subscribe [::subs/mouse-over?])
            has-panel? (some? (:panel tab))]
        (js/console.log (str "nav-item selected-tab-id: " @selected-tab-id " selected?: " selected? " has-panel?: " has-panel? " mouse-over?: " @mouse-over?))
        [:div
         {:style         {;:width            "150px"
                          :white-space      "nowrap"
                          :line-height      "1.3em"
                          :padding-left     (if is-major? "24px" "32px")
                          :padding-top      (when is-major? "6px")
                          :font-size        (when is-major? "15px")
                          :font-weight      (when is-major? "bold")
                          :border-right     (when selected? "4px #d0d0d0 solid")
                          :cursor           (if has-panel? "pointer" "default")
                          :color            (if has-panel? (when selected? "#111") "#888")
                          :background-color (if (or
                                                  (= @selected-tab-id (:id tab))
                                                  @mouse-over?) "#eaeaea")}

          :on-mouse-over (handler-fn (when has-panel? (set-mouse-over true)))
          :on-mouse-out  (handler-fn (set-mouse-over false))
          :on-click      (handler-fn (when has-panel?
                                       (set-active-panel (:id tab))
                                       (scroll-to-top (get-element-by-id "right-panel"))))}
         [:span (:label tab)]]))))


(defn left-side-nav-bar
  [selected-tab-id]
    [v-box
     :class    "noselect"
     :style    {:background-color "#fcfcfc"}
     ;:size    "1"
     :children (for [tab tabs-definition]
                 (do
                   (js/console.log (str "for tab: " tab " @selected-tab-id: " @selected-tab-id))
                   [nav-item tab selected-tab-id]))])

(defn re-com-title-box
  []
  [h-box
   :justify :center
   :align   :center
   :height  "62px"
   :style   {:background-color "#666"}
   :children [[title
               :label "Re-com"
               :level :level1
               :style {:font-size   "32px"
                       :color       "#fefefe"}]]])

(defn browser-alert
  []
  [box
   :padding "10px 10px 0px 0px"
   :child   [alert-box
             :alert-type :danger
             :heading    "Only Tested On Chrome"
             :body       "re-com should work on all modern browsers, but there might be dragons!"]])

(defonce history (History.))
(gevents/listen history EventType/NAVIGATE (fn [event] (secretary/dispatch! (.-token event))))
(.setEnabled history true)

;; main
(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])
        selected-tab-id (re-frame/subscribe [::subs/selected-tab-id])]
    (js/console.log (str "main active-panel: " @active-panel))
    [h-split
       ;; Outer-most box height must be 100% to fill the entrie client height.
       ;; This assumes that height of <body> is itself also set to 100%.
       ;; width does not need to be set.
       :height   "100%"
       ;:gap      "60px"
       :initial-split 9
       :margin "0px"
       :panel-1 [scroller
                 ;:size  "none"
                 :v-scroll :auto
                 :h-scroll :off
                 :child [v-box
                         :size "1"
                         :children [[re-com-title-box]
                                    [left-side-nav-bar active-panel]]]]
       :panel-2 [scroller
                 :attr  {:id "right-panel"}
                 :child [v-box
                         :size  "1"
                         :children [(when-not (-> js/goog .-labs .-userAgent .-browser .isChrome) [browser-alert])
                                    [box
                                     :padding "0px 0px 0px 50px"
                                     ;; the tab panel to show, for the selected tab
                                     :child [(:panel (item-for-id @active-panel tabs-definition))]]]]]]))
