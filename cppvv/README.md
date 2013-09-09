This program is a stand alone verifier for the verificatum program
(which provides a secure mix-net, see www.verificatum.org). 


Purpose
=======

[Verificatum](http://www.verificatum.org) is an implementation of a
provably secure mix-net. Used for electronic vote, such a protocol has
the advantage of providing proofs that its execution went as
expected. However, parsing such proofs would be impossible for a human
being. Thus, independent pieces of software have to be written by
third parties to perform these verifications. Our program is one such
verifier and it implements the verification of the proof of shuffle
for the version 1.1.7 of Verificatum.


Installation
============

There is no pre-compiled version of this software so you have to
compile it "by hand". First of all, you are going to need:
+ [Cmake](http://www.cmake.org/), a cross-platform build system 
+ The [boost program options library](http://www.boost.org/doc/libs/1_54_0/doc/html/program_options.html)
+ The [GMP](http://gmplib.org/) library, used to compute with
  arbitrarily large numbers.
+ [Doxygen](http://www.stack.nl/~dimitri/doxygen/), a tool to generate
  documentation from comments in the source code.
+ [Graphviz](http://www.graphviz.org/) to generate graphs (class
  diagrams and the likes) for the documentation.

On ubuntu, running the following command will install all these at
once.

    sudo apt-get install cmake libboost-program-options-dev libgmp-dev doxygen graphviz


Once these are installed, run the `install.sh` bash script. It will
create folder called `build` and, in it, a file called `cppvv`: it is
the C++ Verificatum Verifier. It will also generate an html
documentation: to read it, open the file `doc/html/index.html`.

To check that the `crypto` and the `arithm` modules work as expected,
you wan run the `./test.sh` bash script in the `tests` folder. It
outputs `[OK]` when a test is successfull and `[FAILED]` otherwise.


Usage
=====

Once the C++ program has been compiled, you can run it with the
following command:

    /path/to/cppvv -shuffle <protInfo.xml> <proofDirectory> (-v)
    
Where both `<protInfo.xml>` and `<proofDirectory>` are path. Put the
`-v` optional parameter to activate a verbose output.

If verbosity is activated, the program will output some hopefully
useful data on the standard output (`stdout`). If `-v` is not present,
**nothing is output**, the only information returned is the exit
code. In the end, the value returned by the program is

+ 0 if everything went perfectly.
+ 1 if the computations should be rejected.


Documentation
=============

Each class and each function has its own doxygen description. If you
decide to extend this program, you can add your own pages to the
documentation by putting markdown documents in the `doc/files`
folder. To update the documentation, simply run `doxygen doxygen.conf`
in the `./doc/` folder. Remember that you need
[doxygen](www.stack.nl/~dimitri/doxygen/) and
[graphviz](http://www.graphviz.org/), both of which are packaged for
most Linux distributions.


Code overview
=============

Description
===========

The cpp-verifier is made of several more or less independent
modules. The dependencies between them are specified using the symbol
`A < B` which means here "B depends on A". All the code is in a
namespace called `cppVerifier`


utils
-----

Contains the bytetree implementation and basic exception handling as
well as functions to turn numbers to formatted strings (and
vice-versa).

io
---

    utils < io

Contains a parser for the configuration file relying on tinyxml as
well utils to easily write logs. It also contains a class serving as a
common interface for all high level algorithms to retrieve the
arguments of the current session.

crypto
------

    utils < crypto

Contains implementation of SHA-2 hashfunctions (SHA-256, 384 and 512),
of a pseudo-random generator and of a random oracle.

arithm
------

    crypto < arithm
    utils < arithm

By far the most complex part. It contains classes providing
implementations of many different groups: product groups, modular
fields, multiplicative subgroups, etc. A UML diagram describing these
and their relations is in the `doc/uml`.

proof
------

    crypto < proofs
    io < proofs
    utils < proofs
    arithm < proofs

Contains the different proof that are part of the core verification
algorithm. To stick as close to the specification as possible, there
is one function per algorithm in it, the variables have the same name
and the code has the same structure (comments are used to highlight
which part of the specification is dealt with in every steps of the
functions).


main.cpp
--------

The main function is in a file of its own. It mostly does the parsing
of the CLI args and calls the different high level algorithms
accordingly.

Copyright
=========

MODIFY HERE

The library [tinyxml](http://www.grinninglizard.com/tinyxml/), released
under a zlib licence, is used when parsing the configuration file.
