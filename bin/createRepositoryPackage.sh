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
# Creates a package containing all files in a fresh Maven repository after compilation.
#
# This script assumes that it is run from the trunk directory.
#
#

VERSION="1.0a1"
MVNREPDIR="${HOME}/.m2/repository"
TMPMVNREPDIR="${HOME}/.m2/repository-tmp-$(date +%s)"
TARFILE="zkpev2013-mvnrep-${VERSION}.tgz"
ZIPFILE="zkpev2013-mvnrep-${VERSION}.zip"

echo
echo "Creating a fresh Maven repository..."

# Make a copy of the current Maven repository
mv $MVNREPDIR $TMPMVNREPDIR

# Compile in order to populate the Maven repository.
mvn clean install site:site

# Packing everything together in a tgz file
tar -czf $TARFILE $MVNREPDIR

# Packing everything together in a zip file
zip -qr $ZIPFILE $MVNREPDIR

# Put the copy of the Maven repository back
rm -fR $MVNREPDIR
mv $TMPMVNREPDIR $MVNREPDIR

echo "Packed the Maven repository together to $TARFILE and $ZIPFILE."