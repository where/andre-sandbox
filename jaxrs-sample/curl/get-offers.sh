
. ./common.env

lat=-56.93
lng=-67.62
#url=$URL/offer
url="$URL/offer?radius=11&lat=$lat&lng=$lng"

echo "URL=$url"

curl $OPTS $url | tee $logBodyFile

echo ""
echo "URL=$url"
echo "OPTS=$OPTS"
