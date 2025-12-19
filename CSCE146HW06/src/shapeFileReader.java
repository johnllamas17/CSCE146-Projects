/*
 * Written by John Llamas
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class shapeFileReader 
{
	public static void readFromFile(LinkedBST<Shape> tree, String fileName) throws FileNotFoundException
	{
		// Passes scanner into input file
		Scanner fileScan = new Scanner(new File(fileName));
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String shapeType;
            int idx = 1;

            if (parts[0].equalsIgnoreCase("Right") && parts[1].equalsIgnoreCase("Triangle"))
            {
                shapeType = "Right Triangle";
                idx = 2;
            } else
            {
                shapeType = parts[0];
            }

            double a = 0, b = 0;
            if (idx < parts.length) a = Double.parseDouble(parts[idx++]);
            if (idx < parts.length) b = Double.parseDouble(parts[idx++]);

            //adds shape into tree, except Rombus
            Shape s = new Shape(shapeType, a, b);
            if (shapeType.equalsIgnoreCase("Rectangle") ||
            	    shapeType.equalsIgnoreCase("Circle") ||
            	    shapeType.equalsIgnoreCase("Right Triangle")) {
            	    tree.add(s);
            	}
        }
        fileScan.close();
	}
	//Prints to new output file
	public static void writeToFile(LinkedBST<Shape> tree, String fileName) throws FileNotFoundException
	{
		PrintWriter output = new PrintWriter(fileName);
		writeInorder(tree.getRoot(), output);
		output.close();
	}
	//Prints to output file in traversal in-order
	public static void writeInorder(LinkedBST<Shape>.Node n, PrintWriter output)
	{
		if (n == null)
			return;
		writeInorder(n.left, output);
		output.println(((Shape) n.data).toFileString());
		writeInorder(n.right, output);
	}
} // end of shapeFileReader class
