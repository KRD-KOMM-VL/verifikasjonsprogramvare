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
# Script to run all the checks between the Return Code Generator (RCG) and
# the Vote Collector Server (VCS).
#

RCG_VOTES=$1
RCG_RECEIPTS=$2
VCS_VOTES=$3
VCS_RECEIPTS=$4
VCS_VOTES_REDUCED=$5
VCS_RECEIPTS_REDUCED=$6
BULLETIN_BOARD_RECEIPTS=$7

function cut_sort_diff {
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
}

function assert_empty {
	FILE=$1
	if [[ -s $FILE ]] ; then
		echo "ERR -- see $FILE for more information"
	else
		echo "OK"
	fi ;
}

function file_length {
	echo `wc -l < $1`
}

RCG_VOTES_SIZE=`file_length $RCG_VOTES`
VCS_VOTES_SIZE=`file_length $VCS_VOTES`
echo "Comparing RCG and VCS votes ($RCG_VOTES_SIZE - $VCS_VOTES_SIZE)..."
cut_sort_diff $RCG_VOTES 2-17 $VCS_VOTES 2-17 rcg_vcs_diff.log
assert_empty rcg_vcs_diff.log

VCS_VOTES_REDUCED_SIZE=`file_length $VCS_VOTES_REDUCED`
echo "Comparing VCS votes with reduced file ($VCS_VOTES_SIZE - $VCS_VOTES_REDUCED_SIZE)..."
cut_sort_diff $VCS_VOTES 2-16 $VCS_VOTES_REDUCED 2-16  vcs_vcs_reduced_diff.log
assert_empty vcs_vcs_reduced_diff.log

VCS_RECEIPTS_REDUCED_SIZE=`file_length $VCS_RECEIPTS_REDUCED`
BULLETIN_BOARD_RECEIPTS_SIZE=`file_length $BULLETIN_BOARD_RECEIPTS`
echo "Comparing VCS and BB receipts ($VCS_RECEIPTS_REDUCED_SIZE - $BULLETIN_BOARD_RECEIPTS_SIZE)..."
cut_sort_diff $VCS_RECEIPTS_REDUCED 1-2,4-9 $BULLETIN_BOARD_RECEIPTS 1-8 vcs_bb_diff.log
assert_empty vcs_bb_diff.log

echo "Done."