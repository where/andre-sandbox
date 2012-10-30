
# Dump keys and values

. ./common.env

CPATH="$CPATH:conf/vtest"
PGM=com.amm.vtest.plugins.datagen.KeyValueGeneratorDriver

max=10
if [ $# -gt 0 ] ; then
  max=$1
  fi

#OPTS="$OPTS --filterIndex 5"
OPTS="$OPTS --showIndex"
OPTS="$OPTS --showOnlyValueSize"
#OPTS="$OPTS --donotShowData"

kbean=randomKeyGeneratorFail
kbean=fixedKeyGenerator
kbean=MD5KeyGenerator
kbean=uuidKeyGenerator
kbean=randomKeyGenerator
OPTS="$OPTS --beanKeyGenerator $kbean"

vbean=fixedValueGenerator
vbean=randomValueGenerator
vbean=randomValueSizeGenerator
OPTS="$OPTS --beanValueGenerator $vbean"

configFile=appContext-datagen.xml

#echo "PROPS=$PROPS"

java $PROPS -cp $CPATH $PGM $OPTS -m $max -c $configFile | tee log.txt
cp log.txt keyvalues-dump.txt
