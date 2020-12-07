(ns all-sols
  (:require [clojure.java.io :as io]
            [util]))

(map #(load-file %) (sort (filter #(<= 0 (.indexOf % "day")) (map str (file-seq (io/file "/home/jchad/Documents/advent2020/src/advent2020"))))))