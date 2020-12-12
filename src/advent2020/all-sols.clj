(ns advent2020.all-sols
  (:require [clojure.java.io :as io]))

(map #(load-file %) (sort (filter #(<= 0 (.indexOf % "day")) (map str (file-seq (io/file "/home/jchad/Documents/advent2020/src/advent2020"))))))