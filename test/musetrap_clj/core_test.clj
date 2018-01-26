(ns musetrap-clj.core-test
  (:require [clojure.test :refer :all]
            [musetrap-clj.core :refer :all]))

(deftest get-ingredient-test
  (testing "we actually get a seq"
    (is (seq? (get-ingredient [:a :b]))))
  (testing "getting a random ingredient from a bundle"
    (is (= 1 (count (get-ingredient [:a :b]))))))

(deftest get-bundles-test
  (testing "we get a sequence of vectors of ingredients"
    (is (seq? (get-bundles [:a :b])))))
