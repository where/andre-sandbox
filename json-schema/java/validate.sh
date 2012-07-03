
# Validates a JSON instance file against a schema

. ./common.env

PGM=com.andre.jsonschema.JsonValidatorDriver

schema=../test-data/schema.json
file=../test-data/okfiles/ok.json

if [ $# -gt 0 ] ; then
  file=$1
  file=../test-data/okfiles/$1
  fi

java $PROPS -classpath $CPATH $PGM $schema $ddir/$file | tee oo

