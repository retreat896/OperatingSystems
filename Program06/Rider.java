class Rider implements Runnable {
    private Coordinator coord = null;
    private int rid = -1; // Rider id #
    private int maxWanderTime = -1;
    private boolean stop;

    public Rider(int id, Coordinator c, int maxWanderTime) // Ask if you don't know.
    {
        this.rid = id;
        this.coord = c;
        this.maxWanderTime = maxWanderTime * 1000; // convert to ms
        this.stop = false;

        new Thread(this).start();
    }

    public void run() {
        while (!stop) {
            // 0. Generate random wander - time & write out wandering message (see sample output)
            int wanderTime = (int) (Math.random() * maxWanderTime);
            System.out.println("age() = "+SmallScheduler.age()+", Rider " + rid + " is wandering the park for " + wanderTime + " ms");

            // 1. nap - wander around the park
            SmallScheduler.nap(wanderTime);

            // 2. write out that Rider is joining the line. (see sample output)
            System.out.println("age() = "+SmallScheduler.age()+", Rider " + rid + " is joining the line.");

            // 3. coord.getInLine(. . .)
            coord.getInLine(rid);

            // 4. actually take a second to take a seat : nap 1000
            SmallScheduler.nap(1000);

            // 5. coord.takeAseat(. . .)
            coord.takeAseat();

            // 6. coord.takeAride(. . .)
            coord.takeAride(rid);

            // 7. write out that rider has finished riding. (see sample output)
            System.out.println("age() = "+SmallScheduler.age()+", Rider " + rid + " has finished riding.");
        }
    }

    public void stopIt() {
        this.stop = true;
    }
}
