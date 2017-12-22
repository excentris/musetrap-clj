(ns musetrap-clj.core
  (:gen-class)
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]))

(def data {
  :recipes {:animal_warrior [animals weapons]
            :humanoid_creature [creatures weapons]}
  :bundles {:animals ["dog" "cat" "bird" "horse"]
            :creatures ["ork" "werewolf" "troll" "dragon" "goblin"]
            :weapons ["sword" "axe" "war hammer" "rifle" "pistol"]}})

(defn get-ingredient
  [bundle]
  (take 1 (repeatedly #(rand-nth bundle))))

(defn get-recipe
  [recipe_id]
  (get-in data [:recipes (keyword recipe_id)]))

(defn cook-recipe
  [recipe_id]
  (flatten (map get-ingredient (get-recipe recipe_id))))

;; TODO this function must handle both recipe and bundle params
(defn cook-improvised-recipe
  [params]
  ;; recipe can be either a single recipe_id or a vector of recipe_id
  (if (vector? (get-in params ["recipe"]))
    (concat (map cook-recipe (get-in params ["recipe"])))
    (cook-recipe (get-in params ["recipe"]))))

(defresource recipe [recipe_id]
  :available-media-types  ["application/json"]
  :handle-ok (cook-recipe recipe_id))

(defresource atelier [params]
  :available-media-types  ["application/json"]
  :handle-ok (cook-improvised-recipe params))

(defresource recipes []
  :available-media-types  ["application/json"]
  :handle-ok (get-in data [:recipes]))

(defroutes app
  (ANY "/recipes/:recipe_id" [recipe_id] (recipe recipe_id))
  (ANY "/" [] (str "hello"))
  (ANY "/cook" [& params] (atelier params))
  (ANY "/recipes" [] (recipes)))

(def handler 
  (-> app 
      wrap-params))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
