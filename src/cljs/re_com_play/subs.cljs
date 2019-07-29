(ns re-com-play.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::selected-tab-id
 (fn [db _]
   (:selected-tab-id db)))

(re-frame/reg-sub
 ::mouse-over?
 (fn [db _]
   (:mouse-over? db)))
