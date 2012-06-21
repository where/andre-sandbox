
# Generate XML XSD Schema from JAXB annotated classes
# Note: cannot specify name of schema file - lame schemagen :(

sdir1=src/main/java/com/andre/rest/data
sdir2=src/main/java/com/andre/pphere/data
classes="$sdir2/Location.java $sdir2/Offer.java $sdir2/BaseObject.java $sdir2/OfferList.java $sdir2/Store.java $sdir2/StoreList.java $sdir1/PagedList.java $sdir1/Link.java $sdir1/Error.java"
odir=tmp

mkdir $odir
schemagen  -d $odir $classes | tee log.txt

echo "SCHEMA in $odir"

