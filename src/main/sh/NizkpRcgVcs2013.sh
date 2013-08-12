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

function assert_empty {
	if [[ -s $1 ]] ; then
		echo "ERR"
	else
		echo "OK"
	fi ;
}

echo "Comparing RCG and VCS votes..."
cut_sort_diff.sh $RCG_VOTES 1-17 $VCS_VOTES 1-17 rcg_vcs_diff.log
assert_empty rcg_vcs_diff.log

echo "Comparing VCS votes with reduced file..."
cut_sort_diff.sh $VCS_VOTES 1-16 $VCS_VOTES_REDUCED 1-16  vcs_vcs_reduced_diff.log
assert_empty vcs_vcs_reduced_diff.log

echo "Comparing VCS and BB receipts..."
cut_sort_diff.sh $VCS_RECEIPTS_REDUCED 1-2,4-9 $BULLETIN_BOARD_RECEIPTS 1-8 vcs_bb_diff.log
assert_empty vcs_bb_diff.log

echo "Done."