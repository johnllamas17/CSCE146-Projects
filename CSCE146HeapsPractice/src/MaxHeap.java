/*
 * Written by John Llamas
 */
public class MaxHeap<T extends Comparable<T>>
{
	private T[] heap;
	private int size; // Also the first null reference
	public static int DEF_SIZE = 128;  // 64, 32, 256, 512, 1024 
	
	public MaxHeap()
	{
		init(DEF_SIZE);
	}
	public MaxHeap(int size)
	{
		init(size);
	}
	private void init(int size)
	{
		if(size <= 1)
		{
			heap = (T[])(new Comparable[DEF_SIZE]);
		} else
			heap = (T[])(new Comparable[size]);
	}
	
	public void add(T aData)
	{
		if(heap[heap.length -1] != null)
			return;
		heap[size] = aData; // Add at the leaf
		bubbleUp();
		size++;
	}
	
	private void bubbleUp()
	{
		int currIndex = size;
		while(currIndex > 0 && heap[(currIndex -1) / 2].compareTo(heap[currIndex]) < 0)
		{
			//Swap
			T temp = heap[(currIndex - 1) /2];
			heap[(currIndex - 1) / 2] = heap[currIndex];
			heap[currIndex] = temp;
			currIndex = (currIndex - 1) /2;
		}
	}
	
	public T remove()
	{
		if(size <= 0)
			return null;
		T ret = heap[0]; // Temp stored in the root
		heap[0] = heap[size - 1];
		heap[size -1] = null;
		size--;
		bubbleDown();
		return ret;
	}
	
	private void bubbleDown()
	{
		int currIndex = 0;
		while(currIndex * 2 +1 < size)
		{
			int bigIndex = currIndex * 2 + 1; // Assumes left is larger
			if(currIndex * 2 + 1 < size && 
					heap[currIndex * 2 + 1].compareTo(heap[currIndex * 2 + 2]) < 0)
				bigIndex = currIndex * 2  + 2;
			if(heap[currIndex].compareTo(heap[bigIndex]) < 0)
			{ // if current index is smaller that big index
				// SWAP
				T temp = heap[currIndex];
				heap[currIndex] = heap[bigIndex];
				heap[bigIndex] = temp;
			}
			else
				break;
			currIndex = bigIndex;	
		}
		
	}
	
	public T peek()
	{
		return heap[0];
	}
	
	public void print()
	{
		for(int i = 0; i < size; i++)
			System.out.println(heap[i]);
	}
	
} // end of MaxHeap class
