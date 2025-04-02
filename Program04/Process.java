

public class Process {
	public int pid, arrivalTime, serviceTime, burstTime, completionTime, responseTime;

	public Process(int pid, int arrivalTime, int burstTime) {
		this.pid = pid; // unique
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.serviceTime = 0;
	}

}
