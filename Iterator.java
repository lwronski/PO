import java.util.Iterator;

/**
 * Created by lukasz on 26.03.2017.
 */
public class a implements Iterable<a> {
    int sort = 0;
    final a a;
    node first;

    @Override
    public Iterator<a> iterator() {
        return new Iterator() {

            int counter = 0;
            node next = a.first;

            @Override
            public boolean hasNext() {
                if ( counter != 0 && next==a.first) {
                    return false;
                }
                else return true;
            }

            @Override
            public a next() {
                counter++;
                next = next.next;
                return new a() {
                    public java.lang.String toString() {

                        return Integer.toString(this.first.that);
                    }
                }.a(next.previous.that);
            }
        };
    }

    private class node {
        int that;
        node next;
        node previous;

        private node(int that, node next, node previous) {
            this.that = that;
            this.next = next;
            this.previous = previous;
        }
    }

    public a(a other) {
        a = other;
        a.sort += 1;
        first = null;
    }

    public a() {
        a = new a(this);
    }

    private void add (int i, node edit) {
        node temp = new node(i,null,null);

        if( this.first == null ) {
            temp.next = temp;
            temp.previous = temp;
            this.first = temp;
        }
        else {
            edit.previous.next = temp;
            temp.previous = edit.previous;
            temp.next = edit;
            edit.previous = temp;
        }
    }

    private node find ( int i ) {
        node edit = first;
        if( first == null ) {
            return null;
        }
        if( i <= first.that ) {
            return edit;
        }
        do {
            edit = edit.next;
        } while(edit != first && i > edit.that );
        return edit;
    }

    private void addsort ( int i) {
        node find = find(i);

        if( first == null ) {
            add(i,null);
        }
        else {
            add(i,find);
            if( find == this.first && i <= this.first.that ) this.first = this.first.previous;
        }
    }

    private int remove( node edit ) {

        if ( this.first.next == first  ) {
           first = null;
        }
        else {
            edit.previous.next = edit.next;
            edit.next.previous = edit.previous;
        }
        return edit.that;
    }

    public a a( Integer i ) {

        if (this.sort == 0) {
            add(i, this.first);
            first = first.previous;
            a.addsort(i);
            return this.a;

        } else {
            addsort(i);
               a.add(i, a.first);
            a.first = a.first.previous;
            return this.a;
        }
    }

    public a a( int i ) {
        if (this.sort == 0) {
            add(i, this.first);
            a.addsort(i);
            return this.a;
        } else {
            addsort(i);
            a.add(i, a.first);
            return this;
        }
    }

    public Integer  a() {  // remove
        node find;
        int returned;
        if ( this.sort == 0) {  // nieposortowana
            find = a.find(first.previous.that);
            a.remove(find);
            if ( a.first == find ) {
                a.first = a.first.next;
            }
            returned = first.previous.that;
            remove(first.previous);
            return returned;
        }
        else {
            returned = a.first.previous.that;
            find = find(returned);
            a.remove(a.first.previous);
            remove(find);
            if ( find == first ) first = first.next;
            return returned;
        }
    }

    public java.lang.String toString() {
        String out = "[";
        node edit = this.first;

        if( this.first!=null ) {
            out += first.that;
            edit = edit.next;
            while ( edit != this.first ) {
                out += ", " + edit.that;
                edit = edit.next;
            }
        }
        out += "]";
        return out;
    }
}