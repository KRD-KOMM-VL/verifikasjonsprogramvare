/**
 * @file   arguments.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-06 22:38:40 leo>
 * 
 * @brief  The source code of the Arguments class.
 * 
 * @see arugments.hpp
 */

#include <fstream>
#include <iostream>

#include "arguments.hpp"


using namespace cppVerifier;
using namespace cppVerifier::io;


// !SECTION! Constructor
// ===================================================================

Arguments::Arguments(
        Log * log,
        std::string path2config,
        std::string auxsid_exp,
        int omega_exp,
        std::string proofPath) :
        protInfo(path2config)
{
        // setting attributes corresponding to CLI args
        auxsid_expected = auxsid_exp;
        omega_expected  = omega_exp;
        proofDir = proofPath;

        try
        {
                // !SUBSECTION! Retrieving numerical constants
                k      = protInfo.retrieveInteger("nopart");
                lambda = protInfo.retrieveInteger("thres");
                kappa  = protInfo.retrieveInteger("keywidth");
                ne     = protInfo.retrieveInteger("vbitlenro");
                nr     = protInfo.retrieveInteger("statdist");
                nv     = protInfo.retrieveInteger("cbitlenro");
                omega_default = protInfo.retrieveInteger("width");
                
                // !SUBSECTION! Retrieving strings
                version = protInfo.retrieveText("version");
                sid     = protInfo.retrieveText("sid");

                // !SUBSECTION! Checking that the arguments are fine
                if (checkArgs() == 1)
                        throw utils::Exception("In Step 2: parameters mismatch.");

                // !SUBSECTION! Building cryptographic primitives
                std::string sH   = protInfo.retrieveText("rohash");
                std::string sPRG = protInfo.retrieveText("prg");
                crypto::SHAx * H    = crypto::getHash(sH);
                crypto::SHAx * hPRG = crypto::getHash(sPRG);
                prg = new crypto::PRG(hPRG);

                ROseed = new crypto::RO(hPRG, hPRG->getHashLength());
                ROchallenge = new crypto::RO(H,nv);

                // !SUBSECTION! Creating the group gq
                std::string sGq = protInfo.retrieveText("pgroup");
                gq = arithm::unmarshal(sGq);
                zq = new arithm::ModField(gq->getMultOrder());

                arithm::Group * M_kappa = new arithm::ProdGrp(gq, kappa);
                arithm::Field * R_kappa = new arithm::ProdField(zq, kappa);
                C_kappa = new arithm::ProdGrp(M_kappa, 2);

                // !SUBSECTION! Creating the other arithmetic objects
                if (omega_default == 1)
                {
                        M_kappa_omega = M_kappa;
                        R_kappa_omega = R_kappa;
                }
                else
                {
                        M_kappa_omega = new arithm::ProdGrp(M_kappa, omega_default);
                        R_kappa_omega = new arithm::ProdField(R_kappa, omega_default);
                }
                C_kappa_omega = new arithm::ProdGrp(M_kappa_omega, 2);
                


                // !SUBSECTION! Computing the prefix rho
                std::string s1 = sid + "." + auxsid;

                utils::ByteTree * rhoBt = new utils::Node();
                // version and version_proof have to be identical
                // anyway, so we can safely use "version" here
                rhoBt->addChild(utils::ByteTree::string2ByteTree(version));
                rhoBt->addChild(utils::ByteTree::string2ByteTree(s1));
                rhoBt->addChild(new utils::Leaf(nr,4));
                rhoBt->addChild(new utils::Leaf(nv,4));
                rhoBt->addChild(new utils::Leaf(ne,4));
                rhoBt->addChild(utils::ByteTree::string2ByteTree(sPRG));
                rhoBt->addChild(utils::ByteTree::string2ByteTree(sGq));
                rhoBt->addChild(utils::ByteTree::string2ByteTree(sH));
                H->hash(rhoBt->toVector());
                rho = H->getHash();

                // !SUBSECTION! Logging parameter values
                std::string logLine = "-- Parameters:\n";
                logLine += "-- -- k = " + utils::num2str(k) + "\n";
                logLine += "-- -- lambda = " + utils::num2str(lambda) + "\n";
                logLine += "-- -- ne = " + utils::num2str(ne) + "\n";
                logLine += "-- -- nr = " + utils::num2str(nr) + "\n";
                logLine += "-- -- nv = " + utils::num2str(nv) + "\n";
                logLine += "-- -- omega_default = " + utils::num2str(omega_default) + "\n";
                logLine += "-- -- version = " + version + "\n";
                logLine += "-- -- sid = " + sid + "\n";
                logLine += "-- -- auxsid = " + auxsid + "\n";
                logLine += "-- -- H = " + sH + "\n";
                logLine += "-- -- prg = " + sPRG + "\n";
                logLine += "-- -- rho = 0x";
                for (unsigned int i=0; i<rho.size(); i++)
                        logLine += utils::byte2str(rho[i]);
                log->write(logLine);
                
                // !SUBSECTION! Initialising h to be empty
                h = arithm::CollectionOfElmts();
                // gq->getByteTree(h)->prettyPrint("");
        }
        catch (utils::Exception exc)
        {
                utils::treatException("In Arguments() (constructor):",exc);
        }
}


