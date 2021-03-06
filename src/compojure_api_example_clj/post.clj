(ns compojure-api-example-clj.post
  (:require [schema.core :as s]
  	        [monger.collection :as mc]
            [monger.core :as mg])
  (:import [org.bson.types ObjectId]))

(s/defschema Post {:_id String
                   :title String
                   :body String
                   :publish (s/enum :True :False)})
(s/defschema NewPost (dissoc Post :_id))
(s/defschema PostId {:id String})

(defn init-db [name]
  (def conn (mg/connect))
  (def db   (mg/get-db conn name)))

(defn to-resource [post]
  (select-keys post [:_id :title :body :publish]))

(defn to-resource-list [posts]
  (map to-resource posts))

(defn get-posts0 []
  (let [posts (mc/find-maps db "posts" {})]
    (println posts)
  posts))

(defn get-posts []
  (let [posts (mc/find-maps db "posts" {})]
    (println posts)
  (to-resource-list  posts)))

(defn get-post0 [id]
  (let [object-id (ObjectId. id)]
  (mc/find-one-as-map db "posts" { :_id object-id })))

(defn get-post [id]
  (let [object-id (ObjectId. id)
        post (mc/find-one-as-map db "posts" { :_id object-id })]
  (to-resource post)))

(defn add! [post]
  (let [id (ObjectId.)]
    (println post)
    (mc/insert db "posts" (assoc post :_id id))
    {:id (str id)}))

(defn update! [post]
  (println (str "Post ID is: " (:_id post)))
  (let [id (ObjectId. (:_id post))]
    (mc/update-by-id db "posts" id (assoc post :_id id))
    post))

(defn delete! [id]
  (let [object-id (ObjectId. id)]
  (mc/remove-by-id db "posts" object-id)) nil)