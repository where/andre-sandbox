
# namespace/test

. ./common.env

CPATH="$CPATH:conf/vtest"

PGM=com.amm.nosql.dao.citrusleaf.CitrusleafDriver

ns=
ns=namespace/profile

hostname=sb01.ad.ord.priv.where.com
hostname=lt15.dfw
port=3000

java $PROPS -cp $CPATH $PGM $hostname $port $ns | tee oo