// !SECTION! File dealing functions
// ===================================================================


bool Arguments::isThereFile(std::string path)
{
        std::ifstream input(path);
        return input;
}



std::string Arguments::getFileContent(std::string path)
{
        if (!isThereFile(path))
        {
                throw utils::Exception("In Arguments.getFileContent: file"
                                       " corrupted at "+path+".");
                return "";
        }
        else
        {
                std::ifstream input(path, std::ifstream::in);
                char c;
                std::string result;
                while (input.good())
                {
                        c = input.get();
                        if (input.good())
                                result.push_back(c);
                }
                return result;
        }
}


std::string Arguments::getFileContent(std::string name,
                                      unsigned int index)
{
        std::string path = name;
        path.push_back((index / 10) + '0');
        path.push_back((index % 10) + '0');
        path.append(".bt");
        return getFileContent(path);
}


utils::ByteTree * Arguments::getByteTreeFromFile(std::string path)
{
        if (isThereFile(path + ".bt"))
                try
                {
                        return utils::ByteTree::parseFile(path + ".bt");
                }
                catch (utils::Exception exc)
                {
                        utils::treatException("In Arguments.getByteTreeFromFile"
                                              "(" + path +").", exc);
                        return NULL; // To please the compiler (never reached)
                }
        else
                throw utils::Exception("In Arguments.getByteTreeFromFile, "
                                       "no such file at path=" + path + ".bt");
}


utils::ByteTree * Arguments::getByteTreeFromFile(std::string name,
                                                 unsigned int index)
{
        std::string path = name;
        path.push_back((index / 10) + '0');
        path.push_back((index % 10) + '0');
        return getByteTreeFromFile(path);
}


void Arguments::computeH(unsigned int Nprime)
{
        utils::ByteTree * bt =
                utils::ByteTree::string2ByteTree("generators");
        std::vector<uint8_t>
                seed = rho,
                s2,
                btVector(bt->toVector());
        seed.insert(seed.end(), btVector.begin(), btVector.end());
        s2 = ROseed->query(seed);
        prg->initialize(s2);
        h = gq->getRandArray(prg,nr,Nprime);
}



// !SECTION! Utils
// ===================================================================



