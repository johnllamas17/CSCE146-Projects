/*
 * Written by John Llamas
 */
public class Shape implements Comparable<Shape>
{
	//Instance Variables
	private String type;
	private double length, width, radius, base, height;
	private double area;
	//Constructor
	public Shape(String type, double a, double b)
	{
		this.type = type.trim();
		
	switch (type.toLowerCase())
	{
	//Assigns shape with respective base, height, length etc.
	case "rectangle":
		this.length = a;
		this.width = b;
		this.area = length * width;
		break;
	case "circle":
		this.radius = a;
		this.area = Math.PI * radius * radius;
		break;
	case "right triangle":
		this.base = a;
		this.height = b;
		this.area = 0.5 * base * height;
		break;
	default:
		this.area = 0.0;
		}
	}
	//Accessor methods 
	public String getType()
	{
		return type;
	}
	
	public double getArea()
	{
		return area;
	}
	
	public double getLength()
	{
		return length;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public double getBase()
	{
		return base;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	@Override
	public String toString()
	{
		switch(type.toLowerCase())
		{
		case "rectangle":
            return "Rectangle Length: " + length + " Width: " + width + " Area: " + area;
        case "circle":
            return "Circle Radius: " + radius + " Area: " + area;
        case "right triangle":
            return "Right Triangle Base: " + base + " Height: " + height + " Area: " + area;
        default:
            return type + " Area: " + area;
		}
	}
	
	public String toFileString() 
	{
        switch (type.toLowerCase())
        {
            case "rectangle":
                return "Rectangle\t" + length + "\t" + width;
            case "circle":
                return "Circle\t" + radius;
            case "right triangle":
                return "Right Triangle\t" + base + "\t" + height;
            default:
                return type + "\t0";
        }
    }
	
	@Override
    public int compareTo(Shape other)
	{
        double diff = this.area - other.area;
        if (diff < 0) return -1;
        if (diff > 0) return 1;

        // Tie-breaker by type: Circle < Rectangle < Right Triangle
        return Integer.compare(typeRank(this.type), typeRank(other.type));
    }
	
	private int typeRank(String t) 
	{
        t = t.toLowerCase();
        if (t.equals("circle")) return 1;
        if (t.equals("rectangle")) return 2;
        if (t.equals("right triangle")) return 3;
        return 99;
    }
} // end of Shape class
