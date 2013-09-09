/**
 * @file   field.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-03-30 21:22:09 leo>
 * 
 * @brief  The source code of the Field class.
 * 
 * @see field.hpp
 */


#include "field.hpp"


using namespace cppVerifier;
using namespace cppVerifier::arithm;


Field::Field(mpz_class aOrder) : Group(1,NULL)
{
        addOrder = aOrder;
}


Field::Field(mpz_class aOrder, mpz_class mOrder, Elmt * gen) :
        Group(mOrder,gen)
{
        addOrder = aOrder;
}



// ----------------------------------------- Additive element operations



Elmt * Field::add(Elmt * e1, Elmt * e2)
{
        return NULL;
}


Elmt * Field::addInverse(Elmt * e)
{
        return NULL;
}


Elmt * Field::soust(Elmt * e1, Elmt * e2)
{
        return add(e1, addInverse(e2));
}




// ------------------------------------------ Additive array operations



CollectionOfElmts Field::add(CollectionOfElmts e1, CollectionOfElmts e2)
{
        if (e1.size() != e2.size())
        {
                std::cout<<"ERROR: in Field.addition(e1,e2), sizes of "
                         <<"e1 and e2 mismatch."
                         <<"\ne1.size()"<<e1.size()
                         <<"\ne2.size()"<<e2.size()<<std::endl;
                exit(1);
        }
        CollectionOfElmts result;
        for (unsigned int i=0; i<e1.size(); i++)
                result.addElmt(add(e1[i],e2[i]));
        return result;
}


CollectionOfElmts Field::addInverse(CollectionOfElmts a)
{
        CollectionOfElmts result;
        for (unsigned int i=0; i<a.size(); i++)
                result.addElmt(addInverse(a[i]));
        return result;
}


CollectionOfElmts Field::soust(CollectionOfElmts e1, CollectionOfElmts e2)
{
        if (e1.size() != e2.size())
        {
                std::cout<<"ERROR: in Field.addition(e1,e2), sizes of "
                         <<"e1 and e2 mismatch."
                         <<"\ne1.size()"<<e1.size()
                         <<"\ne2.size()"<<e2.size()<<std::endl;
                exit(1);
        }
        CollectionOfElmts result;
        for (unsigned int i=0; i<e1.size(); i++)
                result.addElmt(soust(e1[i],e2[i]));
        return result;
}


Elmt * Field::sum(CollectionOfElmts a)
{
        Elmt * result = getZero();
        for (unsigned int i=0; i<a.size(); i++)
                result = add(result, a[i]);
        return result;
}



// ---------------------------------------- Obtaining field elements



Elmt * Field::getZero()
{
        return NULL;
}


CollectionOfElmts Field::getZero(unsigned int n)
{
        CollectionOfElmts result;
        for (unsigned int i=0; i<n; i++)
                result.addElmt(getZero());
        return result;
}



// ----------------------------------------- Data about the field.



mpz_class Field::getAddOrder()
{
        return addOrder;
}


std::string Field::getType()
{
        return "Field";
}
