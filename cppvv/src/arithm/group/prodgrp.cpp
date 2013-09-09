/**
 * @file   prodgrp.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-03 18:45:22 leo>
 * 
 * @brief  The source code of the ProdGrp class.
 * 
 * @see prodgrp.hpp
 */


#include "prodgrp.hpp"


using namespace cppVerifier;
using namespace cppVerifier::arithm;


ProdGrp::ProdGrp(Group * b, unsigned int w) : Group(b->getMultOrder(),NULL)
{
        baseGroup = b;
        width = w;
        generator = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
                generator->getCoord(i) = b->getGenerator();
}



// !SECTION! Bytetree
// ==================================================================


Elmt * ProdGrp::getElmt(utils::ByteTree * bt)
{
        Elmt * res = new ProdGrpElmt(bt->size());
        try {
                for (unsigned int i=0; i<bt->size(); i++)
                        res->getCoord(i) = baseGroup->getElmt(bt->getChild(i));
        } catch(utils::Exception e) {
                utils::treatException(
                        "In ProdGrp.getElmt(e).\n",e);
        }
        
        return res;
}


utils::ByteTree * ProdGrp::getByteTree(Elmt * e)
{
        utils::ByteTree * res = new utils::Node();
        for (unsigned int i=0; i<width; i++)
        {
                try {
                        res->addChild(
                                baseGroup->getByteTree(e->getCoord(i))
                                );
                } catch(utils::Exception e) {
                        utils::treatException(
                                "In ProdGrp.getByteTree(e).\n",e);
                }
        }
        return res;
}


utils::ByteTree * ProdGrp::getByteTree(CollectionOfElmts c)
{
        utils::ByteTree * res = new utils::Node();
        for (unsigned int i=0; i<width; i++)
        {
                utils::ByteTree * ithChild = new utils::Node();
                for (unsigned int k=0; k<c.size(); k++)
                {
                        try
                        {
                                ithChild->addChild(
                                        baseGroup->getByteTree(
                                                c[k]->getCoord(i)));
                        }
                        catch(utils::Exception exc)
                        {
                                utils::treatException(
                                        "In ProdGrp.getByteTree(c).\n",
                                        exc);
                        }
                }
                res->addChild(ithChild);
        }
        return res;
}




CollectionOfElmts ProdGrp::getCiphers(utils::ByteTree * bt)
{
        if (!bt->isNode() || bt->size() != 2)
                throw utils::Exception("In Group.getCiphers(bt): bt is "
                                       "supposed to be a node with 2 children."
                                       "\nbt = " + bt->toString());
        else if (bt->getChild(0)->size() != bt->getChild(0)->size())
                throw utils::Exception("In Group.getCiphers(bt): bt[0] and "
                                       "bt[1] are supposed to be of the same "
                                       "size.\nbt[0] = "
                                       + bt->getChild(0)->toString()
                                       + "\nbt[1] = " 
                                       + bt->getChild(1)->toString());
        else
        {
                CollectionOfElmts result;
                for (unsigned int i=0; i<bt->getChild(0)->size(); i++)
                {
                        Elmt * u, * v;
                        try
                        {
                                u = baseGroup->getElmt(
                                        bt->getChild(0)->getChild(i));
                                v = baseGroup->getElmt(
                                        bt->getChild(1)->getChild(i));
                        }
                        catch (utils::Exception exc)
                        {
                                utils::treatException(
                                        "In Group.getCiphers(bt): problem "
                                        "reading elements from bt.\nbt = "
                                        + bt->getChild(0)->getChild(i)->toString() + "\n"
                                        + bt->getChild(1)->getChild(i)->toString() + "\n"
                                        , exc);
                        }
                        result.addElmt(new ProdGrpElmt(u,v));
                }
                return result;
        }
}



// !SECTION! Basic operations
// ==================================================================


Elmt * ProdGrp::mult(Elmt * e1, Elmt * e2)
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
        {
                Elmt * op1, * op2;
                try {
                        op1 = e1->getCoord(i);
                } catch(utils::Exception exc) {
                        utils::treatException("In ProdGrp.mult, op1.\n",exc);
                }
                try {
                        op2 = e2->getCoord(i);
                } catch(utils::Exception exc) {
                        utils::treatException("In ProdGrp.mult, op2.\n",exc);
                }
                try {
                        res->getCoord(i) = baseGroup->mult(op1,op2);
                } catch(utils::Exception e) {
                        utils::treatException("In ProdGrp.mult, operation "
                                              "in base group.\n",e);
                }
        }
        return res;
}


Elmt * ProdGrp::multInverse(Elmt * e)
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
        {
                try {
                        res->getCoord(i) =
                                baseGroup->multInverse(e->getCoord(i));
                } catch(utils::Exception exc) {
                        utils::treatException("In ProdGrp.multInverse.\n",exc);
                }
        }
        return res;
}



Elmt * ProdGrp::exp(Elmt * e0, Elmt * s)
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
        {
                Elmt * base, * exponent;
                try
                {
                        base = e0->getCoord(i);
                }
                catch (utils::Exception e)
                {
                        utils::treatException("In ProdGrp.exp, computing base\n",e);
                }
                if (s->isLargeNumber())
                        exponent = s;
                else
                {
                        try
                        {
                                exponent = s->getCoord(i);
                        }
                        catch (utils::Exception e)
                        {
                                utils::treatException("In ProdGrp.exp, computing exponent\n",e);
                        }
                }
                try
                {
                        res->getCoord(i) = baseGroup->exp(base,exponent);
                }
                catch (utils::Exception e)
                {
                        utils::treatException("In ProdGrp.exp, computing in sub-group\n",e);
                }
        }
        return res;
}


bool ProdGrp::compare(Elmt * e1, Elmt * e2)
{
        for (unsigned int i=0; i<width; i++)
        {
                try {
                        if (!baseGroup->compare(
                                    e1->getCoord(i),
                                    e2->getCoord(i)
                                    ))
                                return false;
                } catch(utils::Exception e) {
                        utils::treatException("In ProdGrp.compare.\n",e);
                }
        }
        return true;
}




// !SECTION! Obtaining elements
// ==================================================================


Elmt * ProdGrp::getOne()
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
                res->getCoord(i) = baseGroup->getOne();
        return res;
}


Elmt * ProdGrp::getGenerator()
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
                res->getCoord(i) = baseGroup->getGenerator();
        return res;
}



// !SECTION! Data about the group
// ==================================================================



bool ProdGrp::isIn(Elmt * e)
{
        if (e->getWidth() != width)
                return false;
        for (unsigned int i=0; i<width; i++)
                if (!baseGroup->isIn(e->getCoord(i)))
                        return false;
        return true;
}


std::string ProdGrp::getType()
{
        return "Product of " + utils::num2str(width)
                + " " + baseGroup->getType();
}


unsigned int ProdGrp::getWidth()
{
        return width;
}
