class Car implements Runnable {
    private Coordinator coord = null;
    private int cid = -1; // Car id #
    private boolean stop;

    public Car(int id, Coordinator c) {
        // ask if you don't know.
        this.cid = id;
        this.coord = c;
        this.stop = false;
        new Thread(this).start();
    }

    public void run() {
        while (!stop) {
            // 0. Write out the Car + id "is in line to be loaded."
            System.out.println("age() = "+SmallScheduler.age()+", Car " + cid + " is in line to be loaded.");
    
            // 1. rid = coord.load(. . .) - car waits until loaded'
            int rid = coord.load(cid);

            // 2. get bumpingTime
            int bump = coord.getBumpTime(); // in ms


            // 3. bump (nap for bumpingTime) - bump on the floor
            System.out.println("age() = "+SmallScheduler.age()+", Car " + cid + " with Rider "+ rid + " is bumping on the floor for " + bump + " ms");
            SmallScheduler.nap(bump);


            // 4. actually take a second to unload : nap 1000
            SmallScheduler.nap(1000);

            // 5. coord.unload(. . .) - car waits until loaded
            coord.unload(rid);

            // 6. write out Car + id + " has unloaded." (see sample output)
            System.out.println("age() = "+SmallScheduler.age()+", Car " + cid + " has unloaded.");
        }
    }

    public void stopIt() {
        this.stop = true;
    }
}