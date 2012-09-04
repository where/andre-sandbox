
. common.env

id=foo
if [ $# -gt 0 ] ; then
  id=$1
  fi

url=$URL/profile/$id
ifile=put-profile.json

ifile2=$ifile.tmp

#tstamp=`date "+%F_%H-%M-%S"`
tstamp=`date`
sed -e "s/{TOKEN}/$tstamp/" < $ifile > $ifile2

echo "URL=$url"

curl $OPTS -X PUT -T $ifile2 $url | tee log.txt

rm $ifile2
echo "URL=$url"
