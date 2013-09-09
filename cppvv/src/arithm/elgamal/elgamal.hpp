/**
 * @file   elgamal.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-12-20 22:56:25 leo>
 * 
 * @brief  The header of the elGamal namespace.
 * 
 * @see elgamal.cpp
 */

#ifndef _ELGAMAL_H_
#define _ELGAMAL_H_

#include "../group/group.hpp"




namespace cppVerifier {
namespace arithm {


/**
 * @brief Contains functions allowing to perform elGamal encryption
 * and decryption over elements of aribtrary groups.
 */
namespace elGamal {

        /**
         * @brief Let grp->getGenerator() be g: returns \f$Enc_{(g,y)}
         * (m,s) = (g^s, y^s m)\f$ where computations are performed in
         * the group grp.
         *
         * @param grp The group in which computations are made. It has
         * generator \f$g\f$.
         * @param plaintext  The Elmt to encrypt.
         * @param publicKey The public key.
         * @param randomExponent The exponent to use to encrypt.
         */
        Elmt * enc(Group * grp, Elmt * plaintext, Elmt * publicKey,
                   Elmt * randomExponent);

        /**
         * @brief Let \f$(u,v)\f$ be a ciphertext. Then returns
         * \f$Dec_x (u,v) = u^{-x} v\f$ where \f$x\f$ is the private
         * key.
         *
         * @param grp The group in which computations are made.
         * @param ciphertext The cipher to decrypt.
         * @param privateKey The private key to use to decrypt the
         * message.
         */
        Elmt * dec(Group * grp, Elmt * ciphertext, Elmt * privateKey);

        /**
         * @brief Let \f$(u,v)\f$ be a ciphertext. Then returns
         * \f$PDec_{x_l}(u,v) = u^{x_l}\f$ where \f$x_l\f$ is a
         * partial key.
         *
         * @param grp The group in which computations are made.
         * @param ciphertext The cipher to decrypt.
         * @param partialKey The partial key to use to decrypt the
         * message.
         */
        Elmt * pdec(Group * grp, Elmt * ciphertext, Elmt * partialKey);
        
        /**
         * @brief Let \f$(u,v)\f$ be a ciphertext. The returns
         * \f$TDec((u,v),f) = v f^{-1}\f$ by performing operations in
         * grp.
         */
        Elmt * tdec(Group * grp, Elmt * ciphertext, Elmt * f);


}
}
} // closing namespaces


#endif
