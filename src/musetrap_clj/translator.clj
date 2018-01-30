(ns musetrap-clj.translator
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def ^:private dictionary (edn/read-string (slurp (io/resource "i18n/dictionary.edn"))))

(defn translate
  [query default]
  (get-in dictionary query default))
