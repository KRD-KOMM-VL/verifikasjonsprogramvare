/**
 * @file   sha256.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-21 12:17:13 leo>
 * 
 * @brief The header for the SHA-256 hashfunction of the SHA-2 family.
 *
 * @see sha256.cpp
 */
#ifndef _SHA256_H_
#define _SHA256_H_

#include "shax.hpp"


namespace cppVerifier {
namespace crypto {


/**
 * @class SHA256
 *
 * @brief Implements the SHA256 hashfunction, i.e. sha-256. Inherits
 * from SHAx.
 */
class SHA256 : public SHAx
{
private:
        /// @brief The internal state; 8 words of 32 bits.
        uint32_t H[8];
        
        /// @name 32 bits temporary variables
        ///@{
        uint32_t a;
        uint32_t b;
        uint32_t c;
        uint32_t d;
        uint32_t e;
        uint32_t f;
        uint32_t g;
        uint32_t h;
        ///@}

        /// @brief The message to hash.
        std::vector<uint32_t> M;

        /// @brief The length of the message in bits.
        uint64_t l;

        /// @brief The constants to use in each step of the main loop.
        std::vector<uint32_t> K;

public:
        SHA256();
        void preprocess(std::vector<uint8_t> initialMessage);
        void round(unsigned int counter);
        void hash(std::vector<uint8_t> initialMessage);
        std::vector<uint8_t> getHash();
        std::string getType();
        uint32_t getHashLength();

        /// @name 32-bits operations
        /// @{

        /** 
         * @brief Performs a simple shift over x by n step to the
         * right and returns the result.
         *
         * @return \f$(x >> n)\f$
         */
        inline uint32_t shr(uint32_t x, unsigned int n)
        {
                return (x >> n);
        }

        /** 
         * @brief Performs a circular bit shift over x by n step to
         * the right, and returns the result.
         *
         * @return \f$(x >> n) \land (x << 32-n)\f$
         */
        inline uint32_t rotr(uint32_t x, unsigned int n)
        {
                return (x >> n)|(x << (32-n));
        }

        /** 
         * @brief The 32-bit version of the \f$Ch\f$ function.
         *
         * @return \f$(x \land y) \oplus (x \land z)\f$
         */
        inline uint32_t ch(uint32_t x, uint32_t y, uint32_t z)
        {
                return (x & y)^((~x) & z);
        }

        /**
         * @brief The 32-bits version of the \f$Maj\f$ function.
         *
         * @return \f$(x \land y)\oplus(x \land z)\oplus(y \land z)\f$
         */
        inline uint32_t maj(uint32_t x, uint32_t y, uint32_t z)
        {
                return (x & y)^(x & z)^(y & z);
        }

        /**
         * @brief The \f$\Sigma_0^{\{256\}}\f$ function.
         *
         * @return \f$ROTR^2(x) \oplus ROTR^{13}(x) \oplus
         * ROTR^{22}(x)\f$
         */
        inline uint32_t bigSigma0_256(uint32_t x)
        {
                return rotr(x,2)^rotr(x,13)^rotr(x,22);
        }

        /**
         * @brief The \f$\Sigma_1^{\{256\}}\f$ function.
         *
         * @return \f$ROTR^6(x) \oplus ROTR^{11}(x) \oplus
         * ROTR^{25}(x)\f$
         */
        inline uint32_t bigSigma1_256(uint32_t x)
        {
                return rotr(x,6)^rotr(x,11)^rotr(x,25);
        }

        /**
         * @brief The \f$\sigma_0^{\{256\}}\f$ function.
         *
         * @return \f$ROTR^7(x) \oplus ROTR^{18}(x) \oplus SHR^3(x)\f$
         */
        inline uint32_t smallSigma0_256(uint32_t x)
        {
                return rotr(x,7)^rotr(x,18)^shr(x,3);
        }

        /**
         * @brief The \f$\sigma_1^{\{256\}}\f$ function.
         *
         * @return \f$ROTR^{17}(x) \oplus ROTR^{19}(x) \oplus
         * SHR^{10}(x)\f$
         */
        inline uint32_t smallSigma1_256(uint32_t x)
        {
                return rotr(x,17)^rotr(x,19)^shr(x,10);
        }
        
        /// @}
};
 
       
}
} // closing namespace

#endif