int Arguments::checkArgs()
{
        version_proof = getFileContent(proofDir+"/version");
        auxsid        = getFileContent(proofDir+"/auxsid");

        std::string omega_proofString = getFileContent(proofDir+"/width");
        int omega_proof = atoi(omega_proofString.c_str());

        if (version_proof.compare(version) != 0)
        {
                std::cout << "wrong version\n";
                return 1;
        }
        else if (auxsid.compare(auxsid_expected) != 0)
        {
                std::cout << "wrong auxsid\n";
                return 1;
        }
        else if (omega_expected == -1 && omega_default != omega_proof)
        {
                std::cout << "omega_expected == -1 and omega_default != omega_proof\n";
                return 1;
        }
        else if (omega_expected != -1 && omega_default != omega_expected)
        {
                std::cout << "omega_expected != -1 and omega_default != omega_expected\n";
                return 1;
        }
        else
                return 0;
}


std::vector<uint8_t> Arguments::getSeed(utils::ByteTree * bts)
{
        std::vector<uint8_t>
                queryROs = rho,
                btsVector(bts->toVector());
        queryROs.insert(queryROs.end(),
                        btsVector.begin(),
                        btsVector.end());
        return ROseed->query(queryROs);
}


arithm::CollectionOfElmts Arguments::randomExponents(
        std::vector<uint8_t> s, unsigned int n)
{
        // We use the prg's output to compute the exponents
        prg->initialize(s);
        arithm::CollectionOfElmts e;
        unsigned int eiByteLength = ne/8;
        mpz_class twoToTheNe = 2;
        mpz_pow_ui(twoToTheNe.get_mpz_t(),twoToTheNe.get_mpz_t(),ne);
        for (unsigned int i=0; i<n; i++)
        {
                mpz_class ei = 0;
                for (unsigned int j=0; j<eiByteLength; j++)
                        ei = ei*0x100 + prg->getNextRandByte();
                ei = ei % twoToTheNe;
                e.addElmt(new arithm::LargeNumber(ei));
        }
        return e;
}


arithm::Elmt * Arguments::getChallenge(
        std::vector<uint8_t> s,
        utils::ByteTree * bt)
{
        // Building the query and sending it to the RO
        utils::ByteTree * btv = new utils::Node();
        btv->addChild(new utils::Leaf(s));
        btv->addChild(bt);
        std::vector<uint8_t>
                vBytes,
                queryROv = rho,
                btvVector(btv->toVector());
        queryROv.insert(queryROv.end(),
                        btvVector.begin(),
                        btvVector.end());
        vBytes = ROchallenge->query(queryROv);

        // Computing the challenge itself
        mpz_class
                v = 1,
                modulo = 2;
        for (unsigned int i=0; i<nv/8; i++)
                v = v*0x100 + vBytes[i];
        mpz_pow_ui(
                modulo.get_mpz_t(),
                modulo.get_mpz_t(),
                nv); // After that, modulo=2^nv

        // wrapping up
        delete btv;
        return new arithm::LargeNumber(v % modulo);
}


// !SECTION! Getters 

std::string Arguments::getProofDir()
{
        return proofDir + "/";
}


std::vector<uint8_t> Arguments::getRho()
{
        return rho;
}


unsigned int Arguments::getLambda()
{
        return lambda;
}
       
        
unsigned int Arguments::getNe()
{
        return ne;
}
        
        
unsigned int Arguments::getNr()
{
        return nr;
}

        
unsigned int Arguments::getNv()
{
        return nv;
}        
        
        
crypto::PRG * Arguments::getPrg()
{
        return prg;
}


arithm::CollectionOfElmts Arguments::getH()
{
        if (h.size() == 0)
                throw utils::Exception("Trying to retrieve h before its "
                                       "computation.");
        else
                return h;
}


arithm::Group * Arguments::getGq()
{
        return gq;
}


arithm::Field * Arguments::getZq()
{
        return zq;
}


arithm::Group * Arguments::getMkappaOmega()
{
        return M_kappa_omega;
}


arithm::Group * Arguments::getCkappa()
{
        return C_kappa;
}


arithm::Group * Arguments::getCkappaOmega()
{
        return C_kappa_omega;
}


arithm::Field * Arguments::getRkappaOmega()
{
        return R_kappa_omega;
}
