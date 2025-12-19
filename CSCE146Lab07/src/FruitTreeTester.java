/*
 * Written by John Llamas
 */
//Implements type variable 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FruitTreeTester<T extends Comparable<T>>
{
	public static void main(String[] args) throws FileNotFoundException
	{
		LinkedBST<Fruit> tree = new LinkedBST<>();
		Scanner scnr = new Scanner(System.in);
		System.out.println("Welcome to the Fruit Tree!");
		System.out.println("Please enter a Fruit file name");
		
		String fileName = scnr.next();
		System.out.println("Populating tree file");
		File file = new File(fileName);
		Scanner fileReader = new Scanner(file);
		
		while(fileReader.hasNextLine())
		{
			String line = fileReader.nextLine();
			if(line.isEmpty())
				continue;
			try (Scanner lineScan = new Scanner(line)) {
				if(lineScan.hasNext())
				{
					String type = lineScan.next();
					if(lineScan.hasNextDouble())
					{
						double weight = lineScan.nextDouble();
						Fruit newFruit = new Fruit(type, weight);
						tree.add(newFruit);
					}
				}
			}		
		}
		fileReader.close();
		
		System.out.println("Printing the in-order traversal");
		tree.printInorder();
		
		System.out.println("Printing the pre-order traversal");
		tree.printPreorder();
		
		System.out.println("Printing the post-order travesal");
		tree.printPostOrder();
		scnr.close();
	} // end of main method
	
} // end of FruitTreeTester class
