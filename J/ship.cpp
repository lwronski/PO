#include "ship.h"

void Ship::setPath(std::istream &in) {
    in >> amount;
    road = new int[amount];
    for ( int i = 0; i < amount; i++) {
        in >> road[i];
    }
}

Galaxy::Starsystem* Ship::start(Galaxy* g){
    this->g = g;
    this->index = 1;
    this->last = g->getSystem(road[0]);
    return last;
}

Galaxy::Starsystem* Ship::advance() {
    if ( index < getamount()) {
        last = last->adjacent(road[index++]);
        return last;
    }
    else return nullptr;
}

Ship::~Ship() {
    delete [] road;
}

int Ship::getStarsystem(int i) {
    return road[i];
}

int Ship::getamount() {
    return this->amount;
}

Ship::Ship() {

}