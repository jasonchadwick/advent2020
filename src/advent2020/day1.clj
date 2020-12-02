(ns day1
  (:require [clojure.java.io :as io]
            [clojure.math.combinatorics :as combinatorics]))

(defn get-product-of-summing-elems [list sum n-terms-added]
  (reduce * (first (filter #(== sum (reduce + %))
                           (apply combinatorics/cartesian-product (repeat n-terms-added list))))))

(defn add-to-2020 [filename n-terms-added]
  (with-open [rdr (io/reader filename)]
    (get-product-of-summing-elems (map #(Integer/parseInt %) (line-seq rdr)) 2020 n-terms-added)))

`(~(add-to-2020 "resources/day1.txt" 2)
  ~(add-to-2020 "resources/day1.txt" 3))