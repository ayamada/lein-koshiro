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

(defn koshiro
  "Check version of all :dependencies (include :profiles) in your project.clj"
  [project & args]
  (doseq [dependency (get-all-dependencies-from-project-clj)]
    (let [[artifact-id current-version & dep-opts] dependency
          target (pr-str (if (empty? dep-opts)
                           [artifact-id current-version]
                           [artifact-id current-version '...]))]
      (print target "")
      (let [include-snapshot? (version-clj/snapshot? current-version)
            include-qualified? (version-clj/qualified? current-version)
            latest-label (cond
                           include-snapshot? "(latest-snapshot)"
                           include-qualified? "(latest-qualified)"
                           :else "(latest-stable)")
            options {:snapshots? include-snapshot?
                     :qualified? true
                     ;:repositories {...} ; TODO: find from project
                     }
            latest-version (get-latest-version artifact-id options)
            result (cond
                     (= current-version latest-version) latest-label
                     include-snapshot? (pr-str latest-version)
                     include-qualified? (pr-str latest-version)
                     ;; deep check for stable version
                     :else (let [latest-qualified latest-version
                                 options (assoc options :qualified? false)
                                 latest-stable (get-latest-version
                                                 artifact-id options)]
                             (if (= current-version latest-stable)
                               (str "(latest-stable, but found "
                                    (pr-str latest-qualified)
                                    ")")
                               (pr-str latest-stable))))]
        (println "=>" result))))
  (println "All done."))
