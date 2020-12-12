(ns advent2020.day7
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [advent2020.util :as util]))

(defn bags-containing-bag [bag-list bag-seq cumulative-num]
  (if (empty? bag-seq) cumulative-num
      (let [bag (first bag-seq)
            containing-lines (filter #(>= (.indexOf % bag) 1) bag-list)
            remaining-lines (remove #(>= (.indexOf % bag) 1) bag-list)
            containing-bags (map #(subs % 0 (dec (.indexOf % "bags"))) containing-lines)]
        (bags-containing-bag remaining-lines (concat (rest bag-seq) containing-bags) (+ cumulative-num (count containing-bags))))))

(defn bags-containing-gold [fname]
  (bags-containing-bag (line-seq (io/reader fname)) '("shiny gold") 0))

(defn bags-inside-bag-1 [bag-list bag num-bags]
  (if (<= 0 (.indexOf (first (filter #(== (.indexOf % bag) 0) bag-list)) "no other bags"))
    num-bags
    (let [bag-line (first (filter #(== (.indexOf % bag) 0) bag-list))
          bags-inside-str (subs bag-line (+ 8 (.indexOf bag-line "contain")))
          bags-inside-list (string/split bags-inside-str #", ")
          colors-inside (map #(subs % (inc (.indexOf % " ")) (dec (.indexOf % "bag"))) bags-inside-list)
          nums-inside (map #(Integer/parseInt (subs % 0 (.indexOf % " "))) bags-inside-list)
          colors-nums-inside (util/zip colors-inside nums-inside)]
      (* num-bags
         (reduce + 1 (map
                      #(bags-inside-bag-1 bag-list (first %) (second %))
                      colors-nums-inside))))))

(defn bags-inside-bag [bag-list bag]
  (dec (bags-inside-bag-1 bag-list bag 1)))

(defn bags-in-gold [fname]
  (bags-inside-bag (line-seq (io/reader fname)) "shiny gold"))

`(~(bags-containing-gold "resources/day7.txt")
  ~(bags-in-gold "resources/day7.txt"))