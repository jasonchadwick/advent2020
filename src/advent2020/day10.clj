(ns advent2020.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [advent2020.util :as util]))

(defn get-differences [list]
  (map #(- (nth list (inc %)) (nth list %)) (range (dec (count list)))))

(defn count-identical-ints [list]
  (let [results (vec (repeat (inc (apply max list)) 0))]
    (loop [list list res results]
      (if (empty? list) res
          (recur (rest list)
                 (assoc res (first list) (inc (nth res (first list)))))))))

(defn part-1 [fname]
  (let [sorted (cons 0 (sort < (map #(Integer/parseInt %) (line-seq (io/reader fname)))))
        sorted-1 (concat sorted `(~(+ 3 (apply max sorted))))
        results (count-identical-ints (get-differences sorted-1))]
    (* (nth results 1) (nth results 3))))

(defn sequences-of-1s [interval-list]
  (let [ones (map count (string/split (string/join (map str interval-list)) #"3"))
        removal-permutations (map #(if (zero? %) 1
                                       (- (util/expt 2 (dec %)) (if (>= % 4) (- % 3) 0))) ones)]
    (reduce * removal-permutations)))

(defn part-2 [fname]
  (let [sorted (cons 0 (sort < (map #(Integer/parseInt %) (line-seq (io/reader fname)))))
        sorted-1 (concat sorted `(~(+ 3 (apply max sorted))))
        diff (get-differences sorted-1)]
    (sequences-of-1s diff)))

`(~(part-1 "resources/day10.txt")
  ~(part-2 "resources/day10.txt"))