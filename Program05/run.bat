REM clean up class files
del *.class

REM build the Race.class file
javac race.java

REM run the Race.class file with many arguments
java RaceManyThreads -M 10 -N 2
java RaceManyThreads -M 10 -N 3
java RaceManyThreads -M 20 -N 4
java RaceManyThreads -M 20 -N 5
java RaceManyThreads -M 50 -N 5
