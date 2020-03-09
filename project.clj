(defproject my-stuff "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "https://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.stuartsierra/component  "0.4.0"]
                 [honeysql  "0.9.9"]
                 [org.clojure/java.jdbc  "0.7.11"]
                 [org.clojure/tools.namespace  "1.0.0"]
                 [org.postgresql/postgresql  "42.2.10"]
                 [compojure  "1.6.1"]
                 [ring  "1.8.0"]
                 [metosin/compojure-api  "1.1.13"]
                 [nilenso/honeysql-postgres  "0.2.6"]]
  :main ^:skip-aot main/-main
  :target-path "target/%s"
  :repl-options {:init-ns user}
  :profiles {:uberjar {:aot :all}
             :dev {:source-paths ["dev"]}})
