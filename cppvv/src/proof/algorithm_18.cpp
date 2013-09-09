/**
 * @file   algorithm_18.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-04 22:04:14 leo>
 * 
 * @brief Contains the implementation of algorithm 18 (verifier of
 * shuffling).
 * 
 * @see algorithms.hpp
 */

#include "algorithms.hpp"

using namespace cppVerifier;


int proof::algorithm_18(
        io::Log * log, ///< Where to write information.
        io::Arguments * arg,  ///< The basic arguments to use.
        unsigned int N, ///< The size of the arrays.)
        arithm::Elmt * pk, ///< El Gamal public key.
        arithm::CollectionOfElmts L_0,       ///< Original ciphertexts.
        arithm::CollectionOfElmts L_lambda  ///< Shuffled ciphertexts.
        )
{
        arithm::Group
                * gq = arg->getGq(),
                * C_kappa_omega = arg->getCkappaOmega();

        
        arithm::CollectionOfElmts L_lMinusOne = L_0, L_l, u_l;

        for (unsigned int l=1; l<=arg->getLambda(); l++)
        {
                // We read the permutation commitment
                // !WARNING! Should we read the permutation commitment here?
                try
                {
                        utils::ByteTree * mu_l = arg->getByteTreeFromFile(
                                arg->getProofDir()
                                + "proofs/PermutationCommitment",
                                l);
                        u_l = gq->getCollection(mu_l);
                }
                catch (utils::Exception exc)
                {
                        utils::treatException("In algorithm_18: failed reading "
                                              "of mu_l", exc);
                }

                // !SUBSECTION! 1. Array of ciphertexts
                log->write("-- Reading array of ciphertext " + utils::num2str(l));
                if (l < arg->getLambda())
                {
                        try
                        {
                                L_l = C_kappa_omega->getCiphers(
                                        arg->getByteTreeFromFile(
                                                arg->getProofDir()
                                                + "proofs/Ciphertexts",
                                                l));
                        }
                        catch(utils::Exception exc)
                        {
                                utils::treatException(
                                        "In Algorithm_18 (without "
                                        "maxciph): failed reading of "
                                        "L_l for l=" +
                                        utils::num2str(l),
                                        exc);
                        }
                }
                else
                        L_l = L_lambda;
                        
                
                // !SUBSECTION! 2. Verify proof of shuffle
                int algo17Result;
                try
                {
                        utils::ByteTree
                                * tau_l_pos = arg->getByteTreeFromFile(
                                        arg->getProofDir()
                                        + "proofs/PoSCommitment",
                                        l),
                                * sigma_l_pos = arg->getByteTreeFromFile(
                                        arg->getProofDir()
                                        + "proofs/PoSReply",
                                        l);
                        algo17Result = algorithm_17(
                                log,
                                arg,
                                N, // !CONTINUE!  
                                pk,
                                L_lMinusOne,
                                L_l,
                                u_l,
                                tau_l_pos,
                                sigma_l_pos
                                );
                }
                catch (utils::Exception exc)
                {
                        log->write(exc.what());
                        algo17Result = 1;
                }
                if (algo17Result != 0 ||
                    !C_kappa_omega->compare(L_l,L_lMinusOne) )
                {
                        log->write("-- Verifier of shuffling failed for"
                                   " ciphertext " + utils::num2str(l));
                        return 1;
                }
                log->write("  Verification passed.");
                L_lMinusOne = L_l;
        }
        // !SUBSECTION! 3. Accept proof
        return 0;
}
