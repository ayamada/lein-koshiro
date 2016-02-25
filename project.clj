(defproject jp.ne.tir/lein-koshiro "0.1.1"
  :description "Yet another lein-ancient"
  :url "https://github.com/ayamada/lein-koshiro"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"
            :year 2016
            :key "mit"}
  :dependencies [[jp.ne.tir/project-clj "0.1.4"
                  :exclusions [org.codehaus.plexus/plexus-utils
                               org.apache.maven.wagon/wagon-provider-api]]
                 [ancient-clj "0.3.11"
                  :exclusions [org.clojure/clojure]]
                 ]
  :pedantic? :abort
  :min-lein-version "2.4.0"
  :eval-in-leiningen true)
