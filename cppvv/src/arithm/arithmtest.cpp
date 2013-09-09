/**
 * @file   arithmtest.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-07 16:10:17 leo>
 * 
 * @brief A test suite for the whole arithm module.
 */


#include <cstdlib>
#include <cstring>
#include <iostream>
#include <fstream>

#include "arithm.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;

#define PATH_TO_INPUT_FILE  "./input.txt"
#define PATH_TO_OUTPUT_FILE "./actual_output.txt"
#define STATISTICAL_ERROR 100
#define N_RAND_ARRAYS 3



/** 
 * @brief Tests elGamal encryption and decryption by encrypting an
 * arbitrary element and decrypting the thus obtained ciphertext.
  */
void test_elGamal()
{
        Group * grp = new ModField(mpz_class("69a581c5d965405f3a6a9dd4d021f829de2c0a8d7e931c86c926e34d8c2ca804b5df463e652ecf701cd19444837075a0e727a30f61a011ea5ce543a7d9115e320fefbc80c172bfbba1d059190222e47ed1f861", 16));
        Elmt * plaintext  = grp->exp(grp->getGenerator(), new LargeNumber(42));
        Elmt * privateKey = new LargeNumber(424242424242424);
        Elmt * publicKey  = new ProdGrpElmt(
                grp->getGenerator(),
                grp->exp(grp->getGenerator(),privateKey)
                );
        Elmt * exponent   = new LargeNumber(123456);
        Elmt * ciphertext = elGamal::enc(grp, plaintext, publicKey, exponent);
        Elmt * result     = elGamal::dec(grp, ciphertext, privateKey);
        grp->getByteTree(plaintext)->prettyPrint("");
        grp->getByteTree(result)->prettyPrint("");
        if (grp->compare(plaintext, result))
        {
                std::cout << "Plaintext and result of decryption are "
                          << "identical: elGamal [OK]" << std::endl;
        }
        else
                std::cout << "Plaintext and result of decryption are"
                          << " different: elGamal [FAILED]" << std::endl;
}


/** 
 * @brief Tests the behaviour of ProdGrp instance modelling the
 * cartesian products of groups.
 *
 * It checks that the exponentiation of a generator of the cartesian
 * product is equal to 1 by first building a depth-2 cartesian
 * product.
 */
void  test_ProdGrp()
{
        Group   * grp0 = new ModField(mpz_class("56490255408001",10)),
                * grp1 = new ProdGrp(grp0,2),
                * grp2 = new ProdGrp(grp1,3);    
        Elmt    * g = grp2->getGenerator(),
                * s = new ProdGrpElmt(3);
        // Building the exponent s
        s->getCoord(0) = new ProdGrpElmt(
                new LargeNumber(grp0->getMultOrder()),
                new LargeNumber(grp0->getMultOrder())
                );
        s->getCoord(1) = new ProdGrpElmt(
                new LargeNumber(grp0->getMultOrder()),
                new LargeNumber(grp0->getMultOrder())
                );
        s->getCoord(2) = new ProdGrpElmt(
                new LargeNumber(grp0->getMultOrder()),
                new LargeNumber(grp0->getMultOrder())
                );

        // Computing g^s
        Elmt * res = grp2->exp(g,s);
        std::cout << "Generator g:" << std::endl;
        grp2->getByteTree(g)->prettyPrint("");
        std::cout << "g^s (s order of the group):" << std::endl;
        grp2->getByteTree(res)->prettyPrint("");

        // Checking that g^s is equal to 1
        if (grp2->compare(res,grp2->getOne()))
                std::cout<< "---\ng^s is equal to 1: ProdGrp [OK]";
        else
                std::cout<< "---\nPROBLEM: g^s is NOT equal to 1: "
                         << "ProdGrp [FAILED]";
}


/// @brief Displays a short help message
void printHelp()
{
        std::cout << "A small test suite for the algorithm module. Checks that"
                  << " the elGamal encryption can inverted (i.e. checks "
                  << "decryption) and that product groups behaves as you would"
                  << " expect by verifying that exponentiation works."
                  << std::endl;
}


// !SECTION! Main function
// =======================

int main(int argc, char ** argv)
{
        if (argc>=2 && (strcmp(argv[1], "-h") == 0
                       || strcmp(argv[1], "--help") == 0))
                printHelp();
        else
        {
                std::cout << "Checking elGamal encryption." << std::endl;
                test_elGamal();

                std::cout << "====\n\nChecking exponentiation in product"
                          << " group." << std::endl;
                test_ProdGrp();
                std::cout << std::endl;
        }
        return 0;
}
