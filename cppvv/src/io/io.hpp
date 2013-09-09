/**
 * @file   io.hpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-03-15 14:13:42 leo>
 * 
 * @brief Allows other parts of the program to include only one file
 * when using classes from the io library.
 */

#ifndef _IO_H_
#define _IO_H_


// The files we are actually interested in.
#include "log.hpp"
#include "xmlreader.hpp"
#include "arguments.hpp"



namespace cppVerifier
{

/**
 * @namespace cppVerifier::io 
 * 
 * @brief Implements basic input and output operations for a verifier.
 *
 * Input is mainly an xml file so functions to parse it are provided
 * by the XmlReader class.
 *
 * The class Arguments makes sense of this configuration file and
 * provides a simple interface to access the variables it defined.
 *
 * Logs can be kept during execution; they are dealt with by the Log
 * class.
 **/
namespace io
{
}

}
        
#endif
