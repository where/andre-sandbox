
. ./common.env

CPATH="$CPATH:config/dbcompare"
CPATH="$CPATH:lib/jakarta/log4j-1.2.14.jar"
CPATH="$CPATH:lib/database/commons-dbcp.jar"
CPATH="$CPATH:lib/database/commons-pool.jar"
CPATH="$CPATH:lib/database/mysql-connector-java-5.1.8-bin.jar"

PGM=com.andre.dbcompare.DbCompare

java -cp $CPATH $PGM $* | tee oo
