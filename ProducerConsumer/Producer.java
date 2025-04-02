import java.util.Date;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.LinkedList;

public class Producer implements Runnable {
    // private Channel queue;
    /*
     * private ArrayList<Date> queue;
     * 
     * public Producer(ArrayList<Date> queue){
     * this.queue = queue;
     * }
     */
    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Date message;

        while (true) {
            // nap for awhile
            SleepUtilities.nap();

            // produce an item and enter it into the buffer
            message = new Date();
            System.out.println("Producer produced " + message);
            // queue.add(message);
            buffer.insert(message);
        }
    }
}