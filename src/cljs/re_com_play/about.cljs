(ns re-com-play.about
  (:require [re-com.core   :refer [h-box v-box box gap line title label hyperlink-href input-text p p-span]]
            [re-com-play.utils :refer [panel-title]]))

(defn welecome
  []
  [v-box
   :children [[gap :size "10px"]
              [p-span
               "This is a test of the About page"]]])


(defn panel2
  []
  [v-box
   :children [[panel-title "About"]
              [gap :size "15px"]
              [welecome]
              [gap :size "40px"]]])

;; core holds a reference to panel, so need one level of indirection to get figwheel updates
(defn panel
  []
  [panel2])

