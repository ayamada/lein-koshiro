# lein-koshiro

Yet another [lein-ancient](https://github.com/xsc/lein-ancient)

[![Clojars Project](https://img.shields.io/clojars/v/jp.ne.tir/lein-koshiro.svg)](https://clojars.org/jp.ne.tir/lein-koshiro)


# Usage

Add your `:plugins` to `[jp.ne.tir/lein-koshiro "X.Y.Z"]`,
and run `lein koshiro`

~~~
$ cat project.clj
(defproject example "0.1.0-SNAPSHOT"
  :plugins [[jp.ne.tir/lein-koshiro "0.1.1"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.4.1"]]
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]}
             :test {:plugins [[lein-midje "3.1"]]
                    :dependencies [[midje "1.8.2"]]}})

$ lein koshiro
[jp.ne.tir/lein-koshiro "0.1.1"] => (latest-stable)
[org.clojure/clojure "1.8.0"] => (latest-stable)
[org.clojure/java.jdbc "0.4.1"] => [org.clojure/java.jdbc "0.4.2"] !!!
[javax.servlet/servlet-api "2.5"] => (latest-stable)
[lein-midje/lein-midje "3.1"] => [lein-midje/lein-midje "3.2"] !!!
[midje/midje "1.8.2"] => [midje/midje "1.8.3"] !!!
All done.
~~~

# Development

## Run localtest

Run `lein koshiro` in this repository,
to check `:for-localtest` profile in `project.clj` on this repository


# TODO

- Add to support optional argument by lein


# ChangeLog

- 0.1.1 (2016-02-25)
    - Add to check :plugins

- 0.1.0 (2016-02-25)
    - Initial release


