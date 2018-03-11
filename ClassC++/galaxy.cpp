#include "galaxy.h"
#include "reaper.h"
#include "hero.h"

Galaxy::Starsystem& Galaxy::Starsystem::load(Galaxy *g, std::istream &in) {
    actual = g;
    int size,value;
    in >> size;
    for ( int i = 0; i < size; i++ ) {
        in >> value;
        Q.push_back(value);
    }
    return *this;
}

Galaxy::Starsystem * Galaxy::Starsystem::adjacent(size_t idx) {
        if ( idx < this->Q.size() ) return actual->T[Q[idx]];
        else return this;
}

bool Galaxy::Starsystem::isTarget() {
    return this->target;
}

Galaxy& Galaxy::load(std::istream &in) {

    in >> numberstarsystem >> target;
    T = new Starsystem*[numberstarsystem];
    for ( int i = 0; i < this->numberstarsystem; i++ ) {
        T[i] = new Starsystem();
    }
    T[target]->setTarget();
    for ( int i = 0; i < this->numberstarsystem; i++ ) {
        T[i]->load(this, in);
    }
    return *this;
}

Galaxy& Galaxy::add(Reaper *r) {
    this->reaper.push_back(r);
    return *this;
}

Galaxy& Galaxy::add(Hero *h) {
    this->hero = h;
    return *this;
}

Galaxy::Starsystem * Galaxy::getSystem(size_t idx) {
    if ( idx < numberstarsystem) return T[idx];
    else return T[0];
}

void Galaxy::Starsystem::setTarget() {
    target = true;
}

bool Galaxy::Starsystem::isVisited() {
    return this->visited;
}

int Galaxy::getnumbersystem() {
    return this->numberstarsystem;
}

void Galaxy::Starsystem::setVisited() {
    this->visited = true;
}

bool Galaxy::Starsystem::ifpropper(int i) {
    return i < this->Q.size();
}

Galaxy::Galaxy() {
    this->numberstarsystem = 0;
}

Galaxy::~Galaxy() {
    for (int i = 0; i < numberstarsystem; i++) {
        delete T[i];
    }
    delete[] T;
     for (size_t i = 0; i < reaper.size(); ++i) {
        delete reaper[i];
    }
    reaper.clear();
    delete hero;
}

Result Galaxy::simulate() {

    for ( int i = 0; i < this->reaper.size(); i++ ) {
        reaper[i]->start(this)->setVisited();
    }

    int index = hero->getStarsystem(0);
    if (index > this->getnumbersystem() ) return Result ::Invalid;// zly ruch

    hero->start(this);

    if ( getSystem(index)->isTarget() ) return Result::Success; // jestesmy na miejscu
    if ( hero->last->isVisited() ) return Result::Failure; // padlismu


    for ( int i = 1; i < hero->getamount(); i++ ) {

        for ( int j = 0; j < this->reaper.size(); j++ ) {
            if ( reaper[j]->getamount() > i ) reaper[j]->advance()->setVisited();
        }

        if (!hero->last->ifpropper(hero->getStarsystem(i))) return Result ::Invalid;// zly ruch

        hero->advance();

        if ( hero->last->isTarget()) return Result ::Success;// jestesmy na miejscu
        if ( hero->last->isVisited() ) return Result::Failure; // padlismu

    }
    return Result ::Invalid;// nie doszlismu do celu
}
