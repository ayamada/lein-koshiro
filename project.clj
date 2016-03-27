(defproject jp.ne.tir/lein-koshiro "0.1.5-SNAPSHOT"
  :description "Yet another lein-ancient"
  :url "https://github.com/ayamada/lein-koshiro"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"
            :year 2016
            :key "mit"}
  :dependencies [[jp.ne.tir/project-clj "0.1.6"
                  :exclusions [org.codehaus.plexus/plexus-utils
                               org.apache.maven.wagon/wagon-provider-api]]
                 [ancient-clj "0.3.12"
                  :exclusions [org.clojure/clojure]]
                 [version-clj "0.1.2"
                  :exclusions [org.clojure/clojure]]]
  ;; :pedantic? :abort
  :min-lein-version "2.4.0"
  :eval-in-leiningen true
  ;; These profiles are for test run
  :profiles {:for-localtest {:plugins [[lein-midje "3.1"]
                                       [lein-figwheel "0.5.0-5"]]
                             :dependencies [[midje "1.8.4-SNAPSHOT"]
                                            [figwheel "0.5.0-5"]
                                            [javax.servlet/servlet-api "2.5"]
                                            [org.clojure/java.jdbc "0.3.0-rc1"]
                                            ;; redundant entry for test
                                            [version-clj "0.1.2"]
                                            ;; redundant but differ version
                                            [ancient-clj "0.3.11"]]}})
