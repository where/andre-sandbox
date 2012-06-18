
# Launch Jetty server with WAR

CPATH=.
CPATH="$CPATH:build/classes"
for file in `find lib -name *.jar` ; do
  CPATH="$CPATH:$file" ; done

PGM=com.andre.jetty.Main
port=8080
host=localhost
wdir=build/dist
webApp="$wdir/pphere.war"
contextPath="/pphere"

java -classpath $CPATH $PGM $host $port $webApp $contextPath | tee log.txt
