
. ./common.env

url=$URL/store
url="$URL/store?radius=11"
echo "URL=$url"

curl $OPTS $url | tee $logBodyFile

echo ""
echo "URL=$url"
echo "OPTS=$OPTS"
