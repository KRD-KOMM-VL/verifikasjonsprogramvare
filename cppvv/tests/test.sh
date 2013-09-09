#!/bin/bash
# AUTHOR: Léo Perrin <leoperrin@picarresursix.fr>
# Time-stamp: <2013-09-07 16:15:20 leo>

# Runs tests for the crypto module and for the arithm module.

# Testing arithm
echo -e "===== ARITHM =====\n"
../build/src/arithm/arithm_test

# Testing crypto
echo -e "\n===== CRYPTO =====\n"
cd crypto
./test.sh

