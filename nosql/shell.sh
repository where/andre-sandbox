
#**************************************************
#
# shell.sh - Run NoSQL KeyValue Shell program.
#
#**************************************************

. ./common.env

PGM=com.amm.nosql.cli.KeyValueShell

PROPS="$PROPS -DproviderConfigFile=$providerConfigFile"

echo ">> PROPS=$PROPS"

java $PROPS -cp $CPATH $PGM $* | tee log.txt

