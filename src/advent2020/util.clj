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