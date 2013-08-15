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
# Script to clone the Bulletin Board.
#
# Notice that this script creates a new directory and clones the repository
# completely in order to have separate versions of the repository in separate
# directories. Just fetching new commits from GitHub would allow changes to
# the history of the files from GitHub's side.
#

echo "Going to make a new clone of the Bulletin Board at GitHub..."

# Target directory, i.e. the directory where the directories for the clones
# will be created.
TARGET_DIR=$1

# Path to the certificate. If absent, the authenticity will not be checked.
CERTIFICATE_FILE=$2

NOW=$(date +"%Y%m%dT%H%M")

echo "Moving to the target directory $TARGET_DIR..."

cd "$TARGET_DIR"

echo "Creating and moving to a new subdirectory $NOW..."
mkdir $NOW
cd $NOW

echo "Cloning the repository from GitHub..."
git clone https://github.com/KRD-KOMM-VL/evalg.git

if [[ "$CERTIFICATE_FILE" ]]
then
	echo "Verifying the authenticity of the list with receipts..."
	cd evalg
	openssl x509 -pubkey -noout -in "$CERTIFICATE_FILE" > pubkeyVCS.pem
	sha256sum bulletin_130001.txt | grep -o '^[^ ]*' | tr -d '\n' > sha.txt
	openssl dgst -sha256 -verify pubkeyVCS.pem -signature bulletin_130001.sig sha.txt
else
	echo "Skipping the verification of the authenticity of the list with receipts."
fi

cd "$TARGET_DIR"
PREVIOUS=`find . -maxdepth 1 -type d | grep -e '\./[0-9]\{8\}T[0-9]\{4\}' | sort | tail -2 | head -1 | cut -c3-`
echo "Verifying against $PREVIOUS that receipts have only been added, not deleted..."
TEMP1="$(mktemp)"
TEMP2="$(mktemp)"
tail -n +2 $PREVIOUS/evalg/bulletin_130001.txt | sort > $TEMP1
tail -n +2 $NOW/evalg/bulletin_130001.txt | sort > $TEMP2
comm -23 $TEMP1 $TEMP2 > $NOW/comm-$NOW.log
if [[ -s $NOW/comm-$NOW.log ]]
then
	echo "Verification Failure -- see $NOW/comm-$NOW.log for more information"
else
	echo "Verified OK"
fi
rm $TEMP1
rm $TEMP2

echo "Done."