
REST API URLs
  http://localhost:8080/pphere/api
  http://localhost:8080/pphere/api/offers
  http://localhost:8080/pphere/api/stores
  http://localhost:8080/pphere/api/stores/1
  http://localhost:8080/pphere/api/?_wadl&_type=xml

Getting started
  ant
  run.jetty.sh
  cd curl
  get-offers.sh

Tests
  run-jetty.sh
  ant test
  Test results in build/test-output/index.html

Scripts
  gen-schema.sh - generates schema from annotated JAXB data classes - output in tmp/
  run-jetty.sh - Launches Jetty HTTP server with sample war as http://localhost:8080/pphere/api

