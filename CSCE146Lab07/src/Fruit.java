/*
 * Written by John Llamas
 */
public class Fruit implements Comparable<Fruit>
{
	//Instance variables
	private String type;
	private double weight;
	
	//Constructors
	public Fruit()
	{
		this.type = "apple";
		this.weight = 1.0;
	}
	
	//Parameterized constructor
	public Fruit(String type, double weight)
	{
		if(type != null && !type.isEmpty())
			this.type = type;
		else
			this.type = "apple";
		
		if(weight > 0)
			this.weight = weight;
		else
			this.weight = 1.0;
	}
	//Setters(Accessors)
	public void setType(String type)
	{
		if(type != null && type.isEmpty())
			this.type = type;
		else 
			this.type = "apple";
	}
	public void setWeight()
	{
		if(weight > 0)
			weight = 1.0;
	}
	
	//Getters(Mutators)
	public String getType()
	{
		return type;
	}
	public double getWeight()
	{
		return weight;
	}
	
	@Override
	public String toString()
	{
		return "Type: " + type + " Weight: " + weight;
	}
	
	public int compareTo(Fruit alt)
	{
		if(alt == null)
			return -1;
		if(this.weight > alt.weight)
			return 1;
		else if(this.weight < alt.weight)
			return -1;
		else
			return this.type.compareTo(alt.type);
	}
} // end of Fruit class