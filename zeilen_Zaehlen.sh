#!/bin/bash


SUCHE="^$|//|{|}|/\*|\*|@|try|import|sysout|package"
SRC="./src/"
KOMMENTARE="[//|\*]"

# grep -r "[//|\*]" .  |wc -l
# |egrep -v "\*$|\*/"

echo "Benedikt"

PFAD="Theremin"
echo "$PFAD:"
find $SRC -name "*.java" -exec egrep -av $SUCHE {} \; |wc -l
echo -n "Kommentare:"
find $SRC -name "*.java" -exec grep -r $KOMMENTARE {} \;|egrep -v "\*$|\*/" |wc -l





