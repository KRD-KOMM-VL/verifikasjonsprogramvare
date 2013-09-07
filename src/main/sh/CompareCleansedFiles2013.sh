#!/bin/bash
#
# Zero-Knowledge Protocols for E-Vote (ZKPEV).
#
# Copyright © 2013, The Norwegian Ministry of Local Government and Regional
# Development (KRD).
#
# This file is part of ZKPEV.
#
# ZKPEV is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License as published by the Free Software
# Foundation, either version 3 of the License, or (at your option) any later
# version.
#
# ZKPEV is distributed in the hope that it will be useful, but WITHOUT ANY
# WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
# A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
#   
# You can find a copy of the GNU General Public License in
# /src/site/resources/gpl-3.0-standalone.html. Otherwise, see also
# http://www.gnu.org/licenses/.
#

#
# This script compares the contents of two cleansed ballot boxes.
# Notice: The cleansed ballot boxes have to be unpacked first
#

BB1=$1
BB2=$2

echo "Joining the first cleansed ballot box..."
TMP1="$(mktemp --tmpdir=.)"
for FILE in $BB1/*
do
	head -n -1 $FILE >> $TMP1
done
SORTED1="$(mktemp --tmpdir=.)"
sort $TMP1 > $SORTED1

echo "Joining the second cleansed ballot box..."
TMP2="$(mktemp --tmpdir=.)"
for FILE in $BB2/*
do
	head -n -1 $FILE >> $TMP2
done
SORTED2="$(mktemp --tmpdir=.)"
sort $TMP2 > $SORTED2

echo "Comparing the two cleansed ballot boxes..."
diff $SORTED1 $SORTED2 > CompareCleansedFiles2013.log

if [[ -s CompareCleansedFiles2013.log ]] ; then
	echo -e "\e[1;31mERR -- see CompareCleansedFiles2013.log for more information\e[0m"
else
	echo "OK"
fi

rm $TMP1
rm $TMP2
echo "Done."