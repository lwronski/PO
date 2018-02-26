#ifndef PO10_GALAXY_HEADER
#define PO10_GALAXY_HEADER

#include "result.h"
#include <istream>
#include <iostream>
#include <vector>

class Hero;
class Reaper;

class Galaxy {

    int target, numberstarsystem;
    std::vector<Reaper*> reaper;
    Hero* hero;

public:
    Galaxy();
    Galaxy& load(std::istream& in);
    Galaxy& add(Reaper* r);
    Galaxy& add(Hero* h);
    Result simulate();
    int getnumbersystem();

    class Starsystem {
        bool target = false;
        std::vector<int> Q;
        Galaxy * actual;
        bool visited = false;
    public:
        Starsystem& load(Galaxy* g, std::istream& in);
        Starsystem* adjacent(size_t idx);
        bool isTarget();
        void setTarget();
        bool isVisited();
        void setVisited();
        bool ifpropper(int i);
    };

    Starsystem* getSystem(size_t idx);
    ~Galaxy();
private:
    Starsystem ** T;
};


#endif
