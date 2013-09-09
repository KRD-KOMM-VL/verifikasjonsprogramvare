/**
 * @file   modpgrp.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-11-18 17:52:57 leo>
 * 
 * @brief  The header of the ModPGrp class.
 * 
 * @see modpgrp.cpp
 */


#ifndef _MODPGRP_H_
#define _MODPGRP_H_

#include "psubgroup.hpp"

namespace cppVerifier {
namespace arithm {

/**
 * @brief Implements a q-subgroup of a modular field.
 */
class ModPGrp : public PSubGroup
{
private:
        /**
         * @brief The type of encoding to use to parse and store
         * messages using Elmts of this group.
         *
         * This is not used anymore but is kept to still have the same
         * bytetree format (retro-compatibility).
         */
        uint8_t code;

        /**
         * @brief The additive order of the base ModField.
         */
        mpz_class addOrder;

public:
        /**
         * @brief Initializes a modular p-subgroup of order ord by
         * building a new ModField instance of order add, setting it
         * to be the baseGroup and using gen as a generator.
         */
        ModPGrp(mpz_class ord, mpz_class add, mpz_class gen, uint8_t encoding);

        /** 
         * @brief Parses a ByteTree to create a ModPGrp.
         *
         * @param bt A Node containing 4 leaves:
         *   - the additive order
         *   - the order of this subgroup
         *   - the generator
         *   - the encoding to use
         */
        ModPGrp(utils::ByteTree * bt);

        utils::ByteTree * toByteTree();

        CollectionOfElmts getRandArray(crypto::PRG * prg, unsigned int nr, unsigned int n0);
};

}
} // closing namespace

#endif
