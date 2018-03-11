import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by lukasz on 15.04.2017.
 */
public class Switch {
    public static <T> Map<Integer, T> toMap(List<T> list) {

        return new AbstractMap<Integer, T>() {

            @Override
            public Set<Entry<Integer, T>> entrySet() {
               return new AbstractSet<Entry<Integer, T>>() {
                   @Override
                   public Iterator<Entry<Integer, T>> iterator() {
                     return new Iterator<Entry<Integer, T>>() {
                         int size = 0;
                         @Override
                         public boolean hasNext() {
                             return size < list.size();
                         }

                         @Override
                         public Entry<Integer, T> next() {
                             int i = size;
                             size++;
                             return new SimpleImmutableEntry<Integer, T>(i,list.get(i));
                         }
                     };
                   }
                   @Override
                   public int size() {
                       return list.size();
                   }
               };
            }
        };

    }
    public static <T> List<T> toList(Map<Integer, T> map) {

        return new AbstractList<T>() {
            @Override
            public T get(int index) throws MapIsNotListException {
                int counter = 0;
                while(counter < map.size()) {
                    if ( map.get(counter) == null ) throw new MapIsNotListException();
                    counter++;
                }
                return map.get(index);
            }

            @Override
            public int size() {
                return map.size();
            }
        };
    }
}