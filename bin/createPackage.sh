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
# Creates a package containing all the run-time code.
#
# This script assumes that it is run from the trunk directory.
#
#

VERSION="1.0a1"
TMPDIR="zkpev2013-${VERSION}"
TMPLIBDIR="$TMPDIR/lib"
TARFILE="zkpev2013-${VERSION}.tgz"
ZIPFILE="zkpev2013-${VERSION}.zip"

mvn clean install -PNoCodeQuality

echo
echo "Packing all run-time code together..."

# Remove the temporary directory if it already exists
[ -d $TMPDIR ] && rm -R $TMPDIR

# Create a temporary directory where we can put the file
mkdir $TMPDIR

# Create all the necessary subdirectories
mkdir $TMPLIBDIR

# Move all JAR files from the target to the lib directory
cp target/*.jar $TMPLIBDIR
cp target/dependency/*.jar $TMPLIBDIR

# Move the shell scripts to the temporary directory
cp src/main/sh/NizkpRcgVcs2013.sh $TMPDIR
chmod a+x $TMPDIR/NizkpRcgVcs2013.sh
cp src/main/sh/cut_sort_diff.sh $TMPDIR
chmod a+x $TMPDIR/cut_sort_diff.sh
cp src/main/sh/NizkpCleansing.sh $TMPDIR
chmod a+x $TMPDIR/NizkpCleansing.sh
cp src/main/sh/UnpackCleansedFiles.sh $TMPDIR
chmod a+x $TMPDIR/UnpackCleansedFiles.sh
cp src/main/sh/IzkpMixing.sh $TMPDIR
chmod a+x $TMPDIR/IzkpMixing.sh
cp src/main/sh/NizkpDecryption.sh $TMPDIR
chmod a+x $TMPDIR/NizkpDecryption.sh

# Move some sample files to the temporary directory
cp src/test/resources/NizkpRcgVcs2013FunctionalTestEncryptedVotes.csv $TMPDIR
cp src/test/resources/NizkpRcgVcs2013FunctionalTestVotingReceipts.csv $TMPDIR
cp src/test/resources/NizkpCleansingFunctionalTestElGamalProperties.properties $TMPDIR
cp src/test/resources/NizkpCleansingFunctionalTestAreas.csv $TMPDIR
cp src/test/resources/NizkpCleansingFunctionalTestVcsEncryptedVotes.csv $TMPDIR
mkdir $TMPDIR/NizkpCleansingFunctionalTestCleansedFilesDirectory
cp src/test/resources/NizkpCleansingFunctionalTestCleansedFilesDirectory/NizkpCleansingFunctionalTestCleansedFile.csv $TMPDIR/NizkpCleansingFunctionalTestCleansedFilesDirectory
cp src/test/resources/IzkpMixingFunctionalTestDatabaseProperties.properties $TMPDIR
cp src/test/resources/IzkpMixingFunctionalTestElGamalProperties.properties $TMPDIR
cp src/test/resources/IzkpMixingFunctionalTestElGamalPublicKey.properties $TMPDIR
cp src/test/resources/NizkpDecryptionFunctionalTestElGamalProperties.properties $TMPDIR
cp src/test/resources/NizkpDecryptionFunctionalTestElGamalPublicKey.properties $TMPDIR
cp src/test/resources/NizkpDecryptionFunctionalTestDecryptionFile.csv $TMPDIR

# Move the sample database dump to the temporary directory
cp src/test/resources/IzkpMixingFunctionalTestDatabaseDump $TMPDIR

# Packing everything together in a tgz file
tar -czf $TARFILE $TMPDIR

# Packing everything together in a zip file
zip -qr $ZIPFILE $TMPDIR

# Remove the temporary directory again since we're done
rm -R $TMPDIR

echo "Packed all run-time code together to $TARFILE and $ZIPFILE."