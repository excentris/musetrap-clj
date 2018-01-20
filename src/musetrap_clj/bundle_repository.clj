(ns musetrap-clj.bundle-repository
  (:gen-class))

(def ^:private data {
  :bundles {:animals ["dog" "cat" "bird" "horse"]
            :creatures ["ork" "werewolf" "troll" "dragon" "goblin"]
            :weapons ["sword" "axe" "war hammer" "rifle" "pistol"]
            :colors ["red" "green" "blue"]}})

(defn get-bundle
  "Get the vector of ingredients for the specified bundle_id."
  [bundle_id]
  (get-in data [:bundles bundle_id]))

