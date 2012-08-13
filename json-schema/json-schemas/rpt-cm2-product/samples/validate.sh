
# Validate 

bdir=../../../java
CPATH=.
CPATH="$CPATH:$bdir/config"
CPATH="$CPATH:$bdir/build/classes"

for file in `find $bdir/lib -name *.jar` ; do
  CPATH="$CPATH:$file" ; done

PGM=com.andre.jsonschema.JsonValidatorDriver

schemaDir=..
instanceDir=.

java $PROPS -classpath $CPATH $PGM $schemaDir/ProductReport.json $instanceDir/ProductReport.json | tee log.txt
#java $PROPS -classpath $CPATH $PGM $schemaDir/Day.json $instanceDir/Day.json | tee log.txt
#java $PROPS -classpath $CPATH $PGM $schemaDir/BuyItem.json $instanceDir/BuyItem.json | tee log.txt

#java $PROPS -classpath $CPATH $PGM $schemaDir/Day.json $instanceDir/badfiles/Day-badTime.json | tee log.txt
