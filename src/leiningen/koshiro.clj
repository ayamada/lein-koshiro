(ns leiningen.koshiro
  (:require [project-clj.core :as project-clj]
            [ancient-clj.core :as ancient-clj]
            [version-clj.core :as version-clj]))

(defn- get-all-dependencies-from-project-clj []
  (eval `(apply concat
                (project-clj/get :plugins)
                (project-clj/get :dependencies)
                (map (fn [[k# v#]]
                       (concat (:plugins v#) (:dependencies v#)))
                     (project-clj/get :profiles)))))

(defn- get-latest-version [artifact-id options]
  (ancient-clj/latest-version-string! artifact-id options))

(defn- get-version-type [ver]
  (cond
    (version-clj/snapshot? ver) :snapshot
    (version-clj/qualified? ver) :qualified
    :else :stable))

(defn- matched-message [version-type]
  (case version-type
    :stable "(latest-stable)"
    :qualified "(latest-qualified)"
    :snapshot "(latest-snapshot)"))

(defn- af! [current-version latest-version]
  (throw (ex-info "assertion failed" {:current-version current-version
                                      :latest-version latest-version})))

(defn- deep-check [artifact-id options current-version latest-version]
  (let [latest-qualified latest-version
        options (assoc options :qualified? false)
        latest-stable (get-latest-version artifact-id options)]
    (if (= current-version latest-stable)
      (str "(latest-stable, but found " (pr-str latest-qualified) ")")
      (pr-str latest-stable))))

(defn koshiro
  "Check version of all :dependencies (include :profiles) in your project.clj"
  [project & args]
  (doseq [dependency (get-all-dependencies-from-project-clj)]
    (let [[artifact-id current-version & dep-opts] dependency
          target (pr-str (if (empty? dep-opts)
                           [artifact-id current-version]
                           [artifact-id current-version '...]))]
      (print target "=>" "")
      (let [current-version-type (get-version-type current-version)
            options {:snapshots? (= current-version-type :snapshot)
                     :qualified? true
                     ;:repositories {...} ; TODO: find from project
                     }
            latest-version (get-latest-version artifact-id options)
            latest-version-type (get-version-type latest-version)
            result (if (= current-version latest-version)
                     (matched-message current-version-type)
                     (case [current-version-type latest-version-type]
                       [:stable :stable] (pr-str latest-version)
                       [:stable :qualified] (deep-check artifact-id
                                                        options
                                                        current-version
                                                        latest-version)
                       [:stable :snapshot] (af! current-version latest-version)
                       [:qualified :stable] (pr-str latest-version)
                       [:qualified :qualified] (pr-str latest-version)
                       [:qualified :snapshot] (af! current-version
                                                   latest-version)
                       [:snapshot :stable] (pr-str latest-version)
                       [:snapshot :qualified] (pr-str latest-version)
                       [:snapshot :snapshot] (pr-str latest-version)))]
        (println result))))
  (println "All done."))
