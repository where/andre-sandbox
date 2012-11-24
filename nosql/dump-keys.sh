
# Dump some keys

. ./common.env

CPATH="$CPATH:conf/vtest"

PGM=com.amm.vtest.plugins.datagen.driver.KeyGeneratorDriver

max=10
if [ $# -gt 0 ] ; then
  max=$1
  fi

OPTS="$OPTS --index"

#bean=fixedKeyGenerator
#bean=MD5KeyGenerator
bean=randomKeyGenerator

configFiles=appContext-datagen.xml

#echo "PROPS=$PROPS"

java $PROPS -cp $CPATH $PGM $OPTS -m $max -b $bean -c $configFiles | tee log.txt
cp log.txt keys-dump-$bean.txt
