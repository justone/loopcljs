(ns loopcljs.dev
  (:require [loopcljs.core :as core]
            [figwheel.client :as figwheel :include-macros true]
            [cljs.core.async :refer [put!]]
            [weasel.repl :as weasel]))

(enable-console-print!)

(def source-host (.. js/document -location -hostname))

(figwheel/watch-and-reload
  :websocket-url (str "ws://" source-host ":3449/figwheel-ws")
  :jsload-callback (fn [] (core/main)))

(weasel/connect (str "ws://" source-host ":9001") :verbose true)

(core/main)
