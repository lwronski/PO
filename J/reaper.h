//
// Created by lukasz on 28.05.17.
//
#include "ship.h"
#include "galaxy.h"
#ifndef J_REAPER_H
#define J_REAPER_H

class Reaper : public Ship {
public:
    Galaxy::Starsystem* last;
};

#endif //J_REAPER_H
