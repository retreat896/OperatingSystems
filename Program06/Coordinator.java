class Coordinator extends Thread {
    private boolean[] rideBegin, rideOver;
    private int car_riders;
    private boolean finish_loading;
    private int bump;
    private int[] buffer;
    private int in, out;
    private int count;
    private int size;
    private int totalUnloaded;
    

    public Coordinator(int riders, int cars, int bumptime) {
        // - initialize most data to zero
        this.car_riders = riders;
        this.bump = bumptime * 1000; // convert to ms

        // - create buffer as an array of ints with size == cars
        buffer = new int[cars];
        size = cars;

        // - create bool arrays, rideBegin and rideOver size == riders
        rideBegin = new boolean[riders];
        rideOver = new boolean[riders];

        // - initialize bool arrays to all false.
        for (int i = 0; i < riders; i++) {
            rideBegin[i] = false;
            rideOver[i] = false;
        }
    }

    /* the following are public synchronized methods */
    public synchronized void getInLine(int rid) {
        // - put rid into the buffer
        enter(rid);

        // - set rideBegin[rid] to false
        rideBegin[rid] = false;

        // - wait while rideBegin[rid] is false
        while (!rideBegin[rid]) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized int load(int car_id) {
        // - Get curRider from the buffer
        int curRider = remove();

        // - set rideBegin[curRider] to true
        rideBegin[curRider] = true;

        // - signal
        notifyAll();

        // - set finish_loading to false
        finish_loading = false;

        // - wait while finish_loadinwdg is false
        while (!finish_loading) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // - return curRider
        return curRider;

    }

    public synchronized void takeAseat() {
        // - set finish_loading to true
        finish_loading = true;

        // - signal
        notifyAll();

        // - increment car_riders
        car_riders++;
    }

    public synchronized void takeAride(int rid) {
        // - set rideOver[rid] to false
        rideOver[rid] = false;

        // - wait while rideOver[rid] is false
        while (!rideOver[rid]) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public synchronized void unload(int rid) {
        // - set rideOver[rid] to true
        rideOver[rid] = true;

        // - signal
        notifyAll();

        // - increment totalUnloaded
        totalUnloaded++;

    }

    /*
     * the following are public helper methods that are
     * NOT synchronized
     */
    int getBumpTime() {
        // -- returns amount of time a car bumps
        return (int)(Math.random() * bump);
    }

    int findTotalRider() {
    
        return car_riders - rideBegin.length;
    }








    // BBuffer methods
    // /** Creates a new instance of BBuffer 
    //   * @param sizeOfBuffer represents the size of the buffer.
    //   */
    //   private BBuffer( int sizeOfBuffer  ) 
    //   {
    //      buffer = new int[ sizeOfBuffer ];
    //      count = 0  ;
    //      in = 0;
    //      out = 0;
    //      size = sizeOfBuffer;
    //   }

    /**
     * Producer method.
     * When the buffer(count) is full, it waits; then it deposits an id in
     * the buffer and notifies if the count is at exactly one.
     * 
     * @param id - identifier of rider.
     */
    private synchronized void enter(int id) {
        while (count == size) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println(" In enter" + e);
            }
        }
        buffer[in] = id;
        in = (in + 1) % size;
        count++;
        notifyAll();
    }

    /*
     * Consumer method
     * When the buffer is empty, it waits; then id removes an id from
     * the buffer and notifies if the count is at exactly size - 1.
     * 
     * @return the next element in the buffer.
     */
    private synchronized int remove() {
        int tempId;
        while (count == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println(" In getInLine" + e);
            }
        }
        tempId = buffer[out];
        out = (out + 1) % size;
        count--;
        notifyAll();
        return tempId;
    }

}
