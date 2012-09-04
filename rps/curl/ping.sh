
. common.env

id=foo
if [ $# -gt 0 ] ; then
  id=$1
  fi

url=$URL/ping

echo "URL=$url"

curl $OPTS $url | tee log.txt

echo "URL=$url"
