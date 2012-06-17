
. ./common.env

ifile=post-store.json
if [ $# -gt 0 ] ; then
  ifile=$1
  fi
echo "FILE: $ifile"

URL=$URL/store
echo "URL: $URL"

curl $URL $OPTS -X POST -T $ifile | tee $logBodyFile

echo " "
echo "FILE: $ifile"

showProtocol

