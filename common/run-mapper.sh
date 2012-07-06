
. ./common.env

PGM=com.andre.mapper.ObjectMapperDriver

java -cp $CPATH $PGM $* | tee oo
