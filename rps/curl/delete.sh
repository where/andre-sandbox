
. common.env

id=foo
if [ $# -gt 0 ] ; then
  id=$1
  fi

url=$URL/profile/$id

echo "URL=$url"

curl $OPTS -X DELETE $url | tee oo

echo "URL=$url"
#showProtocol
