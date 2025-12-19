/*
 * Written by John Llamas
 */
public class MinHeap <T extends Comparable<T>>
{
	//Instance variables
	private T[] heap;
	private int size;
	public static int DEF_SIZE = 128;
	
	//Constructor
	public MinHeap()
	{
		init(DEF_SIZE);
	}
	
	public MinHeap(int size)
	{
		init(size);
	}
	@SuppressWarnings("unchecked")
	private void init(int size)
	{
		if(size > 1)
			heap = (T[])(new Comparable[size]);
		else
			heap = (T[])(new Comparable[DEF_SIZE]);
		this.size = 0;
	}
	public void add(T aData)
	{
		if(size >= heap.length)
			return;
		heap[size] = aData;
		this.bubbleUp();
		size++;
	}
	 // Checks if parent data is greater than child(aData), if so then swaps values
	public void bubbleUp()
	{
		int currIndex = this.size;
		// while parent node is greater than child
		while(currIndex > 0 && heap[parentIndex(currIndex)].compareTo(heap[currIndex]) > 0)
		{
			// Swap values
			T temp = heap[parentIndex(currIndex)];
			heap[parentIndex(currIndex)] = heap[currIndex];
			heap[currIndex] = temp;
			currIndex = parentIndex(currIndex);
		}
	}
	private int parentIndex(int index)
	{
		return (index - 1) / 2;
	}
	private int leftIndex(int index)
	{
		return (index * 2 + 1);
	}
	private int rightIndex(int index)
	{
		return (index * 2 + 2);
	}
	public T remove()
	{
		if(size <= 0)
			return null;
		T ret = heap[0];
		heap[0] = heap[size - 1];
		heap[size - 1] = null;
		size--;
		bubbleDown();
		return ret;
	}
	public void bubbleDown()
	{
		int currIndex = 0;
		while(leftIndex(currIndex) < size)
		{
			int smallIndex = leftIndex(currIndex);
			if(rightIndex(currIndex) < size && heap[leftIndex(currIndex)].compareTo(heap[rightIndex(currIndex)]) > 0)
					smallIndex = rightIndex(currIndex);
			if(heap[currIndex].compareTo(heap[smallIndex]) > 0)
			{
				T temp = heap[currIndex];
				heap[currIndex] = heap[smallIndex];
				heap[smallIndex] = temp;
			}
			else
				break;
			currIndex = smallIndex;
		}
	}
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public T peek()
	{
		if(size == 0)
			return null;
		return heap[0];
	}
	
	public void print()
	{
		for(int i = 0; i < size; i++)
		System.out.println(heap[i]);
	}
} // end of MinHeap class
