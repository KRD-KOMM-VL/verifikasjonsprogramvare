/**
 * @file   btrtest.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-08-26 14:38:40 leo>
 * 
 * @brief   A test suite for the whole ByteTree module which is intended
 * to be used in coordination with a python script.
 *
 * It is meant to be used in coordination with the unitarytest.py
 * python script so as to compare its behaviour with that of the
 * reference implmentation. The functions in this file are sort of
 * interfaces between the actual classes tested and the unitary tests
 * provided by the python script and based on the reference
 * implementation (which is in python). This script produces a test
 * vector, "./input.txt", and a file containing what this program
 * should output, "./ideal_out.txt".
 *
 * For each non-virtual function in this module, there is a unitary
 * test reading its data line by line. It reads one line of the
 * "./input.txt" file, processes it, writes one line in
 * "./actual_out.txt" and continues until the end of the input file is
 * found.
 * 
 * In the end, the differences between "ideal_out.txt" and
 * "actual_out.txt" are computed. If there is even one error, then you
 * have a problem.
 */

#include <gmp.h>
#include <gmpxx.h>
#include <fstream>
#include <cstring>

#include "utils.hpp"


#define PATH_TO_INPUT_FILE  "./input.txt"
#define PATH_TO_OUTPUT_FILE "./actual_output.txt"


using namespace cppVerifier;
using namespace cppVerifier::utils;


/**
 * @brief Reads hexadecimal string representation of ByteTrees from
 * input.txt and outputs the same representation on actual_output.txt.
 *
 * @param input  The input file at PATH_TO_INPUT_FILE (read).
 * @param output The output file at PATH_TO_OUTPUT_FILE (write).
 */
void test_ByteTree_parseString(std::ifstream &input,
                               std::ofstream &output)
{
        ByteTree * bt;
        std::string line;
        while (input>>line)
        {
                bt = ByteTree::parseString(line);
                output<<bt->toString()<<std::endl;
        }
}


/**
 * @brief Reads in input.txt the path to a file containing a bytetree,
 * parses it and outputs the content of it on actual_output.txt.
 *
 * @param input  The input file at PATH_TO_INPUT_FILE (read).
 * @param output The output file at PATH_TO_OUTPUT_FILE (write).
 */
void test_ByteTree_parseFile(std::ifstream &input,
                             std::ofstream &output)
{
        ByteTree * bt;
        std::string line;
        while (input>>line)
        {
                bt = ByteTree::parseFile(line);
                output<<bt->toString()<<std::endl;
        }
}


/**
 * @brief Reads decimal representations of large integers, constructs
 * Leaf's instances from them and then turns them into integers again.
 * 
 * Integers are read from input.txt and output in actual_output.txt.
 *
 * @param input  The input file at PATH_TO_INPUT_FILE (read).
 * @param output The output file at PATH_TO_OUTPUT_FILE (write).
 */
void test_Leaf_toInteger(std::ifstream &input, std::ofstream &output)
{
        ByteTree * bt;
        std::string line;
        while (input>>line)
        {
                mpz_class integer(line,10);
                bt = new Leaf(integer);
                output<<bt->toInteger()<<std::endl;
        }
}


/**
 * @brief Reads the decimal representations of large integers,
 * constructs Leaf's instances from them and then turns them into
 * boolean arrays.
 * 
 * Integers are read from input.txt and boolean arrays are output in
 * actual_output.txt.
 *
 * @param input  The input file at PATH_TO_INPUT_FILE (read).
 * @param output The output file at PATH_TO_OUTPUT_FILE (write).
 */
void test_Leaf_toBoolArray(std::ifstream &input, std::ofstream &output)
{
        ByteTree * bt;
        unsigned int number;
        while (input>>number)
        {
                std::vector<uint8_t> content(number);
                for (unsigned int i=0; i<number; i++)
                {
                        bool b;
                        input>>b;
                        if (b)
                                content[i] = 1;
                        else
                                content[i] = 0;
                }
                bt = new Leaf(content);
                std::vector<bool> array = bt->toBoolArray();
                for (unsigned int i=0; i<array.size(); i++)
                        if (array[i])
                                output<<"1";
                        else
                                output<<"0";
                output<<std::endl;
        }
}


/** 
 * @brief Prints the help of this program, including a list of the
 * functions that can be tested.
 */
void printHelp()
{
        std::cout<<"Run ./bt_test <function to test> and it will parse the"
                 <<PATH_TO_INPUT_FILE<< "file to produce the "
                 <<PATH_TO_OUTPUT_FILE<<" file.  If this is giberish to you,"
                 <<"you\nshould read the documentation ^^"<<"\n\n"
                 <<"Available functions are:"<<"\n"
                 <<" - ByteTree::parseString\n"
                 <<" - ByteTree::parseFile\n"
                 <<" - Leaf.toInteger\n"
                 <<" - Leaf.toBoolArray\n"
                 <<"\n"<<std::endl;
}



int main(int argc, char ** argv)
{
        if (argc<2)
                printHelp();
        else
        {
                std::ifstream input(PATH_TO_INPUT_FILE,
                                    std::ifstream::in);
                std::ofstream output(PATH_TO_OUTPUT_FILE);
                if (strcmp(argv[1],"ByteTree::parseString") == 0)
                        test_ByteTree_parseString(input,output);
                else if (strcmp(argv[1],"ByteTree::parseFile") == 0)
                        test_ByteTree_parseFile(input,output);
                else if (strcmp(argv[1],"Leaf.toInteger") == 0)
                        test_Leaf_toInteger(input,output);
                else if (strcmp(argv[1],"Leaf.toBoolArray") == 0)
                        test_Leaf_toBoolArray(input,output);
                else
                        printHelp();
                output.close();
        }        
        
	return 0;
}
