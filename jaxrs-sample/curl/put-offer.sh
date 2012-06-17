
. ./common.env

id=1
if [ $# -gt 0 ] ; then
  id=$1
  fi
ifile=put-offer.json
if [ $# -gt 1 ] ; then
  ifile=$2
  fi
echo "FILE: $ifile"

URL=$URL/offer/$id
echo "URL: $URL"

curl $URL $OPTS -X PUT -T $ifile | tee $logBodyFile

showProtocol

