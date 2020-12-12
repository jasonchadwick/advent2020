(ns advent2020.day12
  (:require [clojure.java.io :as io]))

(defn get-move-list [fname]
  (map (fn [x] `(~(first x) ~(Integer/parseInt (subs x 1)))) (line-seq (io/reader fname))))

(defn rotate [dir-x dir-y command angle]
  (cond (zero? angle) `(~dir-x ~dir-y)
        (= \R command) (recur dir-y (- dir-x) command (- angle 90))
        :else (recur (- dir-y) dir-x command (- angle 90))))

(defn get-manhattan-dist [movelist]
  (loop [x 0 y 0 dir-x 1 dir-y 0 moves movelist]
    (if (empty? moves)
      (+ (Math/abs x) (Math/abs y))
      (let [command (first (first moves))
            dist (second (first moves))]
        (case command
          \F (recur (+ x (* dist dir-x)) (+ y (* dist dir-y)) dir-x dir-y (rest moves))
          \N (recur x (+ y dist) dir-x dir-y (rest moves))
          \E (recur (+ x dist) y dir-x dir-y (rest moves))
          \S (recur x (- y dist) dir-x dir-y (rest moves))
          \W (recur (- x dist) y dir-x dir-y (rest moves))
          (recur x y
                 (first (rotate dir-x dir-y command dist))
                 (second (rotate dir-x dir-y command dist)) (rest moves)))))))

(defn get-manhattan-dist-1 [movelist]
  (loop [x 0 y 0 w-x 10 w-y 1 moves movelist]
    (if (empty? moves)
      (+ (Math/abs x) (Math/abs y))
      (let [command (first (first moves))
            dist (second (first moves))]
        (case command
          \F (recur (+ x (* dist w-x)) (+ y (* dist w-y)) w-x w-y (rest moves))
          \N (recur x y w-x (+ w-y dist) (rest moves))
          \E (recur x y (+ w-x dist) w-y (rest moves))
          \S (recur x y w-x (- w-y dist) (rest moves))
          \W (recur x y (- w-x dist) w-y (rest moves))
          (recur x y
                 (first (rotate w-x w-y command dist))
                 (second (rotate w-x w-y command dist)) (rest moves)))))))

`(~(get-manhattan-dist (get-move-list "resources/day12.txt"))
  ~(get-manhattan-dist-1 (get-move-list "resources/day12.txt")))