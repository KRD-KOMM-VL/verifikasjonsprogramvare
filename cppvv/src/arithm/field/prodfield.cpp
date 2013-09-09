/**
 * @file   prodfield.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-03 18:48:19 leo>
 * 
 * @brief  The source code of the ProdField class.
 * 
 * @see prodfield.hpp
 */


#include "prodfield.hpp"


using namespace cppVerifier;
using namespace cppVerifier::arithm;


ProdField::ProdField(Field * f, unsigned int w) : Field(f->getAddOrder())
{
        baseField = f;
        multOrder = f->getMultOrder();
        width = w;
        generator = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
                generator->getCoord(i) = f->getGenerator();
}



// ==================== Bytetree ==================== 


Elmt * ProdField::getElmt(utils::ByteTree * bt)
{
        Elmt * res = new ProdGrpElmt(bt->size());
        try {
                for (unsigned int i=0; i<bt->size(); i++)
                        res->getCoord(i) = baseField->getElmt(bt->getChild(i));
        } catch(utils::Exception e) {
                utils::treatException(
                        "In ProdField.getElmt(e).\n",e);
        }

        // for (unsigned int i=0; i<bt->size(); i++)
        //         res->getCoord(i) = baseField->getElmt(bt->getChild(i));
        return res;
}


utils::ByteTree * ProdField::getByteTree(Elmt * e)
{
        utils::ByteTree * res = new utils::Node();
        for (unsigned int i=0; i<width; i++)
        {
                try
                {
                        res->addChild(
                                baseField->getByteTree(e->getCoord(i))
                                );
                }
                catch(utils::Exception exc)
                {
                        utils::treatException("In ProdField.getByteTree(e).",
                                              exc);
                }
        }
        return res;
}



// ================ Basic operations ================


Elmt * ProdField::mult(Elmt * e1, Elmt * e2)
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
        {
                try
                {
                        res->getCoord(i) = baseField->mult(
                                e1->getCoord(i),
                                e2->getCoord(i)
                                );
                }
                catch(utils::Exception e)
                {
                        utils::treatException("In ProdField.mult, multiplication"
                                              "failed for coord i="
                                              + utils::num2str(i), e);
                }
        }
        return res;
}


Elmt * ProdField::multInverse(Elmt * e)
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
        {
                try
                {
                        res->getCoord(i) =
                                baseField->multInverse(e->getCoord(i));
                }
                catch(utils::Exception exc)
                {
                        utils::treatException("In ProdField.multInverse.",exc);
                }
        }
        return res;
}



Elmt * ProdField::exp(Elmt * e0, Elmt * s)
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
                        utils::treatException("In ProdField.exp, computing "
                                              "base failed.",e);
                }
                try
                {
                        exponent = s->getCoord(i);
                }
                catch (utils::Exception e)
                {
                        utils::treatException("In ProdField.exp, computing "
                                              "exponent failed.",e);
                }
                try
                {
                        res->getCoord(i) = baseField->exp(base,exponent);
                }
                catch (utils::Exception exc)
                {
                        utils::treatException("In ProdField.exp, computing in"
                                              " sub-group.",exc);
                }
        }
        return res;
}


Elmt * ProdField::add(Elmt * e1, Elmt * e2)
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
        {
                try
                {
                        res->getCoord(i) = baseField->add(
                                e1->getCoord(i),
                                e2->getCoord(i)
                                );
                }
                catch(utils::Exception exc)
                {
                        utils::treatException("In ProdField.add, addition"
                                              "failed for coord i="
                                              + utils::num2str(i), exc);
                }
        }
        return res;
}


Elmt * ProdField::addInverse(Elmt * e)
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
        {
                try
                {
                        res->getCoord(i) =
                                baseField->addInverse(e->getCoord(i));
                }
                catch(utils::Exception exc)
                {
                        utils::treatException("In ProdField.addInverse, addition"
                                              "failed for coord i="
                                              + utils::num2str(i), exc);
                }
        }
        return res;
}


bool ProdField::compare(Elmt * e1, Elmt * e2)
{
        for (unsigned int i=0; i<width; i++)
        {
                try
                {
                        if (!baseField->compare(
                                    e1->getCoord(i),
                                    e2->getCoord(i)
                                    ))
                                return false;
                }
                catch(utils::Exception e)
                {
                        utils::treatException("In ProdField.compare.\n",e);
                }
        }
        return true;
}



// =============== Obtaining elements ===============


Elmt * ProdField::getOne()
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
                res->getCoord(i) = baseField->getOne();
        return res;
}


Elmt * ProdField::getZero()
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
                res->getCoord(i) = baseField->getZero();
        return res;
}


Elmt * ProdField::getGenerator()
{
        Elmt * res = new ProdGrpElmt(width);
        for (unsigned int i=0; i<width; i++)
                res->getCoord(i) = baseField->getGenerator();
        return res;
}


// ============== Data about the group ==============


bool ProdField::isIn(Elmt * e)
{
        if (e->getWidth() != width)
                return false;
        for (unsigned int i=0; i<width; i++)
                if (!baseField->isIn(e->getCoord(i)))
                        return false;
        return true;
}


std::string ProdField::getType()
{
        return "Product of " + utils::num2str(width)
                + " " + baseField->getType();
}


unsigned int ProdField::getWidth()
{
        return width;
}
