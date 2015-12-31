(ns compojure-api-example-clj.post
  (:require [schema.core :as s]
  	        [monger.collection :as mc]
            [monger.core :as mg])
  (:import [org.bson.types ObjectId]))

(s/defschema Post {:_id String
                   :title String
                   :body String})
(s/defschema NewPost (dissoc Post :_id))

(defn init-db [name]
  (def conn (mg/connect))
  (def db   (mg/get-db conn name)))

(defn get-posts []
  (mc/find-maps db "posts" {}))

(defn get-post [id]
  (let [object-id (ObjectId. id)]
  (mc/find-one-as-map db "posts" { :_id object-id })))

(defn add [post]
  (let [id (ObjectId.)]
    (mc/insert db "posts" (assoc post :_id id))
    post))

(defn update [post]
  (println (str "Post ID is: " (:_id post)))
  (let [id (ObjectId. (:_id post))]
    (mc/update-by-id db "posts" id (assoc post :_id id))
    post))

(defn delete [id]
  (let [object-id (ObjectId. id)]
  (mc/remove-by-id db "posts" object-id)) nil)