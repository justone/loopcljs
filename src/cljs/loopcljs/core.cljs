(ns loopcljs.core
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [clojure.string :as string]
            [goog.net.Jsonp :as jsonp]))

(def app-state (atom {:images ()}))

(defn load-data
  []
  (let [jsonp (goog.net.Jsonp. "http://www.reddit.com/r/perfectloops/top.json" "jsonp")]
    (.send jsonp
           (clj->js {"sort" "top" "t" "week"})
           (fn [datajs]
             (let [data      (js->clj datajs)
                   entries   (get-in data ["data" "children"])
                   pg13      (remove #(get-in % ["data" "over_18"]) entries)
                   images    (map #(get-in % ["data" "url"]) pg13)
                   just-gifs (filter #(re-matches #".*\.gifv?$" %) images)
                   no-gifvs  (map #(string/replace % "gifv" "gif") just-gifs)]
               ;(js/console.log (str no-gifvs))
               (swap! app-state assoc :images no-gifvs))))))

(defn main []

  ; create component
  (om/root
    (fn [app owner]
      (reify
        om/IRender
        (render [_]
          (dom/div {:id "gifs"} (map #(dom/img {:src %}) (:images app))))))
    app-state
    {:target (. js/document (getElementById "app"))})

  ; load data
  (load-data))
