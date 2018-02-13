(ns musetrap-clj.util
  (:require [clojure.java.io :as io]))

(defn deep-merge
  "Recursively merges maps. If vals are not maps, the last value wins."
  [& vals]
  (if (every? map? vals)
    (apply merge-with deep-merge vals)
    (last vals)))

(defn files-in-dir
  "Get the files in the specified dir."
  [dir]
  (let [dir (io/file dir)]
    (filter #(.isFile %) (file-seq dir))))
