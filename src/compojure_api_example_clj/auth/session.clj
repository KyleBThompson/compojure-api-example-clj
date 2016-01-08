(ns compojure-api-example-clj.auth.session
  (:require [ring.util.http-response :refer [unauthorized forbidden]]
            [ring.middleware.session :refer [wrap-session]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.accessrules :refer [wrap-access-rules]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]))

(def cookie-name "backend-session")

(def auth-backend
  ; By default responds with 401 or 403 if unauthorized
  (session-backend))

(defn wrap-app-session [handler]
  (-> handler
      (wrap-authorization auth-backend)
      (wrap-authentication auth-backend)
      (wrap-session {:cookie-name cookie-name})))

(defn access-error [req val]
  (unauthorized val))

(defn wrap-rule [handler rule]
  (-> handler
      (wrap-access-rules {:rules [{:pattern #".*"
                                   :handler rule}]
                          :on-error access-error})))