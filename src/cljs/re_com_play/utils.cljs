(ns re-com-play.utils
  (:require [re-com.core :refer [h-box v-box box gap title line label hyperlink-href align-style]]))

(defn panel-title
  "Shown across the top of each page"
  [panel-name]
  [v-box
   :children [[h-box
               :margin "0px 0px 9px 0px"
               :height "54px"
               :align :end
               :children [[title
                           :label         panel-name
                           :level         :level1
                           :margin-bottom "0px"
                           :margin-top    "2px"]
                          [line]]]]])


(defn title2
  "2nd level title"
  [text style]
  [title
   :label text
   :level :level2
   :style style])

(defn status-text
  "given some status text, return a component that displays that status"
  [status style]
  [:span
   [:span.bold "Status: "]
   [:span {:style style} status]])

(defn arg-row
  "I show one argument in an args table."
  [name-width arg odd-row?]
  (let [required   (:required arg)
        default    (:default arg)
        arg-type   (:type arg)
        needed-vec (if (not required)
                     (if (nil? default)
                       [[:span.semibold.all-small-caps "optional"]]
                       [[:span.semibold.all-small-caps "default:"] [:span.semibold (str default)]])
                     [[:span.semibold.all-small-caps "required"]])]
    [h-box
     :style    {:background (if odd-row? "#F4F4F4" "#FCFCFC")}
     :children [[:span {:class "semibold"
                        :style (merge (align-style :align-self :center)
                                      {:width        name-width
                                       :padding-left "15px"})}
                 (str (:name arg))]
                [line :size "1px" :color "white"]
                [v-box
                 :style {:padding "7px 15px 2px 15px"}
                 :gap  "4px"
                 :width "310px"
                 :children [[h-box
                             :gap   "4px"
                             :children (concat [[:span.semibold  arg-type]
                                                [gap :size "10px"]]
                                               needed-vec)]
                            [:p
                              {:font-size "smaller" :color "red"}
                              (:description arg)]]]]]))


(defn args-table
  "I display a component arguements in an easy to read format"
  [args]
  (let [name-width  "130px"]
    (fn
      []
      [v-box
       :children (concat
                   [[title2 "Parameters"]
                    [gap :size "10px"]]
                   (map (partial arg-row name-width)  args (cycle [true false])))])))


(defn scroll-to-top
  [element]
  (set! (.-scrollTop element) 0))
