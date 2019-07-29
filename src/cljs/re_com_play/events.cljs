(ns re-com-play.events
  (:require
   [re-frame.core :as re-frame]
   [re-com-play.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-selected-tab-id
 (fn [db [_ select-tab-id]]
   (assoc db :selected-tab-id select-tab-id)))

(re-frame/reg-event-db
 ::mouse-over?
 (fn [db [_ mouse-over?]]
   (assoc db :mouse-over? mouse-over?)))

