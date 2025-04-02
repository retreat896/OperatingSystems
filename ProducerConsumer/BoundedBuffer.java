import java.util.concurrent.*;

public class BoundedBuffer {

    private static final int BUFFER_SIZE = 5;
    private Semaphore mutex, empty, full;
    private int in, out;
    private Object[] buffer;

    public BoundedBuffer() {
        // buffer is initially empty
        in = 0;
        out = 0;
        buffer = new Object[BUFFER_SIZE]; // Shared buffer can store five objects.
        mutex = new Semaphore(1); // mutex allows only one thread to enter
        empty = new Semaphore(BUFFER_SIZE);// empty blocks producer while empty=0
        full = new Semaphore(0); // full blocks consumer while full=0
    }
    // public void insert( ) { /* see next slides */ }
    // public Object remove( ) { /* see next slides */ }

    public void insert(Object item) {
        try {
            empty.acquire();
            mutex.acquire();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        // add an item to the buffer, this is CS
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;
        mutex.release();
        full.release();

    }

    public Object remove() {
        try {
            full.acquire();
            mutex.acquire();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        // remove an item from the buffer, this is CS
        Object item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;
        mutex.release();
        empty.release();
        return item;
    }

}
