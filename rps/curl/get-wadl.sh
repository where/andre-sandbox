

. common.env

url="$URL/?_wadl"

echo "URL=$url"

curl $url | tee log.txt

echo ""
echo "URL=$url"
