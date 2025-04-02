
import java.util.*;

// Source code
public class Scheduler {
	int timer;
	ArrayList<Process> listOfProcesses;
	ArrayList<Process> readyQueue;
	ArrayList<Process> endProcesses;
	int timeQuantum;
	int contextSwitch; // small number (less than half the timeQuantum).. ex:
	// 0.1 * timeQuantum
	Process cpu;
	int counter;

	public Scheduler(ArrayList<Process> listOfProcesses, int timeQuantum) {
		this.timeQuantum = timeQuantum;
		this.listOfProcesses = listOfProcesses;
	}

	public void fcfs() {
		timer = 0;
		contextSwitch = 0;
		cpu = null;
		readyQueue = new ArrayList<>();
		endProcesses = new ArrayList<>();
		/********************************************
		 * Fill the code here (pseudo code is below)
		 * 1. Sort the processes according to arrivalTime
		 * 2. For each process
		 * set process's responseTime
		 * update timer according to the burstTime
		 * set process's completionTime
		 * add it to endProcesses
		 * 3. Update contextSwitch by 1 when the process is completed
		 ************************************************/

		// 1. Sort the processes according to arrivalTime
		Collections.sort(listOfProcesses, Comparator.comparingInt(p -> p.arrivalTime));

		// 2. For each process
		for (int i = 0; i < listOfProcesses.size(); i++) {
			Process currentProcess = listOfProcesses.get(i);

			// set process's responseTime
			currentProcess.responseTime = timer-currentProcess.arrivalTime;
			// update timer according to the burstTime
			timer += currentProcess.burstTime;
			// set process's completionTime
			currentProcess.completionTime = timer;
			// add it to endProcesses
			endProcesses.add(currentProcess);
		}

		// 3. Update contextSwitch by 1 when the process is completed
		contextSwitch = endProcesses.size();


		System.out.println("-----------------FCFS----------------");
		Utilities myUtility = new Utilities(endProcesses, contextSwitch, timer);
		myUtility.calUtilities();

	}

	public void rr() {
		timer = 0;
		contextSwitch = 0;
		cpu = null;
		readyQueue = new ArrayList<>();
		endProcesses = new ArrayList<>();

		while (!readyQueue.isEmpty() || !listOfProcesses.isEmpty() || cpu != null) {
			// add to readyQueue
			for (int i = 0; i < listOfProcesses.size(); i++) {
				if (listOfProcesses.get(i).arrivalTime == timer) {
					readyQueue.add(listOfProcesses.remove(i));
				}
			}

			// add to cpu
			if (cpu == null) {
				cpu = readyQueue.remove(0);
			}

			counter++;
			cpu.serviceTime++;
			/********************************************
			 * Fill the code here (pseudo code is below)
			 * 1. If this process is new to be allocated to cpu, set its responseTime
			 * 2. If the process is completed by comparing cpu.serviceTime == cpu.burstTime,
			 * set process's completionTime
			 * add it to endProcesses
			 * clear up cpu (cpu = null)
			 * increase contextSwitch by 1
			 * reset counter to 0
			 * 2. else if the process uses up the timeQuantum
			 * put it back to readyQueue
			 * clear cpu (cpu = null)
			 * increase contextSwitch by 1
			 * reset counter to 0
			 ************************************************/
			// 1. If this process is new to be allocated to cpu, set its responseTime
			if (cpu.serviceTime == 1) {
				cpu.responseTime = timer-cpu.arrivalTime;
			}
			// 2. If the process is completed by comparing cpu.serviceTime == cpu.burstTime,
			if (cpu.serviceTime == cpu.burstTime) {
				// set process's completionTime
				cpu.completionTime = timer;
				// add it to endProcesses
				endProcesses.add(cpu);
				// clear up cpu (cpu = null)
				cpu = null;
				// increase contextSwitch by 1
				contextSwitch++;
				// reset counter to 0
				counter = 0;
			}
			// 2. else if the process uses up the timeQuantum
			else if (counter == timeQuantum) {
				// put it back to readyQueue
				readyQueue.add(cpu);
				// clear cpu (cpu = null)
				cpu = null;
				// increase contextSwitch by 1
				contextSwitch++;
				// reset counter to 0
				counter = 0;
			}

			timer++; // real time : cpu time

		}
		System.out.println("-----------------RR------------------");
		Utilities myUtility = new Utilities(endProcesses, contextSwitch, timer);
		myUtility.calUtilities();

	}

}
