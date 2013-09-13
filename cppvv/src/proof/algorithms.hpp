/**
 * @file   algorithms.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-09 13:51:39 leo>
 * 
 * @brief Contains the declaration of the different algorithms
 * described in the specification.
 * 
 * @see algorithms.cpp
 */


#ifndef _PROOF_H_
#define _PROOF_H_


#include "../utils/utils.hpp"
#include "../crypto/crypto.hpp"
#include "../arithm/arithm.hpp"
#include "../io/io.hpp"



namespace cppVerifier {


/**
 * @brief Contains the algorithms to be ran if we want to check the
 * different stages of the electronic vote.
 *
 * Names and code are kept as close to the specification as possible
 * so might want to have it close by when reading code of this
 * namespace.
 */
namespace proof {

        
        /**
         * @brief Implements a proof of shuffle of ciphertexts (see
         * page 13 of the specification).
         *
         * @return 0 if the algorithm ran smoothly, 1 if it rejected.
         */
        int algorithm_17(
                io::Log * log, ///< To know what happened.
                io::Arguments * arg, ///< The basic arguments to use.
                unsigned int N, ///< The size of the arrays.
                arithm::Elmt * pk, ///< El Gamal public key.
                arithm::CollectionOfElmts w,       ///< Array of input ciphertexts in C_omega of size N.
                arithm::CollectionOfElmts wPrime,  ///< Array of output ciphertexts in C_omega of size N.
                arithm::CollectionOfElmts u,       ///< Permutation commitment.
                utils::ByteTree * tau_pos,  ///< Commitment of the Fiat-Shamir proof.
                utils::ByteTree * sigma_pos, ///< Reply of the Fiat-Shamir proof.
                utils::ByteTree * w_bt, ///< The bytetree representation of w
                utils::ByteTree * wPrime_bt ///< The bytetree representation of wPrime_bt
                );



        /**
         * @brief Implements a verifier of shuffle (see page 16 of the
         * specification)
         *
         * @return 0 if the algorithm ran smoothly, 1 if it rejected.
         *
         * @throw May throw an exception, in which case it also rejected.
         */
        int algorithm_18(
                io::Log * log, ///< Where to write information.
                io::Arguments * arg,  ///< The basic arguments to use.
                unsigned int N, ///< The size of the arrays.
                arithm::Elmt * pk, ///< El Gamal public key.
                arithm::CollectionOfElmts L_0,       ///< Original ciphertexts.
                arithm::CollectionOfElmts L_lambda,  ///< Shuffled ciphertexts.
                utils::ByteTree * L_0_bt, /// The bytetree representation of L_0
                utils::ByteTree * L_lambda_bt /// The bytetree representation of L_lambda
                );



        /** 
         * @brief Implements the main verification algorithm (see page
         * 26-27 of the specification).
         *
         * @return 0 if the algorithm ran smoothly, 1 if it rejected.
         */
        int algorithm_19(
                io::Log * log, ///< Where to write information.
                std::string protInfo, ///< Path to the protocol info file.
                std::string directory, ///< Directory containing proof.
                std::string auxsid_expected, ///< Expected auxiliary session identifier.
                int omega_expected ///< Expected width of ciphertexts.
                );


}
} // closing namespaces


#endif
