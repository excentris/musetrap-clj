(ns musetrap-clj.core-test
  (:require [clojure.test :refer :all]
            [musetrap-clj.core :refer :all]))

(deftest get-ingredient-test
  (testing "we actually get a seq"
    (is (seq? (get-ingredient [:a :b]))))
  (testing "getting a random ingredient from a bundle"
    (is (= 1 (count (get-ingredient [:a :b]))))))

(deftest extract-params-test
  (testing "params are correctly converted to keywords"
    (is (keyword? (first (extract-params {"bundle" ["colors" "animals"]} "bundle")))))
  (testing "params are correctly extracted"
    (is (= (seq [:colors :animals]) (extract-params {"bundle" ["colors" "animals"]} "bundle")))))
