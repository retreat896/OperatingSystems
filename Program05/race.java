/*---------------------------------------------------------------------
Name: Kristopher Adams
Course: CS 3230, Section 1, Spring 2025
Purpose: This program demonstrates a simple multi-threaded environment 
where multiple "Racers" perform computations. The program ensures mutual 
exclusion using the "Arbitrator" class to manage access to a shared resource.

Input: Command line arguments -M (number of computations) and -N (number of racers).
Output: Displays the progress and results of each Racer thread.
---------------------------------------------------------------------*/
class Racer extends Thread {
   /**
    * Represents a racing thread that performs computations in a controlled manner.
    * This class handles mutual exclusion using an Arbitrator to safely access shared resources.
    */

   private String name;
   private int M = 0;              
   private static volatile long sum = 0;  
   private int id;
   private Arbitrator arbitrator;

   /**
    * Constructor for creating a new Racer thread.
    * @param name String - The name identifier for this racer thread
    * @param M int - The number of computations this racer will perform
    * @param id int - Unique identifier for this racer, used for arbitration
    * @param arbitrator Arbitrator - The arbitrator instance managing mutual exclusion
    */
   public Racer(String name, int M, int id, Arbitrator arbitrator) {
      this.name = name;
      this.M = M;
      this.id = id;
this.arbitrator = arbitrator;
      this.arbitrator = arbitrator;
      System.out.println("age()=" + Scheduler.age() + ", "
         + name + " is alive, M=" + M);
   }

   /**
    * Performs a mathematical computation on the given values.
    * @param j long - The initial value to start computations from
    * @param k int - The number of iterations to perform calculations
    * @return long - The computed sum after k iterations
    */
   private long fn(long j, int k) {
      long total = j;
      for (int i = 1;  i <= k; i++) total += (2 * i - 1) * (2 * i - 1);
      return total;
   }

   /**
    * Main execution method for the racer thread.
    * Performs M iterations of computations while ensuring mutual exclusion
    * through the arbitrator.
    */
   @Override
   public void run() {
      
      System.out.println("\n");
	  System.out.println("age()=" + Scheduler.age() + ", "
         + name + " is running ");
	  System.out.println("Name:" 
		 + Thread.currentThread().getName());
	  System.out.println("ID:" 
		 + Thread.currentThread().getId());
       
      
      for (int m = 1; m <= M; m++)
      {
         arbitrator.wantToEnterCS(id);
         sum = fn(sum, m);      
         arbitrator.finishedInCS(id);

      }
      
      System.out.println("age()=" + Scheduler.age() + ", "
         + name + " is done, sum = " + sum);
	  System.out.println("\n");
     
     
   }
}

class RaceManyThreads {
   /**
    * Main class that manages multiple racing threads.
    * Handles command line arguments and coordinates the execution of racer threads.
    */

   private static int M = 100;
   private static int numRacers = 1;
   private static Arbitrator arbitrator;

   /**
    * Main entry point for the program.
    * Processes command line arguments and creates/manages racer threads.
    * @param args String[] - Command line arguments:
    *                       -M: number of computations per racer
    *                       -N: number of racer threads
    *                       -U: display usage information
    */
   public static void main(String[] args) {

      GetOpt go = new GetOpt(args, "UtN:M:");
      go.optErr = true;
      String usage = "Usage: -t -M m -N numracers";
      int ch = -1;
      //boolean timeSlicingEnsured = false;
      while ((ch = go.getopt()) != go.optEOF) {
         if      ((char)ch == 'U') {
            System.out.println(usage);  System.exit(0);
         }
         else if ((char)ch == 'M')
            M = go.processArg(go.optArgGet(), M);
         else if ((char)ch == 'N')
            numRacers = go.processArg(go.optArgGet(), numRacers);
         else {
            System.err.println(usage);  System.exit(1);
         }
      }
      System.out.println("RaceManyThreads: M=" + M + ", N=" + numRacers 
      //+ ", timeSlicingEnsured=" + timeSlicingEnsured
      );

      // Racer racerObject = new Racer("RacerObject", M);
      arbitrator = new Arbitrator(numRacers);

      Racer[] racer = new Racer[numRacers];
      // Thread[] racer = new Thread[numRacers];
      for (int i = 0; i < numRacers; i++)
         // racer[i] = new Thread(racerObject, "RacerThread" + i);
         racer[i] = new Racer("RacerThread" + i, M, i, arbitrator);
      for (int i = 0; i < numRacers; i++) {
        // racer[i].start();
        racer[i].start();
      }
      System.out.println("age()=" + Scheduler.age() + 
                       ", all Racer threads started");

      try {
            for (int i = 0; i < numRacers; i++) racer[i].join();
         } catch (InterruptedException e) {
            System.err.println("interrupted out of join");
         }

      System.out.println("RaceManyThreads done");
      System.exit(0);
   }
}
