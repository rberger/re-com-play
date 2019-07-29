(ns re-com-play.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
   [secretary.core :as secretary]
   [goog.events :as gevents]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [re-com-play.events :as events]
   ))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (defroute "/" []
    (re-frame/dispatch [::events/set-selected-tab-id :introduction])
    )

  (defroute "/about" []
    (re-frame/dispatch [::events/set-selected-tab-id :about-panel]))


  (defroute "/introduction" []
    (re-frame/dispatch [::events/set-selected-tab-id :introduction]))


  ;; --------------------
  (hook-browser-navigation!))
