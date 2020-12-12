(ns advent2020.day9
  (:require [clojure.java.io :as io]
            [advent2020.util :as util]))

(defn get-num-list [fname]
  (vec (map #(Long/valueOf %) (line-seq (io/reader fname)))))

(defn two-nums-sum-to [list num]
  (reduce #(or %1 %2)
   (map (fn [x]
          (reduce #(or %1 %2)
           (map (fn [y]
                  (== num (+ (nth list y) (nth list x))))
                (remove #(== x %) (range (count list)))))) 
        (range (count list)))))

(defn first-invalid-num [num-list idx]
  (if (== (count num-list) idx) 0
      (let [range (range (- idx 25) idx)
            num (nth num-list idx)]
        (if (two-nums-sum-to (map #(nth num-list %) range) num)
          (recur num-list (inc idx))
          num))))

(comment "tried to do this with CPS. apparently Clojure doesn't like it as much as SML does.")
(defn find-sum-range-CPS [list val original-val s k]
  (cond (or (empty? list) (> (first list) val)) (k)
        (== (first list) val) (s (first list))
        :else (recur
               (rest list)
               (- val (first list))
               original-val
               #(s (+ % (first list)))
               #(find-sum-range-CPS (rest list) val original-val s k))))

(defn add-between [list start end]
  (loop [i start acc 0]
    (if (== i end) acc
        (recur (inc i) (+ acc (nth list i))))))

(defn add-sum-range-endpoints [list val]
  (let [list (vec (drop (.indexOf list val) (reverse list)))]
    (loop [start 0 end (count list)]
      (cond (== (add-between list start end) val)
            (let [return (sort > (util/subsequence list start end))]
              (+ (first return) (last return)))
            (== end start) (recur (inc start) (count list))
            :else (recur start (dec end))))))

`(~(first-invalid-num (get-num-list "resources/day9.txt") 25)
  ~(add-sum-range-endpoints (get-num-list "resources/day9.txt") 21806024))