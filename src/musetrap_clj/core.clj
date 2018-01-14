(ns musetrap-clj.core
  (:gen-class)
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]))

(def data {
  :recipes {:animal_warrior [:animals :weapons]
            :humanoid_creature [:creatures :weapons]}
  :bundles {:animals ["dog" "cat" "bird" "horse"]
            :creatures ["ork" "werewolf" "troll" "dragon" "goblin"]
            :weapons ["sword" "axe" "war hammer" "rifle" "pistol"]
            :colors ["red" "green" "blue"]}})

(defn get-ingredient
  "Get 1 random ingredient from the bundle."
  [bundle]
  (take 1 (repeatedly #(rand-nth bundle))))

(defn get-bundle
  "Get the vector of ingredients for the specified bundle_id."
  [bundle_id]
  (get-in data [:bundles bundle_id]))

(defn get-recipe
  "Get the vector of bundle_id's for the specified recipe_id."
  [recipe_id]
  (get-in data [:recipes recipe_id]))

(defn get-bundles
  "Get a sequence with the vectors of ingrediets for each of the specified bundle id."
  [vector_of_bundle_ids]
  (map get-bundle vector_of_bundle_ids))

(defn get-recipe-bundles
  "Get a sequence with the vectors of ingredients for each bundle on the specified recipe_id."
  [recipe_id]
  (get-bundles (get-recipe recipe_id)))

;; TODO refactor the following two functions
(defn cook-recipe-from-id
  [recipe_id]
  (flatten (map get-ingredient (get-recipe-bundles recipe_id))))

(defn cook-recipe-from-bundles
  [vector_of_bundle_ids]
  (flatten (map get-ingredient (get-bundles vector_of_bundle_ids))))

(defn extract-params
  "Extract a vector of the values for the specified param.
  This is necessary because params will be either a single string or a vector.
  This function makes the result of parsing the params uniform."
  [params param]
  (map keyword (flatten (vector (get-in params [param])))))

(defn cook-recipe
  "When specifying both recipes and bundles, each recipe will be cooked supplemented by all of the bundles."
  [params]
  (concat (map #(concat % (cook-recipe-from-bundles (extract-params params "bundle"))) 
               (map cook-recipe-from-id (extract-params params "recipe")))))

(defresource atelier [params]
  :available-media-types  ["application/json"]
  :handle-ok (cook-recipe params))

(defresource recipes []
  :available-media-types  ["application/json"]
  :handle-ok (get-in data [:recipes]))

(defresource recipe [recipe_id]
  :available-media-types  ["application/json"]
  :handle-ok (get-in data [:recipes (keyword recipe_id)]))

(defroutes app
  (ANY "/" [] (str "hello"))
  (ANY "/cook" [& params] (atelier params))
  (ANY "/recipes/:recipe_id" [recipe_id] (recipe recipe_id))
  (ANY "/recipes" [] (recipes)))

(def handler 
  (-> app 
      wrap-params))
