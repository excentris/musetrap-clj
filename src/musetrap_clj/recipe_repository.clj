(ns musetrap-clj.recipe-repository
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.edn :as edn]))

(defn value-reader
  [key value]
  (if (= key :ingredient_bundles)
    (into [] (map keyword value))
    value))

(def ^:private data 
  (json/read-str (slurp (io/resource "musetrap-data/data/recipes.json")) 
                 :key-fn keyword
                 :value-fn value-reader))

(defn get-recipe
  "Get the vector of bundle_id's for the specified recipe_id."
  [recipe_id]
  (get-in data [recipe_id :ingredient_bundles] []))

(defn list-recipes
  "List the available recipes."
  []
  data)

(defn describe-recipe
  "Describe the specified recipe."
  [recipe_id]
  (get-in data [:recipes recipe_id]))
