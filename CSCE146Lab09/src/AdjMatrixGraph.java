/*
 * Written by John Llamas
 */
import java.util.*;
public class AdjMatrixGraph
{
	public static final int DEF_VERT_COUNT = 10;
	private double[][] adjMatrix;
	private LinkedList<Integer> markedList;
	private Queue<Integer> vQ;
	
	public AdjMatrixGraph()
	{
		init(DEF_VERT_COUNT);
	}
	public AdjMatrixGraph(int size)
	{
		init(size);
	}
	private void init(int size)
	{
		if(size <= 0)
		{
			init(DEF_VERT_COUNT);
			return;
		}
		adjMatrix = new double[size][size];
		for(int i = 0; i < adjMatrix.length;i++)
		{
			for(int j = 0; j < adjMatrix[i].length;j++)
			{
				adjMatrix[i][j] = 0;
			}
		}
		markedList = new LinkedList<Integer>();
		vQ = new LinkedList<Integer>();
	}
	public boolean isValid(int index)
	{
		return index >= 0 && index < adjMatrix.length;
	}
	public void printAdjMatrix()
	{
		for(int i = 0; i < adjMatrix.length;i++)
		{
			for(int j = 0; j < adjMatrix[i].length;j++)
			{
				System.out.print(adjMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	public void printDFS()
	{
		markedList.clear();
		printDFS(0);
	}
	private void printDFS(int index)
	{
		System.out.println(index);
		markedList.add(index);
		for(int i = 0; i < adjMatrix.length;i++)
		{
			if(adjMatrix[index][i] != 0.0 && !markedList.contains(i))
					printDFS(i);
		}
	}
	public void printDFSFrom(int index)
	{
		if(!isValid(index))
			return;
		markedList.clear();
		printDFS(index);
	}
	public void printBFS()
	{
	    markedList.clear();
	    vQ.clear();

	    vQ.add(0);
	    markedList.add(0);

	    while(!vQ.isEmpty())
	    {
	        int curr = vQ.remove();
	        System.out.println(curr);

	        for(int i = 0; i < adjMatrix.length; i++)
	        {
	            if(adjMatrix[curr][i] != 0.0 && !markedList.contains(i))
	            {
	                markedList.add(i);
	                vQ.add(i);
	            }
	        }
	    }
	}
	public void printBFSFrom(int index)
	{
		if(!isValid(index))
			return;
		markedList.clear();
		vQ.clear();
		markedList.add(index);
		vQ.add(index);
		System.out.println(index);
		while(!vQ.isEmpty())
		{
			int curr = vQ.remove();
			for(int i = 0; i < adjMatrix.length; i++)
			{
				if(adjMatrix[curr][i] != 0.0 && !markedList.contains(i) && !vQ.contains(i))
				{
					System.out.println(i);
					markedList.add(i);
					vQ.add(i);
				}
			}
		}
	}
	public void addEdge(int fromVertex, int toVertex, int weight) 
	{
		if(!isValid(fromVertex) || !isValid(toVertex))
			return;
		adjMatrix[fromVertex][toVertex] = (double)weight;
	}
} // end of AdjMatrixGraph
