(ns loopcljs.core
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [clojure.string :as string]
            [goog.net.Jsonp :as jsonp]
            [clojure.walk :as walk]))

(def app-state (atom {:images ()}))

(defn load-data
  []
  (let [jsonp (goog.net.Jsonp. "http://www.reddit.com/r/perfectloops/top.json" "jsonp")]
    (.send jsonp
           (clj->js {"sort" "top" "t" "week"})
           (fn [datajs]
             (let [data    (walk/keywordize-keys (js->clj datajs))
                   entries (get-in data [:data :children])]
               (->> entries
                    (map :data)
                    (remove :over_18)
                    (map :url)
                    (filter #(re-matches #".*\.gifv?$" %))
                    (map #(string/replace % "gifv" "gif"))
                    (swap! app-state assoc :images)))))))

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
