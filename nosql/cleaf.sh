
# 

. ./common.env

CPATH="$CPATH:conf/vtest"

PGM=com.amm.nosql.dao.citrusleaf.CitrusleafDriver

hostname=sb01.ad.ord.priv.where.com
port=3000

java $PROPS -cp $CPATH $PGM $hostname $port | tee oo
