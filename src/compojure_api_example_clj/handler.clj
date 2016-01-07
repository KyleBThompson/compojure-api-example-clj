(ns compojure-api-example-clj.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [compojure-api-example-clj.core :as core]
            [compojure-api-example-clj.post :as post :refer :all]
            [cheshire.generate :refer [add-encoder encode-str]]))

(add-encoder org.bson.types.ObjectId encode-str)

(s/defschema Message {:message String})

(defapi app
  (middlewares [core/wrap-exception-handling]
  (swagger-ui)
  (swagger-docs
    {:info {:title "Compojure-api-example-clj"
            :description "Compojure Api example"}
     :tags [{:name "hello", :description "says hello to the world"}
            {:name "echo", :description "request echoes"}
            {:name "posts", :description "worst blogging app api ever"}
            {:name "exception", :description "throw an exception"}]})

  (context* "/hello" []
    :tags ["_hello"]

    (GET* "/" []
      :return Message
      :summary "say hello world"
      (ok {:message "Hello World"})))

  (context* "/echo" []
    :tags ["echo"]

    (GET* "/hello" []
      :return String
      :query-params [name :- String]
      (ok (str "Hello, " name))))

  (context* "/posts" []
    :tags ["posts"]

    (GET* "/" []
      :summary "Get all posts"
      :swagger {:responses {200 {:schema [Post]}}}
      (ok (post/get-posts)))

    (GET* "/:id" []
      :path-params [id :- String]
      :summary "Get post"
      (ok (post/get-post id)))

    (POST* "/" []
      :summary "Adds a post"
      :return PostId
      :body [post NewPost {:description "new post"}]
      (ok (post/add! post)))

    (PUT* "/" []
      :summary "Updates a post"
      :return Post
      :body [post Post]
      (ok (post/update! post)))

    (DELETE* "/:id" []
      :path-params [id :- String]
      :summary "Deletes a post"
      (ok (post/delete! id))))

  (context* "/exception" []
    :tags ["exception"]

    (GET* "/boom" [] 
      (throw (RuntimeException. "Something blew up"))))))  