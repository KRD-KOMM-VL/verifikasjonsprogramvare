/**
 * @file   arguments.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-05 22:19:29 leo>
 * 
 * @brief  The header of the Arguments class.
 * 
 * @see arguments.cpp
 */


#ifndef _ARGUMENTS_H_
#define _ARGUMENTS_H_


#include "../utils/utils.hpp"
#include "../crypto/crypto.hpp"
#include "../arithm/arithm.hpp"
#include "xmlreader.hpp"
#include "log.hpp"



namespace cppVerifier {
namespace io {


/**
 * @brief Stores the arguments that are common to all algorithms as
 * well as some routines using the said arguments that are used
 * throughout the whole verification process.
 */
class Arguments
{
private:
        /// @brief The xml class used to read the configuration file.
        XmlReader protInfo;

        /// @brief The path to the proof directory
        std::string proofDir;
        

        /// @name Integer constants
        /// @{


        /// @brief The number of parties
        unsigned int k;

        /// @brief The number of the number of mix-servers that take
        /// part in the shuffling, i.e., this is the threshold number
        /// of mix-servers that must be corrupted to break the privacy
        /// of the ciphertexts.
        unsigned int lambda;

        /// @brief The width of the key.
        unsigned int kappa;
        
        /// @brief Number of bits in each component of random vectors
        ///used for batching.
        unsigned int ne;

        /// @brief Acceptable "statistical error" when deriving
        /// independent generators.
        unsigned int nr;

        /// @brief Number of bits in challenges.
        unsigned int nv;

        ///@}

        /// @name Parameters of the session
        ///@{

        /// @brief The default width of the ciphertexts and plaintexts
        /// (from configuration file).
        int omega_default;
        
        /// @brief The expected width of the ciphertexts and
        /// plaintexts (from CLI args).
        int omega_expected;

        /// @brief The expected value of auxsid (from CLI args).
        std::string auxsid_expected;

        /// @brief the globally unique session identifier tied to the
        /// generation of a particular joint public key.
        std::string auxsid;

        /// @brief The globally unique session identifier tied to the
        /// generation of a particular joint public key
        std::string sid;

        /// @brief the version of Verificatum used during the
        /// execution that produced the proof.
        std::string version;

        /// @brief the version defined in the proof directory.
        std::string version_proof;

        ///@}
        

        /// @}
        /// @name Interpreted and computed parameters
        /// @{

        
        /// @brief Pseudo-random generator used to derive random
        /// vectors for batching.
        crypto::PRG * prg;

        /// @brief The random oracle used to generate challenges
        crypto::RO * ROchallenge;

        /// @brief The random oracle used to generate seeds
        crypto::RO * ROseed;

        /// @brief Prefix to the random oracle.
        std::vector<uint8_t> rho;

        /// @brief The standard random generators of gq.
        arithm::CollectionOfElmts h;


        /// @brief Group of prime order with standard generator g.
        arithm::Group * gq;

        /// @brief Field \f$Z_q\f$.
        arithm::Field * zq;

        /// @brief Group in which lay plaintexts.
        arithm::Group * M_kappa_omega;

        /// @brief Group in which lays the public key.
        arithm::Group * C_kappa;

        /// @brief Group in which lay ciphertexts.
        arithm::Group * C_kappa_omega;

        /// @brief Field in which lays randomness.
        arithm::Field * R_kappa_omega;

        /// @}


public:
        /** 
         * @brief Initializes all the attributes of a new Arguments
         * instance from the content of a protocol info file and CLI
         * arguments.
         *
         * The prefix rho is generated here as well as the array of
         * random generators.
         *
         * @param log Where to write the parameter values.
         * @param path2config  The path to the xml configuration file.
         * @param auxsid_exp The value to give to auxsid_expected.d.
         * @param omega_exp The value to give to omega_expected.
         * @param proofPath The path to the proof directory.
         */
        Arguments(Log * log,
                  std::string path2config,
                  std::string auxsid_exp,
                  int omega_exp,
                  std::string proofPath);


        /// @name File dealing functions
        /// @{

        /// @brief Returns true if the file exists, false otherwise.
        bool isThereFile(std::string path);
        
        /**
         * @brief Returns all the content of the file.
         *
         * @throw If there is no such file, throws an exception. 
         */
        std::string getFileContent(std::string path);

