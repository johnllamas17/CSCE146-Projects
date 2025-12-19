/*
 * Written by John Llamas
 */
public class OrderScheduler
{
	//Instance Variables
	private MinHeap<Order> orders;
	private Order currOrder;
	private int currMinute;
	private int totalOrders;
	private int summedWaitTime;
	
	public OrderScheduler()
	{
		orders = new MinHeap<>();
		currOrder = null;
		currMinute = 0;
		totalOrders = 0;
		summedWaitTime = 0;
	}
	public void addOrder(Order newOrder)
	{
		if(currOrder == null && newOrder.getArrivalTime() <= currMinute)
		{
			currOrder = newOrder;
		}
		else
		{
			orders.add(newOrder);
		}
		totalOrders++;
	}
	public Order getCurrentOrder() 
	{
		return currOrder;
	}
	public void advanceOneMinute()
	{
	    // If there is no current order, wait until one arrives
	    if (currOrder == null)
	    {
	        if (!orders.isEmpty() && orders.peek().getArrivalTime() <= currMinute) {
	            currOrder = orders.remove();
	        } else 
	        {
	            currMinute++; // still pass time
	            return;
	        }
	    }
	    // Cook for one minute
	    currOrder.cookForOneMinute();

	    // If the current order finishes this minute
	    if (currOrder.isDone()) {
	        int finishTime = currMinute + 1; // finishes at the end of this minute
	        int wait = finishTime - currOrder.getArrivalTime(); // turnaround time
	        if (wait < 0) wait = 0;

	        summedWaitTime += wait;

	        // Do NOT add an extra idle minute — this was causing 61.1
	        currMinute = finishTime;

	        // Start next order if ready
	        if (!orders.isEmpty() && orders.peek().getArrivalTime() <= currMinute) 
	        {
	            currOrder = orders.remove();
	        } else {
	            currOrder = null;
	        }

	        return; // done with this tick
	    }

	    // Otherwise, still cooking — advance one minute
	    currMinute++;
	}
	//Returns the average waiting time of food order
	public double getAverageWaitingTime()
	{
		if(totalOrders == 0)
			return 0.0;
		return (double)summedWaitTime / totalOrders;
	}
	public boolean isDone()
	{
		return currOrder == null && orders.peek() == null;
	}
} // end of OrderScheduler class
