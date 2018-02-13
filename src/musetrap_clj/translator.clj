(ns musetrap-clj.translator
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [musetrap-clj.util :as util]))

(def ^:private ingredients-dictionary
  (apply util/deep-merge 
         (map #(json/read-str % :key-fn keyword)  
              (map slurp 
                   (map io/as-url 
                        (util/files-in-dir (io/resource "musetrap-data/i18n/ingredients")))))))

(defn translate-ingredient
  [query default]
  (get-in ingredients-dictionary query default))
