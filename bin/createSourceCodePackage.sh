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
# Creates a package containing all the source code.
#
# This script assumes that it is run from the trunk directory.
#
#

VERSION="1.1"
TMPDIR="zkpev-source-${VERSION}"
TARFILE="zkpev-source-${VERSION}.tgz"
ZIPFILE="zkpev-source-${VERSION}.zip"

echo
echo "Packing all source code together..."

# Remove the temporary directory if it already exists
[ -d $TMPDIR ] && rm -fR $TMPDIR

# Create a temporary directory where we can put the file
mkdir $TMPDIR

# Move into the temporary directory
cd $TMPDIR

# Check out the trunk from Subversion
svn --quiet co https://submarine.computas.com/ZKPEV/trunk/

# Eliminate all .svn directories
rm -rf `find . -name .svn`

# Eliminate trunk
mv trunk/* .
rmdir trunk

# Move back to the main directory
cd ..

# Packing everything together in a tgz file
tar -czf $TARFILE $TMPDIR

# Packing everything together in a zip file
zip -qr $ZIPFILE $TMPDIR

# Remove the temporary directory again since we're done
rm -fR $TMPDIR

echo "Packed all source code together to $TARFILE and $ZIPFILE."