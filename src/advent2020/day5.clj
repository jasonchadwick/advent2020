(ns day5
  (:require [clojure.java.io :as io]
            [clojure.set]
            [util]))

(defn get-row [str min max]
  (if (empty? str) min
      (case (first str)
        (\F \L) (get-row (rest str) min (/ (+ max min) 2))
        (\B \R) (get-row (rest str) (/ (+ max min) 2) max))))

(defn get-seat-ID [seat]
  (+ (* (get-row (subs seat 0 7) 0 128) 8) (get-row (subs seat 7) 0 8)))

(defn max-seat-ID [fname]
  (util/max-list (map get-seat-ID (line-seq (io/reader fname)))))

(defn find-missing-seat [fname]
  (let [IDs (map get-seat-ID (line-seq (io/reader fname)))
        max (util/max-list IDs)
        min (util/min-list IDs)]
    (first (clojure.set/difference (set (range min (inc max))) (set IDs)))))

`(~(max-seat-ID "resources/day5.txt")
  ~(find-missing-seat "resources/day5.txt"))