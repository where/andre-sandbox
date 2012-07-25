
. ./common.env

PGM=com.andre.mapper.ObjectMapperDriver

method=list
method=all
method=object
if [ $# -gt 0 ] ; then
  method=$1
  fi

java -cp $CPATH $PGM -m $method | tee oo
