
# Dump some keys

. ./common.env

CPATH="$CPATH:conf/vtest"

PGM=com.amm.vtest.plugins.datagen.KeyGeneratorDriver

daoConfigFile=tasks-keyvalue.xml
PROPS="$PROPS -DdaoConfigFile=$daoConfigFile"
PROPS="$PROPS -DproviderConfigFile=$providerConfigFile"

max=1000000
max=10

#bean=fixedKeyGenerator
#bean=MD5KeyGenerator
bean=randomKeyGenerator

configFiles=vtest.xml

#echo "PROPS=$PROPS"

java $PROPS -cp $CPATH $PGM -m $max -b $bean -c $configFiles | tee oo

