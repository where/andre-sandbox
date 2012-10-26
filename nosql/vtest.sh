
#*******************************************************
#
# vtest.sh - Run script for vtest 
#
# Examples:
#   vtest.sh -r 100 -t 2 -i 5 get.task
#
#*******************************************************

rm -rf logs/*

CPATH="$CPATH:conf/vtest"

. ./common.env

PGM=com.amm.vtest.VTestDriver

daoConfigFile=tasks-keyvalue.xml

job=put-get.job
iterations=1
requests=1
threadPoolSize=1

seedKey=1776
seedValue=1776
#seedValue=-1

keySize=32
valueSize=1000
logModulo=50000

opts="r:t:v:i:b:s:S:p:d:"
while getopts $opts opt
  do
  case $opt in
    r) requests=$OPTARG ;;
    t) threadPoolSize=$OPTARG ;;
    v) valueSize=$OPTARG ;;
    i) iterations=$OPTARG ;;
    s) seedKey=$OPTARG ;;
    S) seedValue=$OPTARG ;;
    p) providerConfigFile=applicationContext-$OPTARG.xml 
       PROPS="$PROPS -DproviderConfigFile=$providerConfigFile"
       ;;
    d) daoConfigFile=$OPTARG.xml 
       PROPS="$PROPS -DdaoConfigFile=$daoConfigFile"
       ;;
    \?) echo $USAGE " Error"
        exit;;
    esac
  done

shift `expr $OPTIND - 1`
if [ $# -gt 0 ] ; then
  job=$1
  fi

#tstamp=`date "+%F_%H-%M"` ; logdir=logs-$job-$tstamp-req ; mkdir $logdir
#tstamp=`date "+%F_%H-%M"` ; logdir=logs-$job-$tstamp-req${requests} ; mkdir $logdir
#tstamp=`date "+%F_%H-%M"` ; logdir=logs-$job-$tstamp-Req${requests}-Thr${threadPoolSize} ; mkdir $logdir
tstamp=`date "+%F_%H-%M-%S"` ; logdir=logs-$job-$tstamp-Req${requests}-Thr${threadPoolSize}-Iter${iterations} ; mkdir $logdir
echo "Log output=$logdir"

cycleMax=$requests

PROPS="$PROPS -Dcfg.requests=$requests"
PROPS="$PROPS -Dcfg.threadPoolSize=$threadPoolSize"
PROPS="$PROPS -Dcfg.valueSize=$valueSize"
PROPS="$PROPS -Dcfg.keyGenerator.size=$keySize"
PROPS="$PROPS -Dcfg.key.seed=$seedKey"
PROPS="$PROPS -Dcfg.value.seed=$seedValue"
PROPS="$PROPS -Dcfg.randomGenerator.cycleMax=$cycleMax"
PROPS="$PROPS -DproviderConfigFile=$providerConfigFile"
PROPS="$PROPS -DdaoConfigFile=$daoConfigFile"
PROPS="$PROPS -Dcfg.logModulo=$logModulo"

echo "keySize=$keySize valueSize=$valueSize"

mem=1024m
XPROPS="-Xmx${mem} -Xms${mem}"

time -p java $XPROPS $PROPS -cp $CPATH $PGM $* \
  --iterations $iterations \
  --job $job \
  | tee log.txt

echo "valueSize=$valueSize" >> log.txt
echo "LOGDIR=$logdir" >> log.txt
echo "LOGDIR=$logdir"
echo "PROPS=$PROPS"
echo "providerConfigFile=$providerConfigFile"
echo "daoConfigFile=$daoConfigFile"

cp -p log.txt $logdir
mv log-*.xml $logdir