
# namespace/test

. ./common.env

CPATH="$CPATH:conf/vtest"

PGM=com.amm.nosql.dao.citrusleaf.CitrusleafDriver
PGM=com.amm.nosql.dao.citrusleaf.Citrusleaf2Driver

ns=namespace/profile
ns=

hostname=sb01.ad.ord.priv.where.com
hostname=lt13.dfw
port=3000

java $PROPS -cp $CPATH $PGM $hostname $port $ns | tee oo
