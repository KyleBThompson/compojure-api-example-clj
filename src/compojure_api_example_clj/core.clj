(ns compojure-api-example-clj.core
  (:require [compojure-api-example-clj.post :as post]
            [compojure.handler :as handler]))

(defn init []
  (post/init-db "development"))