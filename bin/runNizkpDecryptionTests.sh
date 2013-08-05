#!/bin/sh
#
# Zero-Knowledge Protocols for E-Vote (ZKPEV)
#
# Copyright © 2013, The Norwegian Ministry of Local Government and Regional
# Development (KRD)
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
# Runs the test files for NizkpDecryption.
#
# This script assumes that it is run from the trunk directory, and that the
# appropriate test files are available in a subdirectory called tests.
#
# Version: $Id: runNizkpDecryptionTests.sh 10997 2011-10-18 13:49:22Z fvl $
#

VERSION="1.1"
NIZKPDIR="zkpev-${VERSION}"
NIZKP="./NizkpDecryption.sh"
TESTDIR="../tests"

cd $NIZKPDIR

# Remove the current log file, do not prompt if it doesn't exist.
rm -f zkpev.log

test()
{
	echo
	$NIZKP $TESTDIR/NizkpDecryptionElGamalProperties.properties $TESTDIR/NizkpDecryptionElGamalPublicKey.properties $TESTDIR/NizkpDecryptionDecryptionFile-$1.csv Results-Decryption-$1.csv
}

test 3
test 10
test 30
test 100
test 300
test 1000
test 3000
test 10000
test 30000
test 100000