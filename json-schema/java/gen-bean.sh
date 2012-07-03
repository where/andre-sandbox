
# Generates a Java bean from a JSON schema

. ./common.env

PGM=com.andre.jsonschema.bean.BeanGenerator

schema=../test-data/schema.json
pkg=com.where.pphere.api.data
class=Offer

java $PROPS -classpath $CPATH $PGM -s $schema -p $pkg -c $class 

