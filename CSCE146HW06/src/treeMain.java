/*
 * Written by John Llamas
 */
import java.util.Scanner;
public class treeMain 
{

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		Scanner scnr = new Scanner(System.in);
		
		LinkedBST<Shape> tree = new LinkedBST<>();

        boolean running = true;
        while (running) {
            System.out.println("Enter 1. To Read a Shape Tree from a File.");
            System.out.println("Enter 2. To Print a Tree Traversal to the Console");
            System.out.println("Enter 3. To Add a Shape.");
            System.out.println("Enter 4. To Remove a Shape.");
            System.out.println("Enter 5. To Search for a Shape.");
            System.out.println("Enter 6. To Find the Shape with the Max Area.");
            System.out.println("Enter 7. To Remove All Shapes Greater than an Area.");
            System.out.println("Enter 8. To Print Shape Tree to File.");
            System.out.println("Enter 0. To Quit.");
            int choice = Integer.parseInt(scnr.nextLine().trim());
            
            switch (choice) {
            case 1:
                System.out.println("Enter the file's name");
                String fname = scnr.nextLine().trim();
                shapeFileReader.readFromFile(tree, fname);
                System.out.println("Printing after Reading In-Order");
                tree.printInorder();
                break;

            case 2:
                System.out.println("Which traversal?");
                System.out.println("Enter 1. For Pre-order.");
                System.out.println("Enter 2. For In-order");
                System.out.println("Enter 3. For Post-order");
                int t = Integer.parseInt(scnr.nextLine().trim());
                if (t == 1) tree.printPreorder();
                else if (t == 2) tree.printInorder();
                else tree.printPostorder();
                break;

            case 3:
                Shape addShape = promptForShape(scnr, "add");
                tree.add(addShape);
                break;

            case 4:
                Shape remShape = promptForShape(scnr, "remove");
                tree.remove(remShape);
                break;

            case 5:
                Shape searchShape = promptForShape(scnr, "search");
                System.out.println("Was the shape in the tree? " + tree.search(searchShape));
                break;

            case 6:
                Shape max = tree.findMax();
                System.out.println("The shape with the max area " + max);
                break;

            case 7:
                System.out.println("Enter the maximum area");
                double area = Double.parseDouble(scnr.nextLine().trim());
                tree.removeGreaterThan(area);
                break;

            case 8:
                System.out.println("Enter the file's name");
                String outName = scnr.nextLine().trim();
                shapeFileReader.writeToFile(tree, outName);
                break;

            case 0:
                System.out.println("Goodbye!");
                running = false;
                break;
	} // end of switch
       } // end of while
	} // end of main
	
	public static Shape promptForShape(Scanner scnr, String action)
	{
		System.out.println("Enter the type of shape to" + action);
		String type = scnr.nextLine().trim();

        double a = 0, b = 0;
        switch (type.toLowerCase()) 
        {
            case "rectangle":
                System.out.println("Enter the length followed by the width");
                a = Double.parseDouble(scnr.nextLine().trim());
                b = Double.parseDouble(scnr.nextLine().trim());
                break;
            case "circle":
                System.out.println("Enter the radius");
                a = Double.parseDouble(scnr.nextLine().trim());
                break;
            case "right triangle":
                System.out.println("Enter the base followed by the height");
                a = Double.parseDouble(scnr.nextLine().trim());
                b = Double.parseDouble(scnr.nextLine().trim());
                break;
            default:
                System.out.println("Unknown shape type!");
        }
        return new Shape(type, a, b);
	}
} // end of treeMain class
