C++ Verificatum Verifier Documentation                      {#mainpage}
======================================

## Purpose ##

[Verificatum](http://www.verificatum.org) is an implementation of a
provably secure mix-net. Used for electronic vote, such a protocol has
the advantage of providing proofs that its execution went as
expected. However, parsing such proofs would be impossible for a human
being. Thus, independent pieces of software have to be written by
third parties to perform these verifications. Our program is one such
verifier and it implements the verification of the proof of shuffle
for the version 1.1.7 of Verificatum.


## Version ##

This branch contains code complying with the version 1.1.7 of the
protocol.


### Prerequisites ###

There is no pre-compiled version of this software so you have to
compile it "by hand". First of all, you are going to need
[cmake](http://www.cmake.org/), a cross-platform build system (it is
in most Linux distribution's repositories), the
[boost program options library](http://www.boost.org/doc/libs/1_54_0/doc/html/program_options.html)
which is available in the package on Ubuntu
`libboost-program-options-dev`. Finally, the [GMP](http://gmplib.org/)
library is used to compute with arbitrarily large numbers; on Ubuntu,
it is packaged as `libgmp-dev`.


## Usage ##

Once the C++ program has been compiled, run the following command:

    ./cppvv -shuffle <protInfo.xml> <proofDirectory> (-v)
    
Where both `<protInfo.xml>` and `<proofDirectory>` are path. Put the
`-v` optional parameter to activate a verbose output.

If verbosity is activated, the program will output some hopefully
useful data on the standard output (`stdout`). In the end, the value
returned by the program is

+ 0 if everything went perfectly.
+ 1 if the computations should be rejected.

## Documentation ##

This html files contain the detailed documentation of this
program. Each class and each function has its own
description. However, should you have any problem understanding
anything, feel free to contact me at *leoperrin plus the at sign plus
picarresursix plus fr*. If you decide to extend this program, you can
add your own pages by putting markdown documents in the `doc/files`
folder. To update the documentation, simply use the `doc/updateDoc.sh`
shell script (requires [doxygen](www.stack.nl/~dimitri/doxygen/) and
[graphviz](http://www.graphviz.org/), both of which are packaged for
most Linux distributions).


## Authors ##

This program was written by Léo Perrin.

## Copyright ##

MODIFY HERE

