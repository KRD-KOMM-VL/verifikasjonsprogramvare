CMAKE_MINIMUM_REQUIRED(VERSION 2.6)


PROJECT(${LIB_UTILS_NAME})

# the main library
ADD_LIBRARY(${PROJECT_NAME}
  utils.cpp
  bytetree/bytetree.cpp
  bytetree/leaf.cpp
  bytetree/node.cpp
  )



