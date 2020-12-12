(ns advent2020.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set]))

(defn get-answer-list [fname]
  (with-open [rdr (io/reader fname)]
    (map #(string/split % #" ")
         (string/split (string/join " " (line-seq rdr)) #"  "))))

(defn count-unique-chars [strs-list]
  (reduce + (map #(-> % string/join set count) strs-list)))

(defn count-shared-chars [strs-list]
  (reduce + (map 
             #(count (apply clojure.set/intersection (map set %))) 
             strs-list)))

`(~(count-unique-chars (get-answer-list "resources/day6.txt"))
  ~(count-shared-chars (get-answer-list "resources/day6.txt")))