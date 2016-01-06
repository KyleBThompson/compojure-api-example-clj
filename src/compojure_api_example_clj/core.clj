(ns compojure-api-example-clj.core
  (:require [compojure-api-example-clj.post :as post]
            [compojure.handler :as handler]))

(defn init []
  (post/init-db "development"))

(defn wrap-exception-handling
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        {:status 400 :body (.getMessage e)}))))