(ns musetrap-clj.recipe-repository
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def ^:private data (edn/read-string (slurp (io/resource "data/recipes.edn"))))

(defn get-recipe
  "Get the vector of bundle_id's for the specified recipe_id."
  [recipe_id]
  (get-in data [:recipes recipe_id]))

(defn list-recipes
  "List the available recipes."
  []
  (get-in data [:recipes]))

(defn describe-recipe
  "Describe the specified recipe."
  [recipe_id]
  (get-in data [:recipes recipe_id]))
