
. ./common.env

id=1
if [ $# -gt 0 ] ; then
  id=$1
  fi

url=$URL/store/$id
echo "URL=$url"

curl $OPTS $url | tee $logBodyFile

showProtocol
