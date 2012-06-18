
REST API URLs
  http://localhost:8080/pphere/api
  http://localhost:8080/pphere/api/offers?radius=11&lat=-56.93&lng=-67.62
  http://localhost:8080/pphere/api/offers/1
  http://localhost:8080/pphere/api/stores
  http://localhost:8080/pphere/api/stores/1
  http://localhost:8080/pphere/api/?_wadl&_type=xml

Getting started with curl
  ant
  run.jetty.sh
  cd curl
  get-offers.sh

Tests
  run-jetty.sh - in one window
  ant test - in other window
  Test results in build/test-output/index.html

Scripts
  gen-schema.sh - generates schema from annotated JAXB data classes - output in tmp/
  run-jetty.sh - Launches Jetty HTTP server with sample war as http://localhost:8080/pphere/api

