(ns compojure-api-example-clj.auth.restructure
  (:require [compojure.api.meta :refer [restructure-param]]
            [compojure-api-example-clj.auth.session :refer [wrap-rule]]
            [compojure-api-example-clj.auth.access :as access]))

(defmethod restructure-param :auth-rules
  [_ rule acc]
  (update-in acc [:middlewares] conj `(wrap-rule ~rule)))

(defmethod restructure-param :current-user
  [_ binding acc]
  (update-in acc [:letks] into [binding `(:identity ~'+compojure-api-request+)]))