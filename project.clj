(defproject compojure-api-example-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.9.0"] ; required due to bug in lein-ring
                 [metosin/compojure-api "0.22.0"]
                 [com.novemberain/monger "3.0.0-rc2"]]
  :ring {:handler compojure-api-example-clj.handler/app
         :init compojure-api-example-clj.core/init}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [cheshire "5.3.1"]
                                  [ring-mock "0.1.5"]]
                   :plugins [[lein-ring "0.9.6"]]}})