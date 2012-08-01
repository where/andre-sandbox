
# Validates a JSON instance file against a schema

. ./common.env

PGM=com.andre.jsonschema.JsonValidatorDriver

schema=../test-data/basic/schema.json
file=../test-data/basic/okfiles/ok.json
file=../test-data/basic/okfiles/null-property.json

if [ $# -gt 0 ] ; then
  file=$1
  file=../test-data/okfiles/$1
  fi

java $PROPS -classpath $CPATH $PGM $schema $ddir/$file | tee oo

