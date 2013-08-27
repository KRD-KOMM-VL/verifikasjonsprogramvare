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
# Script to the non-interactive zero-knowledge protocol proving correct decryption.
#

CLASSPATH=lib/bsh-2.0b4.jar:lib/commons-codec-1.8.jar:lib/commons-lang-2.6.jar:lib/jackson-core-asl-1.9.2.jar:lib/jackson-mapper-asl-1.9.2.jar:lib/jcommander-1.27.jar:lib/junit-4.10.jar:lib/log4j-1.2.17.jar:lib/snakeyaml-1.6.jar:lib/testng-6.8.5.jar:lib/zkpev2013-1.0a3.jar
java -Xmx2048m -cp $CLASSPATH com.computas.zkpev2013.decryption.NizkpDecryption $@