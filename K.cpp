#include <memory>
using namespace std;

class DataPair{
private:
    unique_ptr<Data> first_, second_;

public:

    const Data  & first() const noexcept {
        return *first_;
    }

    const Data  & second() const noexcept {
        return *second_;
    }

    DataPair( const Data  & a, const Data  & b ) noexcept(is_nothrow_copy_constructible<Data>::value) {
        first_ = unique_ptr<Data>(new Data(a));
        second_ = unique_ptr<Data>(new Data(b));
    }

    DataPair( const Data  & a, Data && b )  noexcept(is_nothrow_move_constructible<Data>::value && is_nothrow_copy_constructible<Data>::value) {
        first_ = unique_ptr<Data>(new Data(a));
        second_ = unique_ptr<Data>(new Data(move(b)));
    }

    DataPair( Data && a, const Data  & b )  noexcept(is_nothrow_move_constructible<Data>::value && is_nothrow_copy_constructible<Data>::value) {
        second_ = unique_ptr<Data>(new Data(b));
        first_ = unique_ptr<Data>(new Data(move(a)));
    }

    DataPair(Data && a, Data && b  ) noexcept(is_nothrow_move_constructible<Data>::value || is_nothrow_copy_constructible<Data>::value) {
        if( is_nothrow_move_constructible<Data>::value ){
            first_ = unique_ptr<Data>(new Data(move(a)));
            second_ = unique_ptr<Data>(new Data(move(b)));
        }
        else {
            first_ = unique_ptr<Data>(new Data(a));
            second_ = unique_ptr<Data>(new Data(b));
        }
    }


    void swapunique_ptr(DataPair & a) {
        swap(first_, a.first_);
        swap(second_, a.second_);
    }

    DataPair(const DataPair  & a) noexcept ( is_nothrow_copy_constructible<Data>::value) {
        DataPair b(*a.first_, *a.second_);
        swapunique_ptr(b);
    }

    DataPair(DataPair && a) noexcept {
        first_ = move(a.first_);
        second_ = move(a.second_);
    }

    DataPair& operator = ( const DataPair   & a ) noexcept ( is_nothrow_copy_constructible<Data>::value)  {
        first_ = unique_ptr<Data>(new Data(*a.first_));
        second_ = unique_ptr<Data>(new Data(*a.second_));
        return *this;
    }
    DataPair& operator = ( DataPair && a )  noexcept {
        first_ = move(a.first_);
        second_ = move(a.second_);
        return *this;
    }
};