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
# Creates a package containing all the system documentation.
#
# This script assumes that it is run from the trunk directory.
#
#

VERSION="1.1"
TMPDIR="zkpev-sysdoc-${VERSION}"
TARFILE="zkpev-sysdoc-${VERSION}.tgz"
ZIPFILE="zkpev-sysdoc-${VERSION}.zip"

mvn clean site:site

echo
echo "Packing all system documentation together..."

# Remove the temporary directory if it already exists
[ -d $TMPDIR ] && rm -R $TMPDIR

# Create a temporary directory where we can put the file
mkdir $TMPDIR

# Move all mvn site:site documentation into the temporary directory
cp -R target/site/* $TMPDIR

# Packing everything together in a tgz file
tar -czf $TARFILE $TMPDIR

# Packing everything together in a zip file
zip -qr $ZIPFILE $TMPDIR

# Remove the temporary directory again since we're done
rm -R $TMPDIR

echo "Packed system documentation together to $TARFILE and $ZIPFILE."