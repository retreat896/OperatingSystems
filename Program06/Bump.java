public class Bump extends Thread{
    public static void main(String[] args) {
        // 0. get user args from command line
        int totalRiders = 10;
        int totalCars = 5;
        int maxBumpTime = 5;
        int maxWanderTime = 10;
        int totalTime = 40;

        // use getOpt to get the command line args
        GetOpt go = new GetOpt(args, "Ur:c:b:w:R:");
        go.optErr = true;
        int ch = -1;
        boolean usagePrint = false;

        while ((ch = go.getopt()) != go.optEOF) {
            if ((char) ch == 'U')
                usagePrint = true;
            else if ((char) ch == 'r')
                totalRiders = go.processArg(go.optArgGet(), totalRiders);
            else if ((char) ch == 'c')
                totalCars = go.processArg(go.optArgGet(), totalCars);
            else if ((char) ch == 'b')
                maxBumpTime = go.processArg(go.optArgGet(), maxBumpTime);
            else if ((char) ch == 'w')
                maxWanderTime = go.processArg(go.optArgGet(), maxWanderTime);
            else if ((char) ch == 'R')
                totalTime = go.processArg(go.optArgGet(), totalTime); // convert to ms
            else
                System.exit(1);
        }
        if (usagePrint) {
            System.out.println("Usage: -r# -c# -b# -w# -R#");
            System.out.println("  -r# = number of rider threads (default 10)");
            System.out.println("  -c# = number of car threads (default 5)");
            System.out.println("  -b# = max bump time in ms (default 1)");
            System.out.println("  -w# = max wander time in seconds (default 10)");
            System.out.println("  -R# = total time in seconds (default 40)");
            System.out.println("  -u  = print usage");
            System.exit(0);
        }

        // 0. print out the user args
        System.out.printf("Bumper Cars: numRiders = %d, numCars = %d%n",
                totalRiders, totalCars);
        System.out.printf("timeBump = %d, timeWander = %d, runTime = %d%n",
                maxBumpTime, maxWanderTime, totalTime);

        // 1. Create coordinator object
        Coordinator coord = new Coordinator(totalRiders, totalCars, maxBumpTime);

        // 2. Create array of cars
        Car[] cars = new Car[totalCars];

        // 3. Create array of riders
        Rider[] riders = new Rider[totalRiders];

        // 4. Create the cars
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(i, coord);
            System.out.println("Car " + i + " is alive");
        }

        // 5. Create the riders
        for (int i = 0; i < riders.length; i++) {
            riders[i] = new Rider(i, coord, maxWanderTime);
            System.out.println("Rider " + i + " is alive");
        }

        // 6. Write "All Car & Rider threads started."
        System.out.println("All Car & Rider threads started.");

        // 7. nap for T-total seconds
        SmallScheduler.nap(totalTime * 1000); // convert to ms

        // 8. Write "Calling stopIts; wait 15 seconds; do mini-report"
        System.out.println("Calling stopIts; wait 15 seconds; do mini-report");

        // 9. Call the stopIt methods and wait 15 seconds.
        for (Car car : cars) {
            car.stopIt();
        }
        for (Rider rider : riders) {
            rider.stopIt();
        }
        SmallScheduler.nap(15000);

        // 10. Print mini-report
        System.out.println("Number of times a car was ridden " + coord.findTotalRider() + ".");

        // 11. System.exit( 0 );
        System.exit(0);
    }
}