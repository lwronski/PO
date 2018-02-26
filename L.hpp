#ifndef RATIONAL_H
#define RATIONAL_H

#include <iostream>

template <typename Number> class Rational{
    Number numerator;
    Number denominator;
public:
    Rational() :numerator(Number(0)), denominator(Number(1)){}
    Rational( Number x ) :numerator(x), denominator(Number(1)) {}
    Rational( Number x, Number y) :numerator(x), denominator(y) {
        reduce();
    }

    Rational operator+(Rational const & obj) const {
        Rational temp;
        temp.numerator = numerator*obj.denominator;
        temp.numerator += obj.numerator*denominator;
        temp.denominator = denominator*obj.denominator;
        temp.reduce();
        return temp;
    }

    Rational& operator+=(Rational const & obj) {
        Rational copy(obj);
        numerator = numerator*copy.denominator;
        numerator += copy.numerator*denominator;
        denominator = denominator*copy.denominator;
        reduce();
        return *this;
    }

    Rational operator+() const {
        Rational copy(*this);
        return copy;
    }

    Rational operator-(Rational const & obj) const {
        Rational temp;
        temp.numerator = numerator*obj.denominator;
        temp.numerator -= obj.numerator*denominator;
        temp.denominator = denominator*obj.denominator;
        temp.reduce();
        return temp;
    }

    Rational& operator-=(Rational const & obj) {
        Rational copy(obj);
        numerator = numerator*copy.denominator;
        numerator -= copy.numerator*denominator;
        denominator = denominator*copy.denominator;
        reduce();
        return *this;
    }

    Rational operator-() const {
        Rational copy(*this);
        copy.numerator *= Number(-1);
        return copy;
    }

    Rational operator*(Rational const & obj) const {
        Rational temp;
        temp.numerator = numerator*obj.numerator;
        temp.denominator = denominator*obj.denominator;
        temp.reduce();
        return temp;
    }

    Rational& operator*=(Rational const & obj) {
        Rational copy(obj);
        numerator = numerator*copy.numerator;
        denominator = denominator*copy.denominator;
        reduce();
        return *this;
    }

    Rational operator/(Rational const & obj) const {
        Rational temp;
        temp.numerator = numerator*obj.denominator;
        temp.denominator = denominator*obj.numerator;
        temp.reduce();
        return temp;
    }

    Rational& operator/=(Rational const & obj) {
        Rational copy(obj);
        numerator = numerator*copy.denominator;
        denominator = denominator*copy.numerator;
        reduce();
        return *this;
    }

    bool operator <(Rational const & obj)  const {
        if ( numerator < Number(0) && obj.numerator > Number(0)) {
            return true;
        }
        if ( numerator > Number(0) && obj.numerator < Number(0)){
            return false;
        }
        if ( numerator == obj.numerator ) {
            if ( numerator < Number(0) ) {
                return denominator < obj.denominator;
            }
            else return denominator > obj.denominator;
        }
        if ( denominator == obj.denominator ) {
            return numerator < obj.numerator;
        }
        return numerator*obj.denominator < denominator*obj.numerator;
    }

    bool operator > (Rational const & obj) const  {
        return obj.operator<(*this);
    }

    bool operator ==(Rational const & obj) const {
        return (!this->operator<(obj) && !this->operator>(obj));
    }

    bool operator !=(Rational const & obj) const {
        return !this->operator==(obj);
    }

    bool operator <=(Rational const & obj) const {
        return (this->operator==(obj) || this->operator<(obj));
    }

    bool operator >=(Rational const & obj) const {
        return (this->operator==(obj) || this->operator>(obj));
    }

    Rational operator--(int){
        Rational copy(*this);
        numerator -= denominator;
        reduce();
        return copy;
    }

    Rational operator++(int) {
        Rational copy(*this);
        numerator += denominator;
        reduce();
        return copy;
    }

    friend Rational<Number>& operator++(Rational<Number>& r){
        r.numerator += r.denominator;
        r.reduce();
        return r;
    }

    friend Rational<Number>& operator--(Rational<Number>& r){
        r.numerator -= r.denominator;
        r.reduce();
        return r;
    }


    friend const Rational<Number> operator++(Rational<Number> const& r){
        Rational copy(r);
        copy.numerator += copy.denominator;
        copy.reduce();
        return copy;
    }

    friend const Rational<Number> operator--(Rational<Number> const& r){
        Rational copy(r);
        copy.numerator -= copy.denominator;
        copy.reduce();
        return copy;
    }

    friend const Rational<Number>& operator++(Rational<Number> const& r,int){
        return r;
    }

    friend const Rational<Number>& operator--(Rational<Number> const& r,int){
        return r;
    }

    Rational& operator=(const Rational& obj) {
        this->numerator = obj.numerator;
        this->denominator = obj.denominator;
        if( denominator < Number(0) ){
            numerator *= Number(-1);
            denominator *= Number(-1);
        }
        return *this;
    }

    friend std::ostream& operator<<(std::ostream& os, const Rational& obj)
    {
        os << obj.numerator << "/" << obj.denominator;
        return os;
    }

private:

    Number nwd( Number x, Number y) {
        Number d = Number(0);
        if( x < Number(0)) x *= Number(-1);
        while( y != Number(0) ) {
            d = x % y;
            x = y;
            y = d;
        }
        return x;
    }

    void reduce(){
        if( denominator < Number(0) ){
            numerator *= Number(-1);
            denominator *= Number(-1);
        }
        Number i = nwd(numerator,denominator);
        numerator /= i;
        denominator /= i;
    }
};


template <typename Number>
Rational<Number> operator++(Rational<Number>&& r) {
    return r;
}

template <typename Number>
Rational<Number> operator--(Rational<Number>&& r) {
    return r;
}


#endif