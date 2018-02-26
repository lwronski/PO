#ifndef PO10_SHIP_HEADER
#define PO10_SHIP_HEADER

#include <istream>
#include <iostream>
#include "galaxy.h"

class Ship {
    int * road;
    int  amount;
  public:
    Galaxy::Starsystem* last;
    int index = 0;
    Galaxy *g;
    Ship();
    void setPath(std::istream& in);
    Galaxy::Starsystem* start(Galaxy* g);
    Galaxy::Starsystem* advance();
    int getStarsystem(int i);
    virtual ~Ship();
    int getamount();
};

#endif