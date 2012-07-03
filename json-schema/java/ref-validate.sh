
# Validate a schema against reference schema. Bombs...

. ./common.env

PGM=com.andre.jsonschema.JsonValidatorDriver

schema=../ref/json-ref.json
file=../test-data/schema.json

if [ $# -gt 0 ] ; then
  file=$1
  fi

java $PROPS -classpath $CPATH $PGM $schema $ddir/$file | tee oo

