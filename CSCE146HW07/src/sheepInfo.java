/*
 * Written by John Llamas
 */
public class sheepInfo implements Comparable<sheepInfo>
{
	//Instance variables
	private String sheepName;
	//Use both shear and arrival time for determining priority
	private int shearTime;
	private int arrivalTime;
	
	//Constructor
	public sheepInfo(String name, int shear, int arrival)
	{
		this.sheepName = name;
		this.shearTime = shear;
		this.arrivalTime = arrival;
	}
	
	//Access methods (Get)
	public String getSheepName()
	{
		return sheepName;
	}
	public int getShearTime()
	{
		return shearTime;
	}
	public int getArrivalTime()
	{
		return arrivalTime;
	}
	
	//Modifiers (Set)
	public void setSheepName(String name)
	{
		 this.sheepName = name;
	}
	public void setshearTime(int shear)
	{
		this.shearTime = shear;
	}
	public void setArrivalTime(int arrival)
	{
		this.arrivalTime = arrival;
	}
	
	@Override
	public String toString()
	{
		return "Name: " + sheepName + ", Shear Time: " + shearTime + ", Arrival Time: " + arrivalTime;
	}
	@Override
	public int compareTo(sheepInfo newSheep)
	{
		if(this.shearTime < newSheep.shearTime)
			return -1;
		if(this.shearTime > newSheep.shearTime)
			return 1;
		else
			return this.sheepName.compareTo(newSheep.sheepName);
	}
} // end of sheepInfo class
