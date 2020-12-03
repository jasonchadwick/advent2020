(ns day3
  (:require [clojure.java.io :as io]
            [util]))

(defn load-map [fname]
  (with-open [rdr (io/reader fname)]
    (to-array-2d (line-seq rdr))))

(defn count-trees [r-cur c-cur r-move c-move tree-count m]
  (let [rows (count m)
        cols (count (aget m 0))
        r-cur (+ r-cur r-move)
        c-cur (+ c-cur c-move)]
    (cond
      (>= r-cur rows) tree-count
      (>= c-cur cols) (count-trees r-cur (mod c-cur cols) r-move c-move
                                 (if (= \# (aget m r-cur (mod c-cur cols)))
                                   (inc tree-count)
                                   tree-count)
                                 m)
      :else (count-trees r-cur c-cur r-move c-move
                       (if (= \# (aget m r-cur c-cur))
                         (inc tree-count)
                         tree-count)
                       m))))

(defn num-trees [r-move c-move]
  (count-trees 0 0 r-move c-move 0 (load-map "resources/day3.txt")))

(defn try-slopes []
  (reduce * (map #(apply num-trees %) '((1 1) (1 3) (1 5) (1 7) (2 1)))))

`(~(num-trees 1 3)
  ~(try-slopes))