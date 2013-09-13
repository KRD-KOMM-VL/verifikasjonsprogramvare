/**
 * @file   algorithm_17.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-09 17:36:28 leo>
 * 
 * @brief Contains the implementation of algorithm 17 (proof of a
 * shuffle).
 * 
 * @see algorithms.hpp
 */

#include "algorithms.hpp"

using namespace cppVerifier;


int proof::algorithm_17(
        io::Log * log,
        io::Arguments * arg,
        unsigned int N,
        arithm::Elmt * pk,
        arithm::CollectionOfElmts w,
        arithm::CollectionOfElmts wPrime,
        arithm::CollectionOfElmts u,
        utils::ByteTree * tau_pos,
        utils::ByteTree * sigma_pos,
        utils::ByteTree * w_bt,
        utils::ByteTree * wPrime_bt
        )
{
        arithm::Group
                * gq = arg->getGq(),
                * C_kappa = arg->getCkappa(),
                * C_kappa_omega = arg->getCkappaOmega(),
                * M_kappa_omega = arg->getMkappaOmega();
        arithm::Field
                * zq = arg->getZq(),
                * R_kappa_omega = arg->getRkappaOmega();

        // !SECTION! 1. Interpreting sigma_pos and tau_pos
        // ===============================================
        
        // !SUBSECTION! (a) Interpreting tau_pos
        // -------------------------------------

        arithm::Elmt * Aprime, * Cprime, * Dprime, * Fprime;
        arithm::CollectionOfElmts B, Bprime;
        try
        {
                B      = gq->getCollection(tau_pos->getChild(0)),
                Aprime = gq->getElmt      (tau_pos->getChild(1));
                Bprime = gq->getCollection(tau_pos->getChild(2));
                Cprime = gq->getElmt      (tau_pos->getChild(3));
                Dprime = gq->getElmt      (tau_pos->getChild(4));
                Fprime = C_kappa_omega->getElmt(tau_pos->getChild(5));
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: failed at parsing "
                                      "tau_pos", exc);
        }
        log->write("-- -- tau_pos interpreted successfully.");

        
        // !SUBSECTION! (b) Interpreting sigma_pos
        // ---------------------------------------

        arithm::Elmt * kA, * kC, * kD, * kF;
        arithm::CollectionOfElmts kB, kE;

        try
        {
                kA = zq->getElmt      (sigma_pos->getChild(0));
                kB = zq->getCollection(sigma_pos->getChild(1));
                kC = zq->getElmt      (sigma_pos->getChild(2));
                kD = zq->getElmt      (sigma_pos->getChild(3));
                kE = zq->getCollection(sigma_pos->getChild(4));
                kF = R_kappa_omega->getElmt(sigma_pos->getChild(5));
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: failed at parsing "
                                      "sigma_pos", exc);
        }
        log->write("-- -- sigma_pos interpreted successfully.");


        // !SECTION! 2. Computing a seed
        // =============================

        arg->computeH(N);
        arithm::CollectionOfElmts h = arg->getH();
        // gq->getByteTree(h)->prettyPrint("");

        log->write("-- -- h computed successfully.");
        
        utils::ByteTree * seedBt = new utils::Node();
        std::vector<uint8_t> s;
        try
        {
                seedBt->addChild(gq->getByteTree(gq->getGenerator()));  // OK
                seedBt->addChild(gq->getByteTree(h)); // OK
                seedBt->addChild(gq->getByteTree(u)); // OK
                seedBt->addChild(C_kappa->getByteTree(pk)); // OK
                seedBt->addChild(C_kappa_omega->getByteTree(w));
                seedBt->addChild(C_kappa_omega->getByteTree(wPrime));
                s = arg->getSeed(seedBt);
        }
        catch (utils::Exception exc)
        {
                utils::treatException(
                        "In algorithm_17: failed computation of the seed s.",
                        exc);
        }
        std::string strS;
        for (unsigned int i=0; i<s.size(); i++)
                strS += utils::byte2str(s[i]);
        log->write("-- -- Seed s computed successfully: 0x" + strS);

        
        // !SECTION! 3. Random exponents, A and F
        // ======================================

        arithm::CollectionOfElmts e;
        try
        {
                e = arg->randomExponents(s, N);
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: coulnd't compute "
                                      "random exponent s", exc);
        }

        arithm::Elmt * A, * F;
        try
        {
                A = gq->expProduct(u, e);
                F = C_kappa_omega->expProduct(w, e);
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: couldn't compute A "
                                      "or F", exc);
        }
        log->write("-- -- Elements A, F and exponents e computed "
                   "successfully.");
                

        // !SECTION! 4. Computing challenge
        // ================================

        arithm::Elmt * v = arg->getChallenge(s, tau_pos);
        log->write("-- -- Challenge v computed successfully: "
                   + zq->getByteTree(v)->toString());

        // !SECTION! 5. Checking equalities
        // ================================


        // !SUBSECTION! First, we compute all the elements used
        // ----------------------------------------------------

        arithm::Elmt
                * C = gq->div(
                        gq->product(u),
                        gq->product(h)
                        ),
                * D = gq->div(
                        B[N-1],
                        gq->exp(
                                h[0],
                                zq->product(e)
                                )
                        );
                
        log->write("-- -- Elements C and D computed successfully.");
        

        // !SUBSECTION! Then, we compute both sides of the equalities we check
        // -------------------------------------------------------------------

        // A variable for each side (LeftHandSide, RightHandSide) of the equalities
        arithm::Elmt
                * lhsA, * rhsA,
                * lhsB, * rhsB, // will be computed in a loop
                * lhsC, * rhsC,
                * lhsD, * rhsD,
                * lhsF, * rhsF;

        // !SUBSUBSECTION! A
        // .................
        try
        {
                lhsA = gq->mult(gq->exp(A, v), Aprime);
                rhsA = gq->mult(
                        gq->exp(gq->getGenerator(), kA),
                        gq->expProduct(h, kE)
                        );
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: failed at operating "
                                      "over A", exc);
        }

        // !SUBSUBSECTION! C
        // .................
        try
        {
                lhsC = gq->mult(gq->exp(C,v), Cprime);
                rhsC = gq->exp(gq->getGenerator(), kC);
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: failed at operating "
                                      "over C", exc);
        }

        // !SUBSUBSECTION! D
        // .................
        try
        {
                lhsD = gq->mult(gq->exp(D, v), Dprime);
                rhsD = gq->exp(gq->getGenerator(), kD);
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: failed at operating "
                                      "over D", exc);
        }

        // !SUBSUBSECTION! F
        // .................
        try
        {
                lhsF = C_kappa_omega->mult(C_kappa_omega->exp(F,v), Fprime);
                rhsF = C_kappa_omega->mult(
                        arithm::elGamal::enc(
                                M_kappa_omega,
                                M_kappa_omega->getOne(),
                                pk,
                                R_kappa_omega->addInverse(kF)
                                ),
                        C_kappa_omega->expProduct(wPrime, kE)
                        );
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: failed at operating "
                                      "over F", exc);
        }
        
        // !SUBSECTION! ... And then we actually compare them
        // --------------------------------------------------

        if (!gq->compare(lhsA, rhsA))
        {
                log->write("-- -- Left hand side and right hand side mismatch "
                           "for A.\n-- -- algorithm_17 rejects.");
                return 1;
        }
        if (!gq->compare(lhsC, rhsC))
        {
                log->write("-- -- Left hand side and right hand side mismatch "
                           "for C.\n-- -- algorithm_17 rejects.");
                return 1;
        }
        if (!gq->compare(lhsD, rhsD))
        {
                log->write("-- -- Left hand side and right hand side mismatch "
                           "for D.\n-- -- algorithm_17 rejects.");
                return 1;
        }
        if (!C_kappa_omega->compare(lhsF, rhsF))
        {
                log->write("-- -- Left hand side and right hand side mismatch "
                           "for F.\n-- -- algorithm_17 rejects.");
                return 1;
        }

        // !SUBSECTION! Considering B separately as it needs a loop
        // --------------------------------------------------------

        // We check the case i=0 separately due to the particular
        // value of B_{-1}
        try
        {
                lhsB = gq->mult(gq->exp(B[0], v), Bprime[0]);
                rhsB = gq->mult(
                        gq->exp(gq->getGenerator(), kB[0]),
                        gq->exp(h[0], kE[0])
                        );
                if (!gq->compare(lhsB, rhsB))
                {
                        log->write(
                                "-- -- Left hand side and right hand "
                                "side mismatch for B_{0}"
                                "\n-- -- algorithm_17 rejects.");
                        return 1;
                }
         
                for(unsigned int i=1; i<N; i++)
                {
                        lhsB = gq->mult(gq->exp(B[i],v), Bprime[i]);
                        rhsB = gq->mult(
                                gq->exp(gq->getGenerator(), kB[i]),
                                gq->exp(B[i-1], kE[i])
                                );
                        if (!gq->compare(lhsB, rhsB))
                        {
                                log->write(
                                        "-- -- Left hand side and right hand "
                                        "side mismatch for B_{"
                                        + utils::num2str(i) + "}."
                                        "\n-- -- algorithm_17 rejects.");
                                return 1;
                        }
                }
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In algorithm_17: failed at operating "
                                      "over B", exc);
        }


        // !SECTION! Accepting
        // ===================

        log->write("Algorithm_17 succeeded");
        return 0;
}

