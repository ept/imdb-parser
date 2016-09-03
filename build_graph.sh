#!/bin/bash 

# example usage : 
# ./build_graph.sh csv-small graph-small

CSVPATH=$1
TARGET=$2

# should grab this from env ... 
NEOHOME="/Users/timothygriffin/working/lectures/db1A/ne04j/neo4j-community-3.0.4/bin" 

IMPORTARGS="--into $TARGET \
           --nodes $CSVPATH/movie.csv \
           --nodes $CSVPATH/person.csv \
           --nodes $CSVPATH/country.csv \
           --nodes $CSVPATH/genres.csv \
           --relationships $CSVPATH/acts_in.csv \
           --relationships $CSVPATH/directs.csv  \
           --relationships $CSVPATH/camera.csv  \
           --relationships $CSVPATH/compose.csv  \
           --relationships $CSVPATH/edits.csv  \
           --relationships $CSVPATH/produces.csv \
           --relationships $CSVPATH/production_design.csv \
           --relationships $CSVPATH/costume_design.csv \
           --relationships $CSVPATH/certificates.csv \ 
           --relationships $CSVPATH/release_dates.csv \ 
           --relationships $CSVPATH/has_genre.csv \ 
           --relationships $CSVPATH/writes.csv"

# echo $IMPORTARGS
# make sure neo4j database is empty 
rm -rf $TARGET/*
# invoke the CSV import tool 
$NEOHOME/neo4j-import --delimiter "|" $IMPORTARGS
            


