/**
 * Created by lukasz on 06.04.2017.
 */
public class RemoteListAdapter<T> {

    RemoteList<T> temp;
    T first; T second; T third;
    int counter; int counterempty;
    int T,E,M,F;

    public RemoteListAdapter(RemoteList<T> rl) {
        temp = rl;
        first = null; third = null; counter = 0; T = 0; E = 0; M = 0; F = 0; counterempty = 0;
    }

    T load(T next)  throws  MissingElement, FakeElement, EmptyList {
        try {
            next  = temp.pop();
        }
        catch( EmptyList e ) {
            if( E == 0 ) E = 1;
            else E = 2;
            next = take(next);
        }
        catch( TimeLimitExceded e) {
            next = load(next);
        }
        return next;
    }

    T take(T next) {
        try {
           next = load(next);
        }
        catch( MissingElement e ) {
            T tempT = second;
            second = first;
            first = tempT;
            next = take(next);
        }
        catch ( FakeElement e ) {
            F = 1;
        }
        catch ( EmptyList e ) {

        }
        return next;
    }

    void emptylist() throws EmptyList {
        if( E==0 ) {
            counter++;
        }
        if(E==1) {
            counterempty++;
            if( counter+2 == counterempty ) throw new EmptyList(); // jak rzuce tutaj wyjatek to zostanie on
                                            // tak jakby przchwycony przez pop ktory tez rzuci wyjatek?
        }
        if(E==2) {
            throw new EmptyList();
        }
    }

    void check() {

        if( first == null) {    // pobranie pierwszych 3 elementow
            first = take(first);
            second = take(second);
            while( F == 1) {
                F = 0; first = take(first);
               second =  take(second);
            }
            third = take(third);
            while( F == 1) {
                F = 0; second = take(second);
                third = take(third);
            }
            counter++;
        }
        else {      // jesli mam dwa piersze elementy to szukam nastepnego
            third = take(third);
            while( F == 1) {
                F = 0; second = take(second);
                third = take(third);
            }
        }
    }

    public T pop() throws EmptyList {

        if ( E == 2) {
            E = 3;
            return first;
        }
        if (counter == counterempty && E == 1) {
            counterempty++;
            return first;
        }
        emptylist();    // sprawdzam czy istnieje kolejny element, jesli nie rzucam wyjatek Emptylis
        check();
        T tempT = first;
        first = second;
        second = third;

        return tempT;

    }

    public boolean hasNext() {

        if ( E == 3 ) {
            return false;
        }
        if(E==1) {
            if( counter+1 == counterempty ) return false;
            else return true;
        }
        return true;
    }

}
