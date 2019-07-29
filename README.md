# re-com-play

Get the basic re-com demo layout with nabber working in a pure re-frame app

Recreates the same nav-bar setup as in the re-com demo but with a more re-frame
approach (no reagent/atoms and using re-frame subs/events for everything)

Started from using the re-frame leinigen template:

```
lein new re-frame re-com-play +re-com +cider +routes +re-frisk
```

And then incorporated the nav-bar stuff from the re-com demo.

## Development Mode

### Start Cider from Emacs:

Put this in your Emacs config file:

```
(setq cider-cljs-lein-repl
	"(do (require 'figwheel-sidecar.repl-api)
         (figwheel-sidecar.repl-api/start-figwheel!)
         (figwheel-sidecar.repl-api/cljs-repl))")
```

Navigate to a clojurescript file and start a figwheel REPL with `cider-jack-in-clojurescript` or (`C-c M-J`)

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
