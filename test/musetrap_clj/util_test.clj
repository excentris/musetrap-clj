(ns musetrap-clj.util-test
  (:require [clojure.test :refer :all]
            [musetrap-clj.util :as util]))

(deftest deep-merge-test 
  (testing "we correctly merge some nested maps"
    (let [a {:en {:a "a" :b "b"}}
          b {:en {:c "c" :d "d"} :es {:x "x"}}
          c {:es {:y "y"}}]
      (is (= {:en {:a "a", :b "b", :c "c", :d "d"}, :es {:x "x", :y "y"}}
             (util/deep-merge a b c))))))