        /**
         * @brief Returns all the content of the file with the given
         * name at the given index. ".bt" is appended automatically.
         *
         * We use the fact that the index is exactly made of two
         * decimal digits.
         *
         * @throw If there is no such file, throws an exception. 
         */
        std::string getFileContent(std::string name, unsigned int index);

        /**
         * @brief Returns the bytetree in the file at path.bt
         * (i.e. the extension .bt is added automatically).
         */
        utils::ByteTree * getByteTreeFromFile(std::string path);

        /**
         * @brief Returns the bytetree in the file with the given name
         * at the given index. ".bt" is appended automatically.
         * 
         * We use the fact that the index is exactly made of two
         * decimal digits.
         */
        utils::ByteTree * getByteTreeFromFile(std::string name, unsigned int index);


        /// @}


        /**
         * @brief Compares the arguments stored in the proof directory
         * against those obtained during construction from the protocol
         * Info file.
         * 
         * @return Returns 0 if the arguments are as expected, 1
         * otherwise.
         */
        int checkArgs();

        /**
         * @brief Computes the seed to be fed to different random
         * oracle queries.
         *
         * Appends the hexadecimal representation of the bytetree
         * given as an argument to rho and queries a RO with the
         * result.
         *
         * @param bt The bytetree giving the entropy we need.
         *
         * @return The seed as a vector of bytes.
         */
        std::vector<uint8_t> getSeed(utils::ByteTree * bt);

        /** 
         * @brief Used to compute a random exponents vector according
         * to the specification.
         *
         * The exponents are derived using a PRG whose output is
         * turned into integers of bytelength $n_e/8$. The moduli of
         * the exponents thus created are returned (division modulo
         * \f$2^{8(n_e/8)}\f$).
         * 
         * @param s  The seed for the prg.
         * @param n  The number of exponents to return.
         * 
         * @return The exponents \f$e_i\f$ for \f$i \in [0,N_0-1]\f$.
         */
        arithm::CollectionOfElmts randomExponents(std::vector<uint8_t> s,
                                                  unsigned int n);

        /** 
         * @brief Returns a challenge computed from the original seed
         * s and a bytetree.
         *
         * First, concatenates the hexadecimal representation of the
         * node containing a leaf containing s and tau with rho. Then,
         * uses it as query for ROv. At last, turns its output into an
         * integer in \f$[0,2^{n_v}]\f$.
         *
         * @param s  The seed used by ROs to compute exponents.
         * @param bt The bytetree containing additional entropy.
         * 
         * @return A challenge for the different proofs.
         */
        arithm::Elmt * getChallenge(std::vector<uint8_t> s,
                                    utils::ByteTree * bt);

        /**
         * @brief Sets the h attribute of this instance according to
         * the specification and sets its size to be Nprime.
         */
        void computeH(unsigned int Nprime);

        /// @}
        /// @name Direct getters
        /// @{

        /// @brief Returns the path to the proof directory
        std::string getProofDir();

        /// @brief Returns the prefix to the random oracle.
        std::vector<uint8_t> getRho();

        /// @brief Returns the number of parties
        unsigned int getLambda();

        /// @brief Returns the number of bits in each component of
        ///random vectors used for batching.
        unsigned int getNe();

        /// @brief Returns the acceptable "statistical error" when
        /// deriving independent generators.
        unsigned int getNr();

        /// @brief Returns the number of bits in challenges.
        unsigned int getNv();
        
        /// @brief Returns the pseudo-random generator used to derive
        /// random vectors for batching.
        crypto::PRG * getPrg();

        /// @brief Returns the array of random generators.
        ///
        /// @throw An exception is thrown is h has not been computed
        /// yet.
        arithm::CollectionOfElmts getH();

        /// @brief Returns the group of prime order with standard
        /// generator g.
        arithm::Group * getGq();

        /// @brief Returns the field in which lay the exponents of gq
        /// elements.
        arithm::Field * getZq();

        /// @brief Returns the plaintext group.
        arithm::Group * getMkappaOmega();

        /// @brief Returns the public key group.
        arithm::Group * getCkappa();

        /// @brief Returns the ciphertext group.
        arithm::Group * getCkappaOmega();

        /// @brief Returns the randomness field.
        arithm::Field * getRkappaOmega();
        
        /// @}        
};


}
} // closing namespace


#endif
