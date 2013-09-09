/**
 * @file   elgamal.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-07 15:47:23 leo>
 * 
 * @brief  The source code of the elGamal namespace.
 * 
 * @see elgamal.hpp
 */

#include "elgamal.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;


Elmt * elGamal::enc(Group * grp, Elmt * plaintext, Elmt * publicKey,
                    Elmt * randomExponent)
{
        try {
                return new ProdGrpElmt(
                        grp->exp(publicKey->getCoord(0),randomExponent),
                        grp->mult(
                                grp->exp(
                                        publicKey->getCoord(1),
                                        randomExponent
                                        ),
                                plaintext
                                )
                        );
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In elGamal::enc()",exc);
                return NULL;
        }
}


Elmt * elGamal::dec(Group * grp, Elmt * ciphertext, Elmt * privateKey)
{
        try {
                Elmt    * u = ciphertext->getCoord(0),
                        * v = ciphertext->getCoord(1);
                return grp->mult(v, grp->multInverse(grp->exp(u,privateKey)));
        } catch (utils::Exception exc) {
                utils::treatException("In elGamal::dec()",exc);
                return NULL;
        }
}


Elmt * elGamal::pdec(Group * grp, Elmt * ciphertext, Elmt * partialKey)
{
        try {
                Elmt * u = ciphertext->getCoord(0);
                return grp->multInverse(grp->mult(u,partialKey));
        } catch (utils::Exception exc) {
                utils::treatException("In elGamal::pdec()",exc);
                return NULL;
        }
}

        
Elmt * elGamal::tdec(Group * grp, Elmt * ciphertext, Elmt * f)
{
        try {
                Elmt * v = ciphertext->getCoord(1);
                return grp->mult(v,f);
        } catch (utils::Exception exc) {
                utils::treatException("In elGamal::tdec()",exc);
                return NULL;
        }
}


