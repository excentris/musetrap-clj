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

(defresource recipe [recipe_id]
  :available-media-types  ["application/json"]
  :handle-ok (cook-recipe recipe_id))

(defresource recipes []
  :available-media-types  ["application/json"]
  :handle-ok (get-in data [:recipes]))

(defroutes app
  (ANY "/recipes/:recipe_id" [recipe_id] (recipe recipe_id))
  (ANY "/" [] (str "hello"))
  (ANY "/recipes" [] (recipes)))

(def handler 
  (-> app 
      wrap-params))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
