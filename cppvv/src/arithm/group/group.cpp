/**
 * @file   group.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-03 18:53:17 leo>
 * 
 * @brief  The source code of the Group class.
 * 
 * @see group.hpp
 */


#include "group.hpp"


using namespace cppVerifier;
using namespace cppVerifier::arithm;



Group::Group(mpz_class order, Elmt * gen)
{
        multOrder = order;
        if (gen == NULL)
                generator = NULL;
        else
                generator = gen->copy();
}



// !SECTION! ByteTree operations
// ===================================================================


utils::ByteTree * Group::toByteTree()
{
        return NULL;
}


Elmt * Group::getElmt(utils::ByteTree * bt)
{
        return NULL;
}


utils::ByteTree * Group::getByteTree(Elmt * e)
{
        return NULL;
}


utils::ByteTree * Group::getByteTree(CollectionOfElmts c)
{
        utils::ByteTree * result = new utils::Node();
        for (unsigned int i=0; i<c.size(); i++)
                result->addChild(getByteTree(c[i]));
        return result;
}


CollectionOfElmts Group::getCollection(utils::ByteTree * bt)
{
        if (!bt->isNode())
        {
                throw utils::Exception(
                        "In Group.getCollection(bt): bt is not a Node."
                        );
        }
        else
        {
                CollectionOfElmts result;
                for (unsigned int i=0; i<bt->size(); i++)
                {
                        try
                        {
                                result.addElmt(getElmt(bt->getChild(i)));
                        }
                        catch (utils::Exception exc)
                        {
                                utils::treatException(
                                        "In Group.getCollection(bt).", exc);
                        }                                
                }
                return result;
        }
}


CollectionOfElmts Group::getCiphers(utils::ByteTree * bt)
{
        return CollectionOfElmts();
}




// !SECTION! Element operations



Elmt * Group::mult(Elmt * e1, Elmt * e2)
{
        return NULL;
}


Elmt * Group::square(Elmt * e)
{
        return mult(e,e);
}


Elmt * Group::multInverse(Elmt * e)
{
        return NULL;
}


Elmt * Group::div(Elmt * e1, Elmt * e2)
{
        return mult(e1, multInverse(e2));
}


Elmt * Group::exp(Elmt * e0, Elmt * t)
{
        mpz_t exponent;
        try 
        {
                mpz_init_set(exponent, t->getValue().get_mpz_t());
        }
        catch (utils::Exception e)
        {
                utils::treatException("In Group.exp.\n",e);
        }
        unsigned int expLength = mpz_sizeinbase(exponent,2);
        Elmt * base = e0->copy();
        Elmt * result = getOne();
        for (unsigned int i=0; i<expLength; i++)
        {
                if (mpz_tstbit(exponent,i) == 1)
                        result = mult(result, base);
                base = square(base);
        }
        return result;
}


bool Group::compare(Elmt * e1, Elmt * e2)
{
        return false;
}




// !SECTION! Array operations
// ===================================================================


CollectionOfElmts Group::mult(CollectionOfElmts e1, CollectionOfElmts e2)
{
        if (e1.size() != e2.size())
        {
                throw utils::Exception(
                        "In Group.mult(Collections):  collections are "
                        "not of the same size."
                        );
        }
        CollectionOfElmts result;
        for (unsigned int i=0; i<e1.size(); i++)
                result.addElmt(mult(e1[i], e2[i]));
        return result;
}


CollectionOfElmts Group::multInverse(CollectionOfElmts e)
{
        CollectionOfElmts result;
        for (unsigned int i=0; i<e.size(); i++)
                result.addElmt(multInverse(e[i]));
        return result;
}



CollectionOfElmts Group::div(CollectionOfElmts e1, CollectionOfElmts e2)
{
        if (e1.size() != e2.size())
        {
                throw utils::Exception(
                        "In Group.mult(Collections): Collections are "
                        "not of the same size."
                        );
        }
        CollectionOfElmts result;
        for (unsigned int i=0; i<e1.size(); i++)
                result.addElmt(div(e1[i],e2[i]));
        return result;
}


CollectionOfElmts Group::exp(CollectionOfElmts e, CollectionOfElmts s)
{
        if (e.size() != s.size())
        {
                throw utils::Exception(
                        "In Group.exp(Collections) Collections are "
                        "not of the same size."
                        );
        }
        CollectionOfElmts result;
        for (unsigned int i=0; i<e.size(); i++)
                result.addElmt(exp(e[i], s[i]));
        return result;
}


Elmt * Group::product(CollectionOfElmts e)
{
        Elmt * result = getOne();
        for (unsigned int i=0; i<e.size(); i++)
                result = mult(result,e[i]);
        return result;
}


Elmt * Group::partialProduct(CollectionOfElmts e, unsigned int n)
{
        Elmt * result = getOne();
        unsigned int endLoop = n < e.size() ? n : e.size();
        for (unsigned int i=0; i<endLoop; i++)
                result = mult(result,e[i]);
        return result;        
}


Elmt * Group::expProduct(CollectionOfElmts e, CollectionOfElmts s)
{
        unsigned int endLoop = s.size() < e.size() ? s.size() : e.size();

        // if (e.size() != s.size())
        // {
        //         throw utils::Exception(
        //                 "In Group.expProduct(Collections): Collections "
        //                 "are not of the same size ("
        //                 + utils::num2str(e.size()) + " != "
        //                 + utils::num2str(s.size()) + ").");
        // }

        Elmt * result = getOne();
        for (unsigned int i=0; i<endLoop; i++)
                result = mult(result, exp(e[i],s[i]));
        return result;
}


Elmt * Group::partialExpProduct(CollectionOfElmts e,
                                CollectionOfElmts s, unsigned int n)
{
        if (e.size() < n && s.size() < n)
        {
                throw utils::Exception(
                        "In Group.partialExpProduct(Collections): at least one"
                        "collection has less than n elements."
                        );
        }
        Elmt * result = getOne();
        for (unsigned int i=0; i<n; i++)
                result = mult(result, exp(e[i],s[i]));
        return result;        
}


bool Group::compare(CollectionOfElmts e1, CollectionOfElmts e2)
{
        if (e1.size() != e2.size())
        {
                throw utils::Exception(
                        "In Group.compare(collections): Collections are"
                        " not of the same size."
                        );
        }
        for (unsigned int i=0; i<e1.size(); i++)
                if (! compare(e1[i],e2[i]) )
                        return false;
        return true;
}


        


// !SECTION! Obtaining element
// ===================================================================


Elmt * Group::getOne()
{
        return NULL;
}


CollectionOfElmts Group::getOnes(unsigned int n)
{
        CollectionOfElmts result;
        for (unsigned int i=0; i<n; i++)
                result.addElmt(getOne());
        return result;
}


CollectionOfElmts Group::getRandArray(
        crypto::PRG * prg,
        unsigned int nr,
        unsigned int n0)
{
        CollectionOfElmts result;
        return result;
}



// !SECTION! Data about the group
// ===================================================================


mpz_class Group::getMultOrder()
{
        return multOrder;
}

Elmt * Group::getGenerator()
{
        return generator;
}
        


CollectionOfElmts Group::getGenerators(unsigned int n)
{
        CollectionOfElmts result;
        for (unsigned int i=0; i<n; i++)
                result.addElmt(getGenerator());
        return result;
}


bool Group::isIn(Elmt * e)
{
        return false;
}


std::string Group::getType()
{
        return "Group";
}


unsigned int Group::getWidth()
{
        return 1;
}
