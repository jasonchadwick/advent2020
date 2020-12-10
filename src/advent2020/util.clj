(ns util)

(defmacro spy [& body]
  `(let [x# ~@body]
     (printf "=> %s = %s\n" (first '~body) x#)
     x#))

(defmacro xor [a b]
  `(let [a# ~a
         b# ~b]
     (or (and a# (not b#))
         (and (not a#) b#))))

(defn safe-nth [str n]
  (if (>= n (count str)) nil
      (nth str n)))

(defn is-numeric [str]
    (empty? (filter #(not (<= 48 (int %) 58)) str)))

(defn is-hex [str]
  (empty? (filter #(not (or (<= 48 (int %) 58) 
                            (<= 65 (int %) 70) 
                            (<= 97 (int %) 102))) 
                  str)))

(defn max-list [l]
  (reduce #(if (>= %1 %2) %1 %2) l))

(defn min-list [l]
  (reduce #(if (<= %1 %2) %1 %2) l))

(defn zip [a b]
  (map vector a b))

(defn subsequence [list start end]
  (drop start (take end list)))

(defn fact [n]
  (loop [n n k 1]
    (if (zero? n) k
        (recur (dec n) (* k n)))))

(defn expt [x y]
  (loop [y y k 1]
    (if (zero? y) k
        (recur (dec y) (* k x)))))