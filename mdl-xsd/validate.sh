
xsd=schema.xsd
file=accounts.xml

if [ $# -gt 0 ] ; then
  file=$1
  fi
echo "FILE=$file"
echo "XSD=$xsd"

#xmllint  --schema $xsd $file 
xmllint --output log.txt --schema $xsd $file 

