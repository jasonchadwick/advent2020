(ns advent2020.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [advent2020.util :as util]))

(defn char-count [char str]
  (count (re-seq (re-pattern char) str)))

(defn is-valid-pword-1 [char min-n max-n pword]
  (and (<= min-n (char-count char pword)) (>= max-n (char-count char pword))))

(defn is-valid-pword-2 [char idx1 idx2 pword]
  (util/xor (= (first char) (nth pword (dec idx1)))
            (= (first char) (nth pword (dec idx2)))))

(defn is-valid-line [setting line]
  (let [idx1 (s/index-of line \-)
        min-n (Integer/parseInt (subs line 0 idx1))
        idx2 (s/index-of line \space)
        max-n (Integer/parseInt (subs line (inc idx1) idx2))
        char (subs line (inc idx2) (+ 2 idx2))
        pword (subs line (+ 4 idx2))]
    (case setting
      1 (is-valid-pword-1 char min-n max-n pword)
      2 (is-valid-pword-2 char min-n max-n pword))))

(defn num-valid-pwords [filename setting]
  (with-open [rdr (io/reader filename)]
    (count (filter (partial is-valid-line setting) (line-seq rdr)))))

`(~(num-valid-pwords "resources/day2.txt" 1)
  ~(num-valid-pwords "resources/day2.txt" 2))