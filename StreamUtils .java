import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Created by lukasz on 26.04.2017.
 */

public class StreamUtils <E> {
    public static<E> Stream<E> generateRest(Stream<? extends E> parametrs, BiFunction<? super E, ? super E,? extends E> function){
        LinkedList<E> list = new LinkedList<>();
        list.add(null); list.add(null);
        return Stream.concat(parametrs.peek(e -> { list.add(e); list.removeFirst();} ),
                Stream.generate( () -> function.apply(list.getFirst(),list.getLast()) ).peek(e -> { list.add(e); list.removeFirst();}));
    }
}