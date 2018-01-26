(ns musetrap-clj.bundle-repository-test
  (:require [clojure.test :refer :all]
            [musetrap-clj.bundle-repository :refer :all]))

(deftest get-bundles-test
  (testing "we get a sequence of vectors of ingredients"
    (is (seq? (get-bundles [:animals :colors])))))
