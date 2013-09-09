# Time-stamp: <2013-09-06 21:37:00 leo>

# Bash script compiling the cppvv program and generating its
# documentation.


BASE_DIR=$(pwd)

# compiling the program
mkdir $BASE_DIR/build
cd $BASE_DIR/build
cmake $BASE_DIR
make

# generating its documentation
cd $BASE_DIR/doc
doxygen doxygen.conf 1> /dev/null # doxygen is very talkative

echo "Code compiled and documentation generated!"
