
. ./common.env

url="$BURL/schema.xsd"

echo "URL=$url"

curl $OPTS $url | tee schema.xsd

echo ""
echo "URL=$url"
echo "OPTS=$OPTS"

