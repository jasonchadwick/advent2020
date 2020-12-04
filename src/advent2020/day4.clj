(ns day4
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as sets]
            [util]))

(defn get-ID-list [fname]
  (with-open [rdr (io/reader fname)]
    (string/split (string/join " " (line-seq rdr)) #"  ")))

(defn parse-key-vals [string map keys]
  (let [k (first keys)
        idx (string/index-of string k)
        end-idx (when idx (or (#(when % (+ % idx)) (string/index-of (subs string idx) " ")) (count string)))
        map-new (if idx
                  (assoc map k (subs string (+ idx (count k) 1) end-idx))
                  map)]
    (if (empty? (rest keys))
      map-new
      (parse-key-vals string map-new (rest keys)))))

(defn ID-list-to-attribute-maps [list keys]
  (map #(parse-key-vals % {} keys) list))

(defn num-valid [fname keys validation-fn]
  (count 
   (filter 
    (fn [ID] (reduce #(and %1 %2) (map #(validation-fn % (get ID %)) keys)))
    (ID-list-to-attribute-maps
     (get-ID-list fname)
     keys))))

(defn valid-1 [_ v]
  v)

(defn valid-2 [k v]
  (when (> (count v) 0)
    (case k
      "byr" (and (util/is-numeric v) (<= 1920 (Integer/parseInt v) 2002))
      "iyr" (and (util/is-numeric v) (<= 2010 (Integer/parseInt v) 2020))
      "eyr" (and (util/is-numeric v) (<= 2020 (Integer/parseInt v) 2030))
      "hgt" (and (>= (count v) 4)
             (let [units (subs v (- (count v) 2))
                   height-str (subs v 0 (- (count v) 2))
                   height (when (util/is-numeric height-str) (Integer/parseInt height-str))]
               (case units
                 "cm" (<= 150 height 193)
                 "in" (<= 59 height 76)
                 nil)))
      "hcl" (and (util/is-hex (rest v)) (= \# (first v)) (== 7 (count v)))
      "ecl" (sets/subset? #{v} (set '("amb" "blu" "brn" "gry" "grn" "hzl" "oth")))
      "pid" (and (util/is-numeric v) (== 9 (count v))))))

(num-valid "resources/day4.txt" '("byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid") valid-1)
(num-valid "resources/day4.txt" '("byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid") valid-2)



(parse-key-vals (first (get-ID-list "resources/day4.txt")) {} '("byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid" "cid"))

(apply + '(1 2))