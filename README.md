# Wat?

This is a ClojureScript implementation of [this post](http://glenmaddern.com/articles/javascript-in-2015).

Why?  Because.

# How to run this thing

Start a REPL (in a terminal: `lein repl`. In the REPL do:

```clojure
(run)
```

Then go to <http://localhost:10555/>.  Any code you edit will be automatically reloaded in the browser.

## Browser REPL

To start the browser REPL, run the following in the REPL prompt:

```clojure
(browser-repl)
```

OR, if you're using Vim with [all the right plugins](http://endot.org/2014/02/12/setting-up-vim-for-clojure/), then you can do the following in Vim instead:

```
Piggieback (weasel.repl.websocket/repl-env :ip "0.0.0.0" :port 9001)
```
