/**
 * @file   modpgrp.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-11-18 21:32:15 leo>
 * 
 * @brief  The source code of the ModPGrp class.
 * 
 * @see modpgrp.hpp
 */


#include "modpgrp.hpp"
#include "../field/modfield.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;


ModPGrp::ModPGrp(mpz_class ord, mpz_class add,
                 mpz_class gen, uint8_t encoding) :
        PSubGroup(
                new ModField(add),    // base field
                ord,                  // order of the psubgroup
                new LargeNumber(gen)  // generator
                )
{
        code = encoding;
        addOrder = add;
}


ModPGrp::ModPGrp(utils::ByteTree * bt) :
        PSubGroup(
                new ModField(bt->getChild(0)->toInteger()),
                bt->getChild(1)->toInteger(),
                new LargeNumber(bt->getChild(2)->toInteger())
                )
{
        if (!bt->isNode())
        {
                std::cout<<"ERROR: in ModPGrp(bt), bt is not a node. "
                         <<"\nbt=";
                bt->prettyPrint("");
                std::cout<<std::endl;
                exit(1);
        }
        addOrder = bt->getChild(0)->toInteger();
        code = bt->getChild(3)->toInteger().get_ui();
}


utils::ByteTree * ModPGrp::toByteTree()
{
        utils::ByteTree * res = new utils::Node();
        res->addChild(baseGroup->getByteTree(new LargeNumber(addOrder )));
        res->addChild(baseGroup->getByteTree(new LargeNumber(multOrder)));
        res->addChild(baseGroup->getByteTree(generator));
        res->addChild(new utils::Leaf(code,4));
        return res;
}


CollectionOfElmts ModPGrp::getRandArray(crypto::PRG * prg, unsigned int nr,
                          unsigned int n0)
{
        CollectionOfElmts result;
        unsigned int np = mpz_sizeinbase(addOrder.get_mpz_t(),2);
        unsigned int tiLength = (np+nr)/8; // The byte length of the
                                           // t_i:s
        mpz_class ti(0);
        mpz_class base(2),
                modulo(0);
        mpz_pow_ui(modulo.get_mpz_t(),
                   base.get_mpz_t(),
                   np+nr);
        for (unsigned int i=0; i<n0; i++)
        {
                ti = 0;
                for (unsigned int j=0; j<=tiLength; j++)
                        ti = ti*0x100 + prg->getNextRandByte();
                ti = (ti % modulo);
                // don't use this->exp as 'ti' may very well not be in
                // this modular p-group.
                mpz_powm(ti.get_mpz_t(),
                         ti.get_mpz_t(),
                         coOrder.get_mpz_t(),
                         addOrder.get_mpz_t());
                result.addElmt(new LargeNumber(ti));
        }
        return result;
}


