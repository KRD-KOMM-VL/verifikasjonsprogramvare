/**
 * @file   log.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-03-15 14:44:14 leo>
 * 
 * @brief  The source code of the Log class.
 * 
 * @see log.hpp
 */


#include <fstream>
#include <iostream>

#include "log.hpp"

using namespace cppVerifier;
using namespace cppVerifier::io;


Log::Log(std::string place)
{
        if (place.compare("none") == 0)
                output = NULL;
        else if (place.compare("stdout") == 0)
                output = &std::cout;
        else
                output = new std::ofstream(place);
}


void Log::write(std::string logLine)
{
        if (output != NULL)
                (*output) << logLine << std::endl;
}


Log::~Log()
{
        if (output != NULL)
                output->flush();
}
