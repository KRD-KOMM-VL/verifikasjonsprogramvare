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
# Script to cut two CSV files, sort them and then run diff on them. The output
# of diff is sent to an output file.
#

INPUT_FILE1=$1
FIELDS_LIST1=$2
INPUT_FILE2=$3
FIELDS_LIST2=$4
OUTPUT_FILE=$5

TEMP1="$(mktemp)"
TEMP2="$(mktemp)"

cut -d, -f$FIELDS_LIST1 $INPUT_FILE1 | sort > $TEMP1
cut -d, -f$FIELDS_LIST2 $INPUT_FILE2 | sort > $TEMP2
diff $TEMP1 $TEMP2 > $OUTPUT_FILE
rm $TEMP1
rm $TEMP2