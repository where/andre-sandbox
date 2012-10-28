******************************************************************
*
* nosql README.txt                                     28 oct 2012
*
******************************************************************

To configure:

  common.env - Common stuff for shell scripts
    Toggle properties:
      providerConfigFile - NoSQL provider
      hosts - URL for server

  user.properties - user-specific overwrites for conf/nosql.properties

Shell scripts

  shell.sh - Shell to interactively get and put keys.

  cli.sh - Command line program to get and put keys.
    cli.sh -m get -k MyKey
    cli.sh -m put -k MyKey -v MyValue

  cleaf.sh - Citrusleaf server info
 
  dump-keys.sh - Dump some key values for Key Generators

vtest 

  vtest.sh - Script to run vtest
    Output is in log.txt - look at bottom for summary table.

  conf/
    nosql.properties - Provider properties
    appContext-{PROVIDER}.xml - Provider-specific beans

  conf/vtest
    tasks-keyvalue.xml - tasks for KeyValue tests
    vtest.properties - core vtest properties
    vtest.xml - core vtest beans

