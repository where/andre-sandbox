
#dir=/Users/andremaserovic/git/where/andre-sandbox/bin
dir=.

file=wadl_documentation.xsl
xsl=$dir/xsl/$file

wfile=wadl.xml

if [ $# -gt 0 ] ; then
  wfile=$1
  fi

xsltproc $xsl $wfile > $wfile.html

echo "Output: $wfile.html XSL: $xsl"
