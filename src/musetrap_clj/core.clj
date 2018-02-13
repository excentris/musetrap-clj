(ns musetrap-clj.core 
  (:gen-class) 
  (:require [liberator.core :refer [resource defresource]] 
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]
            [ring.adapter.jetty :refer [run-jetty]]
            [musetrap-clj.recipe-repository :as recipes]
            [musetrap-clj.bundle-repository :as bundles]
            [musetrap-clj.translator :refer [translate-ingredient]]))

(defn get-ingredient
  "Get 1 random ingredient from the vector of ingredients. If the vector is empty, return an
  empty sequence."
  [vector_of_ingredients]
  (if-not (empty? vector_of_ingredients)
    (take 1 (repeatedly #(rand-nth vector_of_ingredients)))
    (sequence ())))

(defn get-recipe-bundles
  "Get a sequence with the vectors of ingredients for each bundle on the specified recipe_id."
  [recipe_id]
  (bundles/get-bundles (recipes/get-recipe recipe_id)))

(defn prepare-ingredients
  [sequence_of_bundles lang]
  (map 
    #(translate-ingredient [lang %] %)
    (flatten (map get-ingredient sequence_of_bundles))))

(defn extract-params
  "Extract a vector of the values for the specified param.
  This is necessary because params will be either a single string or a vector.
  This function makes the result of parsing the params uniform."
  [params param]
  (let [param-value (get-in params [param])]
    (if-not (nil? param-value)
      (map keyword (flatten (vector (get-in params [param]))))
      (sequence ()))))

(defn cook-recipe
  "When specifying both recipes and bundles, each recipe will be cooked supplemented by all 
  of the bundles."
  [params]
  (let [requested-bundles (extract-params params "bundle")
        requested-recipes (extract-params params "recipe")
        lang (keyword (get-in params ["lang"] "en"))]
    (concat
      (map 
        #(concat % (prepare-ingredients (bundles/get-bundles requested-bundles) lang))
        (map #(prepare-ingredients % lang) (map get-recipe-bundles requested-recipes))))))

(defresource atelier
  [params]
  :available-media-types  ["application/json"]
  :handle-ok (cook-recipe params))

(defresource recipes
  []
  :available-media-types  ["application/json"]
  :handle-ok (recipes/list-recipes))

(defresource recipe
  [recipe_id]
  :available-media-types  ["application/json"]
  :handle-ok (recipes/describe-recipe (keyword recipe_id)))

(defroutes app
  (ANY "/" [] (str "hello"))
  (ANY "/cook" [& params] (atelier params))
  (ANY "/recipes/:recipe_id" [recipe_id] (recipe recipe_id))
  (ANY "/recipes" [] (recipes)))

(def handler 
  (-> app 
      wrap-params))

(defn -main
  []
  (let [port  (Integer.  (or  (System/getenv  "PORT")  "8080"))]
    (run-jetty handler {:port port :join? false})))
