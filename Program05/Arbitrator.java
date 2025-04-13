/*---------------------------------------------------------------------
Name: Kristopher Adams
Course: CS 3230, Section 1, Spring 2025
Purpose: This program implements an Arbitrator class that uses
Lamports Algorithm to ensure mutual exclusion. The Arbitrator 
allows multiple nodes to request access to a critical section 
(CS) while ensuring that only one node can enter the CS at a 
time. The algorithm resolves conflicts using ticket numbers 
and ensures fairness by breaking ties based on node IDs.

Input: The number of nodes in the system and the node IDs 
requesting access to the critical section.

Output: The program ensures mutual exclusion by allowing 
nodes to enter the critical section in a safe and fair manner. 
No two nodes will be in the critical section simultaneously.
---------------------------------------------------------------------*/
class Ticket
{
    /**
     * Represents a ticket in Lamport's bakery algorithm.
     * The ticket value determines the order in which nodes enter the critical section.
     * A value of 0 indicates the node is not requesting access.
     */
	public volatile int value = 0;
}

class Arbitrator
{
    /**
     * Implements Lamport's bakery algorithm for mutual exclusion.
     * Manages access to critical sections among multiple nodes using
     * a ticket-based system for ordering.
     */
	
	private int numNodes = 0; 
	private Ticket[] ticket = null;
	
    /**
     * Constructor for the Arbitrator.
     * @param numNodes int - The total number of nodes that will compete for the critical section
     */
	public Arbitrator(int numNodes)
	{
		this.numNodes = numNodes;
		ticket = new Ticket[numNodes];
		for (int i = 0; i < numNodes; i++)
			ticket[i] = new Ticket();
	}
	
    /**
     * Finds the maximum ticket value among all nodes.
     * @param ticket Ticket[] - Array of tickets to search through
     * @return int - The highest ticket value found
     */
	private int maxx(Ticket[] ticket)
	{
		int mx = ticket[0].value;
		for (int i = 1; i < ticket.length; i++)
			if (ticket[i].value > mx)
				mx = ticket[i].value;
		return mx;
	}
	
    /**
     * Request entry to the critical section for a node.
     * Implements Lamport's bakery algorithm by assigning a ticket number
     * and waiting until it's the node's turn to enter.
     * @param i int - The ID of the node requesting access
     */
	public void wantToEnterCS(int i) 
	{
		ticket[i].value = 1;
		ticket[i].value = 1 + maxx(ticket); 
		for (int j = 0; j < numNodes; j++)
			if (j != i)
				while (!(ticket[j].value ==0 || ticket[i].value < ticket[j].value
					||//break a tie
					(ticket[i].value == ticket[j].value && i<j)))
					Thread.currentThread().yield();
	}

    /**
     * Signals that a node has finished using the critical section.
     * @param i int - The ID of the node that has finished
     */
	public void finishedInCS(int i)
	{
		ticket[i].value = 0;
	}
}
