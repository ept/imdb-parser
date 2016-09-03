#!/bin/bash 

# example usage : 
# ./run_cypher.sh graph-small src/examples/cypher/list_all_titles.cql 

DBPATH=$1
QUERY_FILE=$2

# should grab this from env ... 
NEOHOME="/Users/timothygriffin/working/lectures/db1A/ne04j/neo4j-community-3.0.4/bin" 

$NEOHOME/neo4j-shell -c --path $DBPATH < $QUERY_FILE 
            


