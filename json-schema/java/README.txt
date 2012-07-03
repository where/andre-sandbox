
Overview
  Tests various Java JSON Schema providers.

Directories
  test-data - Sample schema and instance files
  sample-report - Sample TestNG report
  src - Java source

Ant targets
  ant test - run test (equalExpertsJsonValidator)
  ant -DjsonValidator=equalExpertsJsonValidator
  ant -DjsonValidator=vahlasJsonValidator

Scripts
  validate.sh - Validates a JSON instance file against a schema
  ref-validate-schema.sh - Validate a schema against reference schema. Bombs...
  gen-bean.sh - Generates a Java bean from a JSON schema

Two JSON schema validator providers:

  equalExpertsJsonValidator 
    Works with Schema version 3
    https://github.com/EqualExperts/json-schema-validator

  vahlasJsonValidator 
    Works with Schema version 2
    http://gitorious.org/json-schema-validation-in-java

Avoid vahlasJsonValidator - use equalExpertsJsonValidator. 
The main difference is that v.2 'optional' has been replaced in v. 3 with 'required'

http://tools.ietf.org/html/draft-zyp-json-schema-02#section-5.4

  5.4.  optional

     This indicates that the instance property in the instance object is
     optional.  This is false by default.

http://tools.ietf.org/html/draft-zyp-json-schema-03#section-5.7

  5.7.  required

     This attribute indicates if the instance must have a value, and not
     be undefined.  This is false by default, making the instance optional.

