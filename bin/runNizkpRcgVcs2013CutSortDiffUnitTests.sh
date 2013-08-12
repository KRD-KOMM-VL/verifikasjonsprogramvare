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
# This script runs unit tests on cut_sort_diff.sh.
#

function assert_empty {
	if [[ -s $1 ]] ; then
		echo "ERR"
	else
		echo "OK"
	fi ;
}

function assert_not_empty {
	if [[ -s $1 ]] ; then
		echo "OK"
	else
		echo "ERR"
	fi ;
}

# Tests that two matching files return an empty log file
src/main/sh/cut_sort_diff.sh src/test/resources/NizkpRcgVcs2013CutSortDiffMatchingUnitTestFile1.csv 1-5 src/test/resources/NizkpRcgVcs2013CutSortDiffMatchingUnitTestFile2.csv 1-3,5-6 NizkpRcgVcs2013CutSortDiffMatchingUnitTest.log
assert_empty NizkpRcgVcs2013CutSortDiffMatchingUnitTest.log

# Tests that two unsorted matching files return an empty log file
src/main/sh/cut_sort_diff.sh src/test/resources/NizkpRcgVcs2013CutSortDiffUnsortedMatchingUnitTestFile1.csv 1-5 src/test/resources/NizkpRcgVcs2013CutSortDiffUnsortedMatchingUnitTestFile2.csv 1-3,5-6 NizkpRcgVcs2013CutSortDiffUnsortedMatchingUnitTest.log
assert_empty NizkpRcgVcs2013CutSortDiffUnsortedMatchingUnitTest.log

# Tests that two non-matching files return a non-empty log file
src/main/sh/cut_sort_diff.sh src/test/resources/NizkpRcgVcs2013CutSortDiffNonMatchingUnitTestFile1.csv 1-5 src/test/resources/NizkpRcgVcs2013CutSortDiffNonMatchingUnitTestFile2.csv 1-3,5-6 NizkpRcgVcs2013CutSortDiffNonMatchingUnitTest.log
assert_not_empty NizkpRcgVcs2013CutSortDiffNonMatchingUnitTest.log

# Tests that two unsorted non-matching files return an non-empty log file
src/main/sh/cut_sort_diff.sh src/test/resources/NizkpRcgVcs2013CutSortDiffUnsortedNonMatchingUnitTestFile1.csv 1-5 src/test/resources/NizkpRcgVcs2013CutSortDiffUnsortedNonMatchingUnitTestFile2.csv 1-3,5-6 NizkpRcgVcs2013CutSortDiffUnsortedNonMatchingUnitTest.log
assert_not_empty NizkpRcgVcs2013CutSortDiffUnsortedNonMatchingUnitTest.log
