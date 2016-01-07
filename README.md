# compojure-api-example-clj

Simple Clojure REST API with MongoDB.

###Libraries

- Ring (Http requests, responses, etc..)
- Compojure (routing)
- Compojure-Api ("Stuff on top of Compojure for making sweet web apis)
 - Schema (input and output validation and coercion)
 - Swagger (api documentation via ring-swagger)
 - Friendlier routing
- Cheshire (JSON serializaton) 
- Monger (MongoDB library)

## Usage

### Run the application locally

`lein ring server`

### Run the tests

`lein test`

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`

## License

Copyright ©  FIXME
