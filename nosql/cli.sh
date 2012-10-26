
#**************************************************
#
# cli.sh - Run NoSQL KeyValue CLI program.
#
#**************************************************

. ./common.env

PGM=com.amm.nosql.cli.KeyValueDriver

PROPS="$PROPS -DproviderConfigFile=$providerConfigFile"

java $PROPS -cp $CPATH $PGM $* | tee log.txt

