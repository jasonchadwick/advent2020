(ns advent2020.day1
  (:require [clojure.java.io :as io]
            [clojure.math.combinatorics :as combinatorics]))

(defn product-of-elems-sum-to-val [list sum n-terms-added]
  (reduce * (first (filter #(== sum (reduce + %))
                           (apply combinatorics/cartesian-product (repeat n-terms-added list))))))

(defn add-to-2020 [filename n-terms-added]
  (with-open [rdr (io/reader filename)]
    (product-of-elems-sum-to-val (map #(Integer/parseInt %) (line-seq rdr)) 2020 n-terms-added)))

`(~(add-to-2020 "resources/day1.txt" 2)
  ~(add-to-2020 "resources/day1.txt" 3))