(ns advent2020.day11
  (:require [clojure.java.io :as io]
            [advent2020.util :as util]))

(defn chars-to-nums [c]
  (case c
    \L 0
    \# 1
    \. 2))

(defn get-array [fname]
  (util/arr-ref (util/deep-map chars-to-nums (util/map-vec vec (vec (line-seq (io/reader fname)))) 2) 2))

(defn occupied? [arr r c]
  (if
   (or (< r 0) (< c 0) (>= r (count arr)) (>= c (count (first arr))))
    0
    (mod (util/aref arr r c) 2)))

(defn num-adjacent-seats [arr r c]
  (reduce + (map #(occupied? arr (+ r (first %)) (+ c (second %)))
                 '((-1 -1) (-1 0) (-1 1)
                   (0 -1)         (0 1)
                   (1 -1)  (1 0)  (1 1)))))

(defn occupied-direction? [arr dir r c]
  (let [r (+ r (first dir))
        c (+ c (second dir))]
    (if (or (< r 0) (< c 0) (>= r (count arr)) (>= c (count (first arr))))
           0
           (case (util/aref arr r c)
             0 0
             1 1
             2 (recur arr dir r c)))))

(defn num-directional-seats [arr r c]
  (reduce + (map #(occupied-direction? arr % r c)
                 '((-1 -1) (-1 0) (-1 1)
                   (0 -1)         (0 1)
                   (1 -1)  (1 0)  (1 1)))))

(comment "returns a list: (n_changes, new_arr)")
(defn do-step [arr directional?]
  (let [prev-arr (util/arr-deref arr 2)]
    (loop [r (dec (count arr))
           n-changes 0]
      (if (< r 0)
        `(~n-changes ~arr)
        (recur
         (dec r)
         (loop [c (dec (count (first arr)))
                n-changes-1 n-changes]
           (if (< c 0)
             n-changes-1
             (recur
              (dec c)
              (when (not= 2 (util/aref arr r c))
                (let [seats (if directional?
                              (num-directional-seats prev-arr r c)
                              (num-adjacent-seats prev-arr r c))]
                  (cond (and (zero? @(util/aref arr r c)) (zero? seats))
                        (do (reset! (util/aref arr r c) 1)
                            (inc n-changes-1))
                        (and (== 1 @(util/aref arr r c)) (>= seats (if directional? 5 4)))
                        (do (reset! (util/aref arr r c) 0)
                            (inc n-changes-1))
                        :else n-changes-1)))))))))))

(defn count-occupied [arr]
  (reduce + (util/map-vec (fn [x] (reduce + (filter #(== 1 %) x))) arr)))

(defn print-seats [arr]
  (util/map-vec (fn [x] 
                  (util/map-vec
                   #(print
                     (case %
                       0 \L
                       1 \#
                       2 \.)) x)
                  (println))
                arr))

(defn num-final-seats [arr directional?]
  (loop [arr arr steps 0]
    (let [result (do-step arr directional?)]
      (if (zero? (first result))
        (count-occupied (util/arr-deref (second result) 2))
        (recur (second result) (inc steps))))))

(defn do-n-steps [arr n directional?]
  (if (<= n 0) (util/arr-deref arr 2)
      (recur (second (do-step arr directional?)) (dec n) directional?)))

`(~(num-final-seats (get-array "resources/day11.txt") false)
  ~(num-final-seats (get-array "resources/day11.txt") true))