
# Validate some fig JSON Schema samples

bdir=../../../java
CPATH=.
CPATH="$CPATH:$bdir/config"
CPATH="$CPATH:$bdir/build/classes"

for file in `find $bdir/lib -name *.jar` ; do
  CPATH="$CPATH:$file" ; done

PGM=com.andre.jsonschema.JsonValidatorDriver

schemaDir=..
instanceDir=.

java $PROPS -classpath $CPATH $PGM $schemaDir/Store.json $instanceDir/store.json | tee log.txt

#java $PROPS -classpath $CPATH $PGM $schemaDir/Store.json $instanceDir/badfiles/bad-store.json | tee log.txt
