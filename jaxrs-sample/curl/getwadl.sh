
. ./common.env

url="$URL/?_wadl&_type=xml"

echo "URL=$url"

curl $OPTS $url | tee wadl.xml

echo ""
echo "URL=$url"
echo "OPTS=$OPTS"

