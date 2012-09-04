
# Examples:
#   service-cli.sh -m get -k KEY
#   service-cli.sh -m put -k KEY -v VALUE
#   service-cli.sh -m delete -k KEY

clientProvider=restClient
clientProvider=serviceClient
PROPS="-DclientProvider=$clientProvider"

. ./common.env

PGM=com.paypal.ppmn.rps.client.ClientDriver

java -cp $CPATH $PROPS $PGM $* | tee log.txt
