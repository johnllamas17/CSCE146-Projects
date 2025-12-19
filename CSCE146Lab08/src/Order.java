/*
 * Written by John Llamas
 */
public class Order implements Comparable<Order>
{
	//Instance variables
	private String customer;
	private String foodOrder;
	private int cookingTime;
	private int arrivalTime;
	private int remainingTime;
	
	//Constructors
	public Order()
	{
		customer = foodOrder = "none";
		cookingTime = 1;
		arrivalTime = 0;
		remainingTime = cookingTime;
	}
	//Parameterized
	public Order(String customer, String foodOrder, int cookingTime, int arrivalTime)
	{
		if(customer != null)
			this.customer = customer;
		else
			this.customer = "none";
		
		if(foodOrder != null)
			this.foodOrder = foodOrder;
		else
			this.foodOrder = "none";
		
		if(cookingTime > 0)
			this.cookingTime = cookingTime;
		else
			this.cookingTime = 1;
		
		if(arrivalTime > 0)
			this.arrivalTime = arrivalTime;
		else
			this.arrivalTime = 0;
		this.remainingTime = this.cookingTime;
	}
	
	//Accessors
	public String getCustomer()
	{
		return customer;
	}
	public String getFoodOrder()
	{
		return foodOrder;
	}
	public int getCookingTime()
	{
		return cookingTime;
	}
	public int getArrivalTime()
	{
		return arrivalTime;
	}
	
	public int getRemainingTime()
	{
		return remainingTime;
	}
	//Mutators
	public void setCustomer(String customer)
	{
		if(customer != null)
			this.customer = customer;
		else
			this.customer = "none";
	}
	
	public void setFoodOrder(String foodOrder)
	{
		if(foodOrder != null)
			this.foodOrder = foodOrder;
		else
			this.foodOrder = "none";
	}
	
	public void setCookingTime(int cookingTime)
	{
		if(cookingTime > 1)
			this.cookingTime = cookingTime;
		else
			this.cookingTime = 1;
	}
	
	public void setArrivalTime(int arrivalTime)
	{
		if(arrivalTime > 0)
			this.arrivalTime = arrivalTime;
		else
			this.arrivalTime = 0;
	}
	
	public void setRemainingTime(int cookingTime)
	{
		if(cookingTime > 1)
			this.remainingTime = cookingTime;
	}
	
	@Override
	public String toString()
	{
		return "Customer: " + customer + " Order: " + foodOrder + ", Cooking Time Left: " + remainingTime;
	}
	// Compares object of Order
	public int compareTo(Order newOrder)
	{
		if(this.remainingTime < newOrder.remainingTime)
			return -1;
		else if(this.remainingTime > newOrder.remainingTime)
			return 1;
		else
			return 0;
	}
	
	public void cookForOneMinute()
	{
		remainingTime--;
	}
	
	public boolean isDone()
	{
		return remainingTime <= 0;
	}
} // end of Order class
