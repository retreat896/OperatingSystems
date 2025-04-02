import java.util.Date;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.LinkedList;

public class Factory {

    public static void main(String args[]) {
        // create the message queue
        // Channel<Date> queue = new MessageQueue<Date>();
        // ArrayList<Date> queue = new ArrayList<Date>();
        BoundedBuffer buffer = new BoundedBuffer();

        // create the producer and consumer threads and
        // pass each thread a reference to the MessageQueue object.
        Thread producer = new Thread(new Producer(buffer));
        Thread consumer = new Thread(new Consumer(buffer));

        // start the threads
        producer.start();
        consumer.start();
    }
}