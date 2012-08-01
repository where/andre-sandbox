
# Generates a Java bean from a JSON schema

. ./common.env

PGM=com.andre.jsonschema.bean.BeanGeneratorDriver

#schema=../test-data/schema.json

class=Location
class=Address
class=Store
schema=../json-schemas/fig/$class.json
pkg=com.where.pphere.api.data

OPTS="--jaxb"

java $PROPS -classpath $CPATH $PGM -s $schema -p $pkg -c $class $OPTS

