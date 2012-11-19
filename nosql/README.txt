******************************************************************
*
* nosql README.txt                                     18 nov 2012
*
******************************************************************

ant
  all          Compile and Build jar
  all.resolve  Download 3rd party jars and builds jar
  echo         Echo classpath
  resolve      Retrieve dependencies with ivy
  Default target: all

Configuration

  common.env - Common stuff for all shell scripts
    Toggle properties:
      providerConfigFile - NoSQL provider
      hosts - URL for server

  user.properties - user-specific overwrites for conf/nosql.properties

Shell scripts

  shell.sh - Shell to interactively manipulate items (put, get and delete).

  cli.sh - Command line program to manipulate items.
    cli.sh -m get -k MyKey
    cli.sh -m put -k MyKey -v MyValue
    cli.sh -m delete -k MyKey

  cleaf.sh - Citrusleaf server info
 
  dump-keys.sh - Dump some keys for Key Generators

  dump-keyvalues.sh - Dump some key and values for Key Generator and ValueGenerator.

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
    datagen.xml - Key and value generator beans

