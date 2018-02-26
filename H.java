import java.io.Reader;
import java.util.*;


public class Relay {
    Map<Thread,Integer> reference = new HashMap<>();
    boolean start = false;
    Thread currentThread = null;
    Scanner in;
    Integer id = null;

    public Relay(Reader reader) {
        in = new Scanner(reader);
    }

    public void register(int id, Thread competitor) {
        reference.put(competitor,id);
    }

    public void startRelayRace() {
        synchronized (this) {
            start = true;
            try {
                notifyAll();
            } catch (Exception e) {
            }
        }
    }

    boolean flag = true;

    synchronized public boolean dispatch() {

        while(!start && flag) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        if(Thread.currentThread().equals(currentThread) && flag){
            currentThread = null;
            id = null;
            if(!in.hasNext()) {
                flag = false;
            }
            notifyAll();
        }

        while (currentThread!=null && flag) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        if( id == null && flag ) {
            id = in.nextInt();
        }

        while(!reference.get(Thread.currentThread()).equals(id) && flag){
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        currentThread = Thread.currentThread();
        return flag;
    }
}