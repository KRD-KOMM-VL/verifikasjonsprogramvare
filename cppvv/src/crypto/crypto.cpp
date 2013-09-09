/**
 * @file   crypto.cpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-03-11 22:20:36 leo>
 * 
 * @brief Contains the implementation of crypto::getHash().
 */


#include "crypto.hpp"

using namespace cppVerifier;


crypto::SHAx * crypto::getHash(std::string hashName)
{
        if (hashName.compare("SHA-256") == 0)
                return new crypto::SHA256();
        else if (hashName.compare("SHA-384") == 0)
                return new crypto::SHA384();
        else if (hashName.compare("SHA-512") == 0)
                return new crypto::SHA512();
        else
        {
                std::string message = "In crypto::getHash(): incorrect "
                        "hash type: ";
                message.append(hashName);
                throw utils::Exception(message);
        }
}
