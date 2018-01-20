(ns musetrap-clj.recipe-repository
  (:gen-class))

(def ^:private data {
  :recipes {:animal_warrior [:animals :weapons]
            :humanoid_creature [:creatures :weapons]}})

(defn get-recipe
  "Get the vector of bundle_id's for the specified recipe_id."
  [recipe_id]
  (get-in data [:recipes recipe_id]))

