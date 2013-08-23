#!/bin/sh
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
# Script to unpack the result of the cleansing process, and copy all ballot
# files into one directory.
#

SOURCEFILE=$1
TARGETDIR=$2

echo
echo "Starting to recursively unzip $SOURCEFILE..."

TMP1DIR="$(mktemp -d --tmpdir=.)"

echo "Round 1..."

unzip -nq $SOURCEFILE -d $TMP1DIR

TMP2DIR="$(mktemp -d --tmpdir=.)"

echo "Round 2..."

for zip in $TMP1DIR/*.zip
do
  unzip -nq $zip -d $TMP2DIR
done

rm -R $TMP1DIR

TMP3DIR="$(mktemp -d --tmpdir=.)"

echo "Round 3..."

for zip in $TMP2DIR/*.zip
do
  unzip -nq $zip -d $TMP3DIR
done

rm -R $TMP2DIR

rm -R $TARGETDIR

echo "Round 4..."

for zip in $TMP3DIR/*.zip
do
  unzip -nq $zip -d $TARGETDIR
  BALLOTID=$(echo $zip | sed "s/.*\///g" | sed "s/.zip//g")
  mv $TARGETDIR/ballot $TARGETDIR/ballot-$BALLOTID.csv
done

rm -R $TMP3DIR

rm $TARGETDIR/ballot.sig

echo "Ballot boxes unpacked to $TARGETDIR."