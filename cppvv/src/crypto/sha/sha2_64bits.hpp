/**
 * @file   sha2_64bits.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-21 12:45:00 leo>
 * 
 * @brief  The header of the SHA2_64bits class.
 * 
 * @see sha2_64bits.cpp
 */


#ifndef _SHA2_64BITS_H_
#define _SHA2_64BITS_H_

#include "shax.hpp"


namespace cppVerifier {
namespace crypto {


/**
 * @brief A class to factor the code which is common for SHA-384 and SHA-512.
 */
class SHA2_64bits : public SHAx
{
protected:
        /// @brief The internal state; 8 words of 64 bits.
        uint64_t H[8];

        /// @name 64 bits temporary variables
        ///@{
        uint64_t a;
        uint64_t b;
        uint64_t c;
        uint64_t d;
        uint64_t e;
        uint64_t f;
        uint64_t g;
        uint64_t h;
        ///@}

        /// @brief Message to be hashed.
        std::vector<uint64_t> M;

        /// @brief Length of the message (in bits).
        uint64_t l;

        /// @brief The constants to use in each step of the main loop
        /// of a round.
        std::vector<uint64_t> K;

        /// @brief The constants to which the internal state must be
        /// initialised.
        std::vector<uint64_t> init;

public:
        SHA2_64bits();
        void preprocess(std::vector<uint8_t> initialMessage);
        void round(unsigned int counter);
        void hash(std::vector<uint8_t> initialMessage);

        /// @name 64-bits operations
        /// @{


        /** 
         * @brief Performs a shift over x by n step to the
         * right, x being a 64-bits word and returns the result.
         *
         * @return \f$(x >> n)\f$
         */
        inline uint64_t shr(uint64_t x, unsigned int n)
        {
                return x >> n;
        }
        
        /** 
         * @brief Performs a circular bit shift over x by n step to
         * the right, x being a 64-bits word and returns the result.
         *
         * @return \f$(x >> n) \land (x << 64-n)\f$
         */
        inline uint64_t rotr(uint64_t x, unsigned int n)
        {
                return (x >> n) | (x << (64-n));
        }

        /** 
         * @brief The 64-bit version of the \f$Ch\f$ function.
         *
         * @return \f$(x \land y) \oplus (x \land z)\f$
         */
        inline uint64_t ch(uint64_t x, uint64_t y, uint64_t z)
        {
                return (x & y) ^ ((~x) & z);
        }

        /**
         * @brief The 64-bits version of the \f$Maj\f$ function.
         *
         * @return \f$(x \land y)\oplus(x \land z)\oplus(y \land z)\f$
         */
        inline uint64_t maj(uint64_t x, uint64_t y, uint64_t z)
        {
                return (x & y) ^ (x & z) ^ (y & z);
        }

        /**
         * @brief The \f$\Sigma_0^{\{512\}}\f$ function.
         *
         * @return \f$ROTR^{28}(x) \oplus ROTR^{34}(x) \oplus
         * ROTR^{39}(x)\f$
         */
        inline uint64_t bigSigma0_512(uint64_t x)
        {
                return rotr(x,28) ^ rotr(x,34) ^ rotr(x,39);
        }

        /**
         * @brief The \f$\Sigma_1^{\{512\}}\f$ function.
         *
         * @return \f$ROTR^{14}(x) \oplus ROTR^{18}(x) \oplus
         * ROTR^{41}(x)\f$
         */
        inline uint64_t bigSigma1_512(uint64_t x)
        {
                return rotr(x,14) ^ rotr(x,18) ^ rotr(x,41);
        }

        /**
         * @brief The \f$\sigma_0^{\{512\}}\f$ function.
         *
         * @return \f$ROTR^1(x) \oplus ROTR^{8}(x) \oplus SHR^7(x)\f$
         */
        inline uint64_t smallSigma0_512(uint64_t x)
        {
                return rotr(x,1) ^ rotr(x,8) ^ shr(x,7);
        }

        /**
         * @brief The \f$\sigma_1^{\{512\}}\f$ function.
         *
         * @return \f$ROTR^{19}(x) \oplus ROTR^{61}(x) \oplus
         * SHR^{6}(x)\f$
         */
        inline uint64_t smallSigma1_512(uint64_t x)
        {
                return rotr(x,19) ^ rotr(x,61) ^ shr(x,6);
        }

        /// @}

};

}
} // closing namespace

#endif
