(ns musetrap-clj.bundle-repository
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.edn :as edn]))


(defn value-reader
  [key value]
  (if (= key :ingredients)
    (into [] (map keyword value))
    value))

(def ^:private data 
  (json/read-str (slurp (io/resource "musetrap-data/data/ingredient_bundles.json")) 
                 :key-fn keyword
                 :value-fn value-reader))

(defn get-bundle
  "Get the vector of ingredients for the specified bundle_id."
  [bundle_id]
  (get-in data [bundle_id :ingredients] []))

(defn get-bundles
  "Get a sequence with the vectors of ingredients for each of the specified bundle id."
  [vector_of_bundle_ids]
  (map get-bundle vector_of_bundle_ids))
