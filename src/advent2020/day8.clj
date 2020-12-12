(ns advent2020.day8
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(defn get-instructions [fname]
  (vec (map #(as-> % x
               (string/split x #" ")
               `(~(first x) ~(Integer/parseInt (second x))))
            (line-seq (io/reader fname)))))

(comment "Recursively runs the code.
          Returns exit code 0 with acc if code finishes normally,
          or exit code 1 with acc if code would loop infinitely.")
(defn run-code-1 [ptr acc instr prev-instr]
  (cond (<= (count instr) ptr) `(0 ~acc)
        (nth prev-instr ptr) `(1 ~acc)
        :else (let [ins (first (nth instr ptr))
                    num (second (nth instr ptr))]
                (case ins
                  "acc" (run-code-1 (inc ptr) (+ acc num) instr (assoc prev-instr ptr true))
                  "jmp" (run-code-1 (+ ptr num) acc instr (assoc prev-instr ptr true))
                  "nop" (run-code-1 (inc ptr) acc instr (assoc prev-instr ptr true))))))

(defn run-code [instr]
  (run-code-1 0 0 instr (vec (repeat (count instr) false))))

(defn change-instr [instr ptr]
  (let [ins (nth instr ptr)]
    (case (first ins)
      "jmp" (assoc instr ptr `("nop" ~(second ins)))
      "nop" (assoc instr ptr `("jmp" ~(second ins))))))

(defn find-code-fix [instr ptr]
  (if (or (= "jmp" (first (nth instr ptr)))
          (= "nop" (first (nth instr ptr))))
    (let [result (run-code (change-instr instr ptr))]
      (case (first result)
        0 (second result)
        1 (find-code-fix instr (inc ptr))))
    (find-code-fix instr (inc ptr))))

`(~(second (run-code (get-instructions "resources/day8.txt")))
  ~(find-code-fix (get-instructions "resources/day8.txt") 0))