import java.util.Date;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Consumer implements Runnable {
    /*
     * private ArrayList<Date> queue;
     * 
     * public Consumer(ArrayList<Date> queue){
     * this.queue = queue;
     * }
     */
    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Date message = null;

        while (true) {
            // nap for awhile
            SleepUtilities.nap();

            // consume an item from the buffer
            try {
                // message = queue.remove(0);
                message = (Date) buffer.remove();
            } catch (Exception e) {

            }

            if (message != null)
                System.out.println("Consumer consumed " + message);
        }
    }
}