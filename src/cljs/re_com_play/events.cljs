(ns re-com-play.events
  (:require
   [re-frame.core :as re-frame]
   [re-com-play.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ tabs-definition]
   (js/console.log (str "initialize-db tabs-definition: " (second tabs-definition)))
   (db/init-db (second tabs-definition))))

(re-frame/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :selected-tab-id active-panel)
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 ::mouse-over?
 (fn [db [_ mouse-over?]]
   (assoc db :mouse-over? mouse-over?)))

