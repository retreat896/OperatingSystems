public class Scheduler
{
   /**
    * Provides timing utilities for measuring program execution.
    * Used to track the age (elapsed time) of program operations.
    */
   private static final long startTime = System.currentTimeMillis();

   /**
    * Calculates the elapsed time since program start.
    * @return long - The number of milliseconds that have passed since initialization
    */
   protected static final long age()
   {
      return System.currentTimeMillis() - startTime;
   }
}
