
. ./common.env

lat=42.3583 
lng=71.0603
#url=$URL/offer
url="$URL/offer?radius=11&lat=$lat&lng=$lng"

echo "URL=$url"

curl $OPTS $url | tee $logBodyFile

echo ""
echo "URL=$url"
echo "OPTS=$OPTS"
