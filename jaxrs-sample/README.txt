
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
  Test results in build/test-output/html/index.html

Scripts
  gen-schema.sh - Generates schema from annotated JAXB data classes - output in tmp/
  run-jetty.sh - Launches Jetty HTTP server with sample war as http://localhost:8080/pphere/api

ant
  if you want the default ant target to deploy your WAR, set deploy.dir in user.properties. For example:
     deploy.dir=$TOMCAT_HOME/webapps

Data Format
  JSON and XML are used in this sample. Traditional JAXB annotations are used.
  Validation is also used - see config/cxf/cxf.xml for configuration.
  Note that JSON validation is based on XML XSD - quite problematic.
  See: http://www.mnot.net/blog/2012/04/13/json_or_xml_just_decide

JSON Format with CXF

  http://cxf.apache.org/docs/json-support.html
  http://cxf.apache.org/docs/jax-rs-data-bindings.html#JAX-RSDataBindings-JSONsupport

  CXF has ability to use different JSON providers each with a different format.
  Default Jettison provider format differs from Jackson provider. This API uses the latter.
  See config/cxf/cxf.xml for configuration.

  Example for GET store/0

    Jettison:
      {
          "store": {
              "accountId": "EMS", 
              "id": 0, 
              "location": {
                  "city": "Allston", 
                  "neighborhood": "Allston", 
                  "state": "MA", 
                  "street1": "1041 Comm Ave", 
                  "zip": "02115"
              }, 
              "name": "EMS Comm Ave", 
              "phoneNumber": "617 123-0000", 
              "providerStoreId": "psid-1776"
          }
      }

    Jackson:
      {
          "accountId": "EMS", 
          "id": 0, 
          "location": {
              "city": "Allston", 
              "neighborhood": "Allston", 
              "state": "MA", 
              "street1": "1041 Comm Ave", 
              "zip": "02115"
          }, 
          "name": "EMS Comm Ave", 
          "phoneNumber": "617 123-0000", 
          "providerStoreId": "psid-1776"
      }

  Example for GET store

    Jettison:
      {
          "stores": {
              "store": [
                  { 
                      "accountId": "EMS",
                      "id": 0,
                      "name": "EMS"
                  }, 
                  {
                      "accountId": "REI", 
                      "id": 1, 
                      "name": "REI"
                  }, 
                  {
                      "accountId": "Hiltons", 
                      "id": 2, 
                      "name": "Hiltons"
                  }
              ]
          }
      }

    Jackson
      {
          "link": [],
          "store": [
              {
                  "accountId": "EMS",
                  "id": 0,
                  "name": "EMS"
              },
              {
                  "accountId": "REI",
                  "id": 1,
                  "name": "REI"
              },
              {
                  "accountId": "Hiltons",
                  "id": 2,
                  "name": "Hiltons"
              }
          ]
      }
