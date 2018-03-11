import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by lukasz on 23.04.2017.
 */
public class ArrayListSqrCloned<E>{

    ArrayList<ArrayList<E>> list;

    public ArrayListSqrCloned() {
        list = new ArrayList<>();
    }

    public ArrayListSqrCloned( Collection<? extends ArrayList<? extends E>>  listedit) {
        this();
        if(listedit != null) {
        for ( ArrayList<? extends E> temp: listedit) {
            this.add(temp);
        }
        }
    }

    public boolean add(ArrayList<? extends E> element){
        return list.add(new ArrayList<E>(element));
    }

    public void add(int index, ArrayList<? extends E> element){
        list.add(index,new ArrayList<E>(element));
    }

    public boolean addAll(Collection<? extends ArrayList<? extends E>> listedit) {
        if ( listedit == null || listedit.size() == 0 ) return false;
        for ( ArrayList<? extends E> temp: listedit) {
            this.add(temp);
        }
        return true;
    }

    public boolean addAll(int index,  Collection<? extends ArrayList<? extends E>> listedit) {
        if ( listedit == null || listedit.size() == 0 || index >= listedit.size()) return false;
        int counter = 0;
        for ( ArrayList<? extends E> temp: listedit) {
            if( index <= counter) this.add(temp);
            counter++;
        }
        return true;
    }

    public ArrayList<E> set(int index, ArrayList<? extends E> listedit ){
        ArrayList<E> temp = this.list.get(index);
        this.list.set(index, new ArrayList<E>(listedit));
        return temp;
    }

    public void clear() {
        this.list.clear();
    }

    public boolean contains(Object element) {
        return this.list.contains(element);
    }

    public boolean containsAll( Collection<?> listedit){
        return this.list.containsAll(listedit);
    }

    public ArrayList<E> get( int index) {
        return this.list.get(index);
    }

    public int indexOf(Object listedit) {
        return this.list.indexOf(listedit);
    }

    public int lastIndexOf(Object listedit) {
        return this.list.lastIndexOf(listedit);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Iterator iterator() {
        return this.list.iterator();
    }

    public ListIterator listIterator() {
        return this.list.listIterator();
    }

    public ListIterator listIterator( int index) {
        return this.list.listIterator(index);
    }

    public ArrayList<E> remove( int index) {
        return this.list.remove(index);
    }

    public void remove( Object list) {
        this.list.remove(list);
    }

    public boolean removeAll( Collection<?> listedit) {
        return this.list.removeAll(listedit);
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    public boolean equals(Object listedit) {
        if ( !( listedit instanceof ArrayListSqrCloned)) return false;
        ArrayListSqrCloned temp = (ArrayListSqrCloned) listedit;
        return this.list.equals(temp.list);
    }

    public int size() {
        return list.size();
    }
}