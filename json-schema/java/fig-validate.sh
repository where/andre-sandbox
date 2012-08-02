
# Validate some fig samples

. ./common.env

PGM=com.andre.jsonschema.JsonValidatorDriver

schemaDir=../json-schemas/fig
dataDir=../json-schemas/fig/samples

java $PROPS -classpath $CPATH $PGM $schemaDir/Store.json $dataDir/store.json | tee log.txt

