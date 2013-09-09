/**
 * @file   algorithm_19.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-07 15:42:38 leo>
 * 
 * @brief Contains the implementation of algorithm 19 (Verifier).
 * 
 * @see algorithms.hpp
 */

#include "algorithms.hpp"

using namespace cppVerifier;

int proof::algorithm_19(
        io::Log * log,
        std::string protInfo,
        std::string directory,
        std::string auxsid_expected,
        int omega_expected
        )
{
        // !SECTION! 1. Protocol parameters
        // !SECTION! 2. Proof parameters
        // !SECTION! 3. Derived sets and objects
        // !SECTION! 4. Prefix to random oracles
        // These steps are all performed when building the arg variable.
        io::Arguments * arg;
        try
        {
                arg = new io::Arguments(
                        log,
                        protInfo,
                        auxsid_expected,
                        omega_expected,
                        directory
                        );
        }
        catch(utils::Exception exc)
        {
                log->write(exc.what());
                log->write("[FAIL] Reading or interpreting protocol info file.");
                log->write("\nREJECT");
                return 1;
        }
        log->write("[DONE] Interpretation of the protocol info file.");


        // !SECTION! 5. Read keys
        arithm::Elmt * pk;
        arithm::CollectionOfElmts x, y;
        
        try
        {
                utils::ByteTree * pkBt = arg->getByteTreeFromFile(
                        arg->getProofDir() + "FullPublicKey");
                pk = arg->getCkappa()->getElmt(pkBt);
        }
        catch (utils::Exception exc)
        {
                log->write(exc.what());
                log->write("[FAIL] Reading publick keys.");
                log->write("\nREJECT");
                return 1;
        }
        log->write("[DONE] Public key read.");


        // !SECTION! 6. Read lists
        // !SUBSECTION! a) Read input ciphers
        arithm::CollectionOfElmts L_0, L_lambda, m;
        unsigned int N;
        try
        {
                utils::ByteTree * l_0Bt = arg->getByteTreeFromFile(
                        arg->getProofDir() + "Ciphertexts");
                L_0 = arg->getCkappaOmega()->getCiphers(l_0Bt);
                N = L_0.size();
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In Algorithm_19: can't read L_0",
                                      exc);
                log->write("[FAIL] Reading array L_0");
                log->write("\nREJECT");
                return 1;
        }
        log->write("[DONE] Reading array L_0");
        
        
        // !SUBSECTION! b) Read shuffled ciphertexts


        try
        {                        
                L_lambda = arg->getCkappaOmega()->getCiphers(
                        arg->getByteTreeFromFile(
                                "ShuffledCiphertexts"
                                ));
        }
        catch (utils::Exception exc)
        {
                log->write(exc.what());
                log->write("[FAILED] reading L_lambda");
                log->write("\nREJECT");
                return 1;
        }
        log->write("[DONE] reading L_lambda");
        

        // !SECTION! 7. Verify relations between lists        
        // !SUBSECTION! a) Verify shuffling
        int verificationOfShuffling = 0;
        try
        {
                verificationOfShuffling = algorithm_18(
                        log,
                        arg,
                        N,
                        pk,
                        L_0,
                        L_lambda
                        );
        }
        catch (utils::Exception exc)
        {
                log->write(exc.what());
                verificationOfShuffling = 1;
        }
        if (verificationOfShuffling == 1)
        {
                log->write("[FAILED] Verification of shuffling");
                log->write("\nREJECT");
                return 1;
        }

        // !SUBSECTION! b) Accept proof
        log->write("All verifications passed!\nACCEPT");
        return 0;
}
