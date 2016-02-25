# lein-koshiro

Yet another [lein-ancient](https://github.com/xsc/lein-ancient)

[![Clojars Project](https://img.shields.io/clojars/v/jp.ne.tir/lein-koshiro.svg)](https://clojars.org/jp.ne.tir/lein-koshiro)


# Usage

Add your `:plugins` to `[jp.ne.tir/lein-koshiro "0.1.0"]`,
and run `lein koshiro`

~~~
$ cat project.clj
(defproject example "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.4.1"]])

$ lein koshiro
[org.clojure/clojure "1.8.0"] => (latest-stable)
[org.clojure/java.jdbc "0.4.1"] => [org.clojure/java.jdbc "0.4.2"] !!!
All done.
~~~

# TODO

- Add to support optional argument by lein


# ChangeLog

- 0.1.0 (2016-02-25)
    - Initial release


