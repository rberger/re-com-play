(ns re-com-play.db
  (:require
   [re-com.util :refer [item-for-id]]
   [alandipert.storage-atom :refer [local-storage]]))

(defonce id-store        (local-storage (atom nil) ::id-store))


(defn init-db
  [tabs-definition]
  (js/console.log (str "init-db tabs-definition: " tabs-definition))
  (defonce selected-tab-id (if (or (nil? @id-store) (nil? (item-for-id @id-store tabs-definition)))
                             (:id (first tabs-definition))
                             @id-store))  ;; id of the selected tab from local storage
  (js/console.log (str "init-db selected-tab-id: " ) selected-tab-id)
  {:name "re-frame"
   :mouse-over? false
   :active-panel :introduction
   :selected-tab-id selected-tab-id})
