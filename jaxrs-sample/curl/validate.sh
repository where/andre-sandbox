
xsd=../config/schema.xsd

if [ $# -eq 0 ] ; then
  echo "ERROR: Missing XML file"
  exit
  fi
file=$1
echo "FILE=$file"
echo "XSD=$xsd"
xmllint --output log.txt --schema $xsd $file

