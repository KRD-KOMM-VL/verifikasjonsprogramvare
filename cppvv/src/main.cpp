/**
 * @file   main.cpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-09-06 22:06:38 leo>
 * 
 * @brief  Contains the main function of the c++ verificatum verifier.
 */


#include <boost/program_options.hpp>
#include <vector>
#include <string>
#include <iostream>
#include <fstream>
#include <iomanip>

#include "proof/algorithms.hpp"


using namespace cppVerifier;

// This namespace's name is longer than a longcat >_<"
namespace po = boost::program_options;


/**
 * @brief Prints on the screen a space separated list of the supported
 * versions of the protocol.
 */
void displayVersions()
{
        std::cout<<"1.1.7"<<std::endl;
}


/**
 * @brief Adds a hyphen at the beginning of the string if it is an
 * option name (i.e. it starts with '-'), leaves it untouched
 * otherwise.
 *
 * Used to circumvent the difference in formatting for the CLI options
 * between the specification and the default boost::parse_option
 * behaviour.
 */
char * addHyphen(const char * s)
{
        if (s[0] == '-')
        {
                char * result = (char *)malloc(strlen(s) + 1);
                sprintf(result, "-%s", s);
                return result;
        }
        else
                return (char *)s;
}


/**
 * @brief Builds a help message from the description of the command
 * line arguments.
 *
 * Actually, the help message is essentially provided by
 * boost::parse_options::options_description. However, we still have
 * the problem of the double hyphens. Thus, we generate the usual
 * error message by printing it in a string stream and then replace
 * all double hyphens in it by space + simple ones.
 */
std::string getHelp(po::options_description opt)
{
        std::ostringstream helpStream("");
        std::string
                help = "",
                actualHelp = "";
        helpStream << opt;
        help = helpStream.str();
        bool jump = false;
        for (unsigned int i=0; i<help.size(); i++)
                if (help[i] == '-' && !jump)
                {
                        actualHelp.push_back(' ');
                        jump = true;
                }
                else
                {
                        jump = false;
                        actualHelp.push_back(help[i]);
                }
        return actualHelp;
}


/**
 * @brief Parses the command line arguments and calls the
 * corresponfing subroutines.
 *
 * Arguments parsing is done using boost::program_options. First we
 * initialize the variable for this session from the CLI args and then
 * we call the functions implementing the different algorithms
 * depending on the options set in the command.
 * 
 * @param argc The number of CLI arguments.
 * @param argv The actual CLI arguments.
 * 
 * @return 0 if the program ran successfully, 1 if everything went
 * fine but the ciphertexts didn't pass the verification and 2 if an
 * internal error occured.
 */
int main(int argc, char ** argv)
{
        // Parameters of the session
        std::string auxsidExpected;
        unsigned int omegaExpected;

        
        po::options_description cliOptions("Options");
        po::variables_map cliContent;

        // !SECTION! Setting up the CLI parser

        // !SUBSECTION! Adding an hyphen in front of every option

        // Yes, this hack is ugly, but it is understandable and works
        // just fine. argvBis is the same as argv except that
        // everytime an argument starts with '-', a second '-' is
        // added to its beginning.
        char **argvBis = (char **)malloc(sizeof(char*) * argc);
        for (int i=0; i<argc; i++)
                argvBis[i] = addHyphen(argv[i]);


        // !SUBSECTION! Setting up CLI options
        cliOptions.add_options()
                // Display simple messages
                ("help",
                 "Displays this help message and exits.")
                ("compat",
                 "Displays the versions of the protocol supported by this "
                 "verifier and exits.")
                // Perform actual verification
                ("shuffle",
                 "Performs a proof of shuffle. The path to the"
                 " protocol info file and the path to the proof directory must"
                 " be supplied (in this order).")
                // Setting session variables
                ("auxsid",
                 po::value<std::string>(&auxsidExpected)->default_value("default"),
                 "An auxiliary identifier for the session. Has to match the"
                 " regex \"[A-Za-z0-9_]+\"; defaults to \"default\".")
                ("width",
                 po::value<unsigned int>(&omegaExpected)->default_value(0),
                 "The width omega of the ciphertexts as a decimal number. May"
                 " match the one in the config file or not.")
                ("v",
                 "Turns on a verbose output.")
                ;

        // !SUBSECTION! Declaring the hidden option
        // The hidden options are the name of the protocol info file
        // and proof directory (don't show up in help).
        po::options_description hiddenOptions("hidden");
        hiddenOptions.add_options()
                ("protInfo",
                 po::value<std::string>(),
                 "The path to the protocol info file"
                        )
                ("directory-or-default",
                 po::value<std::string>(),
                 "The path to the proof diirectory"
                 " or the \"default\" string."
                        )
                ;
        po::positional_options_description positionalArgs;
        positionalArgs.add("protInfo",1).add("directory-or-default",-1);

        // !SUBSECTION! Fusion of all arguments.
        // We fusion positional arguments and options to be able to
        // retrieve all of them.
        po::options_description allOptions;
        allOptions.add(cliOptions).add(hiddenOptions);
        
        po::parsed_options parsed = po::command_line_parser(argc, argvBis)
                .options(allOptions)
                .positional(positionalArgs)
                .allow_unregistered()
                .run();

        po::store(parsed, cliContent);
        po::notify(cliContent);


        // !SECTION! Main if, dependent on the CLI args

        // !SUBSECTION! help, compat and verbose
        if (cliContent.count("help") || argc < 2)
        {
                std::cout<<getHelp(cliOptions)<<std::endl ;
                return 0;
        }
        else if (cliContent.count("compat"))
        {
                displayVersions();
                return 0;
        }

        // !SUBSECTION! Setting up logging of the execution and return value
        io::Log * log;
        if (cliContent.count("v"))
                log = new io::Log("stdout");
        else
                log = new io::Log("none");

        // We will return the following value in the end (unless basic
        // error requiring immediat exit 2 have been made).
        int toReturn = 0;


        // !SUBSECTION! First we check that we do have the protInfo.xml and directory
        if (!cliContent.count("protInfo")
            || !cliContent.count("directory-or-default"))
        {
                std::cout<<"[ERROR] protocol info file AND direcory (or"
                        " \"default\") needed!"<<std::endl;
                return 2;
        }
        log->write("[DONE] Protocol info file and proof directory set up.");               
        
        // !SUBSECTION! Checking the shuffling
        if (cliContent.count("shuffle"))
        {
                try
                {
                        toReturn = proof::algorithm_19(
                                log,
                                cliContent["protInfo"].as<std::string>(), // protinfo
                                cliContent["directory-or-default"].as<std::string>(), // proof directory
                                "default", // auxsid_expected
                                -1 // omega_expected
                                );
                }
                catch (utils::Exception exc)
                {
                        log->write(exc.what());
                        log->write("\nREJECT");
                        toReturn = 1;
                }
        }
        else
        {
                log->write("Unknown proof type.\nREJECT.");
                return 1;
        }
        return toReturn;
}
