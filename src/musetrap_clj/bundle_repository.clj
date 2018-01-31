(ns musetrap-clj.bundle-repository
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))


(def ^:private data (edn/read-string (slurp (io/resource "data/bundles.edn"))))

(defn get-bundle
  "Get the vector of ingredients for the specified bundle_id."
  [bundle_id]
  (get-in data [:bundles bundle_id]))

(defn get-bundles
  "Get a sequence with the vectors of ingredients for each of the specified bundle id."
  [vector_of_bundle_ids]
  (map get-bundle vector_of_bundle_ids))
