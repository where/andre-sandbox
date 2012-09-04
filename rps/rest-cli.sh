
# Examples:
#   rest-cli.sh -m get -k KEY
#   rest-cli.sh -m put -k KEY -v VALUE
#   rest-cli.sh -m delete -k KEY

clientProvider=restClient
PROPS="-DclientProvider=$clientProvider"

. ./common.env

PGM=com.paypal.ppmn.rps.client.ClientDriver

java -cp $CPATH $PROPS $PGM $* | tee log.txt
