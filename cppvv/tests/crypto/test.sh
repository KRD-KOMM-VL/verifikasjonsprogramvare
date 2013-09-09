#!/bin/bash
# AUTHOR: Léo Perrin <leoperrin@picarresursix.fr>
# Time-stamp: <2013-09-07 16:04:51 leo>

# Run all the tests in the cryptotools module. The idea is to compare
# the output of the XXX_test binary generated from
# src/crypto/XXXtest.cpp with the expected output.
#
# The expected output comes from the specification for the PRG and the
# RO and from NIST for the hashfunctions of the SHA-2 family.


# !SECTION! Retrieving the binaries from the build folder
# =======================================================

echo "Retrieving test binaries from the build folder."
cp ../../build/src/crypto/*test .


# !SECTION! Testing the hashfunctions
# ===================================

function test_NIST
# Compares the output of the hashfunctions with the NIST test vectors.
# param: $1 must be either "256", "384" or "512"
{
    test_vector=$(pwd)/sha$1-test-data.txt
    arg=SHA-$1
    result=$(pwd)/$1.res
    expected_result=$(pwd)/sha$1-expected-output.txt

    cat  $test_vector | ./sha_test $arg > $result

    diff $result $expected_result
    if [[ $? == 0  ]]
    then
        echo "SHA-"$1" [OK]"
    else
        echo "SHA-"$1" [failed]"
    fi
    rm $result
}

echo "Testing hashfunctions"
test_NIST 256
test_NIST 384
test_NIST 512


# !SECTION! Testing the PRG
# =========================

./prg_test > prg-output.txt
diff  prg-output.txt prg-expected-output.txt
if [[ $? == 0  ]]
then
    echo "PRG [OK]"
else
    echo "PRG [failed]"
fi
# rm prg-output.txt


# !SECTION! Testing the RO
# ========================

./ro_test > ro-output.txt
diff  ro-output.txt ro-expected-output.txt
if [[ $? == 0  ]]
then
    echo "RO [OK]"
else
    echo "RO [failed]"
fi
rm ro-output.txt
