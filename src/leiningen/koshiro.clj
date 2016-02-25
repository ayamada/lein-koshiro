(ns leiningen.koshiro
  (:require [ancient-clj.core :as ancient]
            [project-clj.core]))

(defn- get-dependencies-from-project-clj []
  (eval '(apply concat
                (project-clj.core/get :plugins)
                (project-clj.core/get :dependencies)
                (map (fn [[k v]]
                       (concat (:plugins v) (:dependencies v)))
                     (project-clj.core/get :profiles)))))

(defn koshiro
  "Check version of all :dependencies (include :profiles) in your project.clj"
  [project & args]
  (doseq [dependency (get-dependencies-from-project-clj)]
    (let [[artifact-id current-version & dep-opts] dependency
          ;; TODO: customize by args
          ancient-options {:snapshots? false
                           :qualified? false
                           ;:repositories {...}
                           }
          latest-version (ancient/latest-version-string! artifact-id
                                                         ancient-options)
          target (pr-str [artifact-id current-version])
          result (if (= current-version latest-version)
                   "(latest-stable)"
                   (str (pr-str [artifact-id latest-version]) " !!!"))]
      (println target "=>" result)))
  (println "All done."))
