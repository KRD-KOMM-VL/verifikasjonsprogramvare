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
# Creates a package containing sample files for testing.
#
# This script assumes that it is run from the trunk directory.
#

VERSION="1.0a3"
TMPDIR="zkpev2013-test-${VERSION}"
TARFILE="zkpev2013-test-${VERSION}.tgz"
ZIPFILE="zkpev2013-test-${VERSION}.zip"

echo
echo "Packing all test files together..."

# Remove the temporary directory if it already exists
[ -d $TMPDIR ] && rm -R $TMPDIR

# Create a temporary directory where we can put the file
mkdir $TMPDIR

# Move some sample files to the temporary directory
cp src/test/resources/NizkpRcgVcs2013FunctionalTestEncryptedVotes.csv $TMPDIR
cp src/test/resources/NizkpRcgVcs2013FunctionalTestVotingReceipts.csv $TMPDIR
cp src/test/resources/NizkpCleansingFunctionalTestElGamalProperties.properties $TMPDIR
cp src/test/resources/NizkpCleansingFunctionalTestAreas.csv $TMPDIR
cp src/test/resources/NizkpCleansingFunctionalTestVcsEncryptedVotes.csv $TMPDIR
mkdir $TMPDIR/NizkpCleansingFunctionalTestCleansedFilesDirectory
cp src/test/resources/NizkpCleansingFunctionalTestCleansedFilesDirectory/NizkpCleansingFunctionalTestCleansedFile.csv $TMPDIR/NizkpCleansingFunctionalTestCleansedFilesDirectory
cp src/test/resources/IzkpMixing2013FunctionalTestDatabaseProperties.properties $TMPDIR
cp src/test/resources/IzkpMixing2013FunctionalTestElGamalProperties.properties $TMPDIR
cp src/test/resources/IzkpMixing2013FunctionalTestElGamalPublicKey.properties $TMPDIR
cp src/test/resources/NizkpDecryption2013FunctionalTestElGamalProperties.properties $TMPDIR
cp src/test/resources/NizkpDecryption2013FunctionalTestElGamalPublicKeys.properties $TMPDIR
cp src/test/resources/NizkpDecryption2013FunctionalTestDecryptionFile.csv $TMPDIR

# Move the sample database dump to the temporary directory
cp src/test/resources/IzkpMixing2013FunctionalTestDatabaseDump $TMPDIR

# Packing everything together in a tgz file
tar -czf $TARFILE $TMPDIR

# Packing everything together in a zip file
zip -qr $ZIPFILE $TMPDIR

# Remove the temporary directory again since we're done
rm -R $TMPDIR

echo "Packed all test files together to $TARFILE and $ZIPFILE."