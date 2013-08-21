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
		echo -e "\e[1;31mERR -- see $FILE for more information\e[0m"
	else
		echo "OK"
	fi ;
}

function assert_deletions_only {
	FILE=$1
	sed -i -e'/^< .*$/d' $FILE
	sed -i -e'/^[0-9]\+d[0-9]*$/d' $FILE
	assert_empty $FILE
}

function file_length {
	echo "$(wc -l < $1)"
}

function base64_decode_voting_receipts {
	INPUT=$1
	OUTPUT=$2
	cat $INPUT | while read LINE
	do
		VOTING_RECEIPT=`echo $LINE | cut -d, -f6`
		DECODED_VOTING_RECEIPT=`echo $VOTING_RECEIPT | base64 --decode`
		LINE_WITH_DECODED_VOTING_RECEIPT=$(sed "s#$VOTING_RECEIPT#$DECODED_VOTING_RECEIPT#g" <<< $LINE)
		echo $LINE_WITH_DECODED_VOTING_RECEIPT >> $OUTPUT
	done
}

function compare_encrypted_votes_with_receipts {
	VOTES=$1
	RECEIPTS=$2
	LOG=$3
	CLASSPATH=lib/bsh-2.0b4.jar:lib/commons-codec-1.8.jar:lib/commons-lang-2.6.jar:lib/jackson-core-asl-1.9.2.jar:lib/jackson-mapper-asl-1.9.2.jar:lib/jcommander-1.27.jar:lib/junit-4.10.jar:lib/log4j-1.2.17.jar:lib/snakeyaml-1.6.jar:lib/testng-6.8.5.jar:lib/zkpev2013-1.0a1.jar
	java -Xmx2048m -cp $CLASSPATH com.computas.zkpev2013.rcgvcs.NizkpRcgVcs $VOTES $RECEIPTS $LOG
}

RCG_VOTES_SIZE=`file_length $RCG_VOTES`
VCS_VOTES_SIZE=`file_length $VCS_VOTES`
echo "Comparing RCG and VCS votes ($RCG_VOTES_SIZE - $VCS_VOTES_SIZE)..."
cut_sort_diff $RCG_VOTES 2-17 $VCS_VOTES 2-17 rcg_vcs_votes_diff.log
assert_deletions_only rcg_vcs_votes_diff.log

RCG_RECEIPTS_SIZE=`file_length $RCG_RECEIPTS`
VCS_RECEIPTS_SIZE=`file_length $VCS_RECEIPTS`
echo "Comparing RCG and VCS receipts ($RCG_RECEIPTS_SIZE - $VCS_RECEIPTS_SIZE)..."
cut_sort_diff $RCG_RECEIPTS 2-9 $VCS_RECEIPTS 2-9 rcg_vcs_receipts_diff.log
assert_deletions_only rcg_vcs_receipts_diff.log

VCS_VOTES_REDUCED_SIZE=`file_length $VCS_VOTES_REDUCED`
echo "Comparing VCS votes with reduced file ($VCS_VOTES_SIZE - $VCS_VOTES_REDUCED_SIZE)..."
cut_sort_diff $VCS_VOTES 2-16 $VCS_VOTES_REDUCED 2-16  vcs_vcs_reduced_votes_diff.log
assert_empty vcs_vcs_reduced_votes_diff.log

VCS_RECEIPTS_REDUCED_SIZE=`file_length $VCS_RECEIPTS_REDUCED`
echo "Base64 decoding the voting receipts in the reduced receipts file ($VCS_RECEIPTS_REDUCED_SIZE)..."
VCS_RECEIPTS_REDUCED_DECODED="$(mktemp)"
base64_decode_voting_receipts $VCS_RECEIPTS_REDUCED $VCS_RECEIPTS_REDUCED_DECODED
echo "Comparing VCS receipts with reduced file ($RCG_RECEIPTS_SIZE - $VCS_RECEIPTS_REDUCED_SIZE)..."
echo -e "\e[33mCOMPARISON OF FULL_VOTING_RECEIPT NOT IMPLEMENTED YET\e[0m"
cut_sort_diff $VCS_RECEIPTS 2,4-9 $VCS_RECEIPTS_REDUCED_DECODED 2,4-9 vcs_vcs_reduced_receipts_diff.log
assert_empty vcs_vcs_reduced_receipts_diff.log
rm $VCS_RECEIPTS_REDUCED_DECODED

BULLETIN_BOARD_RECEIPTS_SIZE=`file_length $BULLETIN_BOARD_RECEIPTS`
echo "Comparing VCS and BB receipts ($VCS_RECEIPTS_REDUCED_SIZE - $BULLETIN_BOARD_RECEIPTS_SIZE)..."
cut_sort_diff $VCS_RECEIPTS_REDUCED 1-2,4-9 $BULLETIN_BOARD_RECEIPTS 1-8 vcs_bb_receipts_diff.log
assert_empty vcs_bb_receipts_diff.log

echo "Comparing RCG receipts against RCG votes..."
compare_encrypted_votes_with_receipts $RCG_VOTES $RCG_RECEIPTS rcg_votes_receipts.log

echo "Comparing VCS receipts against VCS votes..."
compare_encrypted_votes_with_receipts $VCS_VOTES $VCS_RECEIPTS vcs_votes_receipts.log

echo "Comparing reduced VCS receipts against reduced VCS votes..."
compare_encrypted_votes_with_receipts $VCS_VOTES_REDUCED $VCS_RECEIPTS_REDUCED vcs_votes_receipts_reduced.log
#echo -e "\e[33mCOMPARISON OF VCS REDUCED RECEIPTS AGAINST VCS REDUCED VOTES NOT IMPLEMENTED YET\e[0m"

echo "Done."