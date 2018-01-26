(ns musetrap-clj.recipe-repository-test
  (:require [clojure.test :refer :all]
            [musetrap-clj.recipe-repository :refer :all]))

(deftest get-recipe-test
  (testing "we get a vector of ingredients when getting a recipe"
    (is (vector? (get-recipe :humanoid_creature)))))
