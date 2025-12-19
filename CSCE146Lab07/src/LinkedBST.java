/*
 * Written by John Llamas
 */
public class LinkedBST<T extends Comparable<T>>
{
	private class Node
	{
		 T data;
		Node left, right;
		
		public Node(T data)
		{
			this.data = data;
			this.left = this.right = null;
		}
	}
	private Node root; // head of the tree, first element
	
	public LinkedBST()
	{
		root = null;
	}
	
	public void add(T aData)
	{
		if(root == null)
			root = new Node(aData);
		else
			root = add(root, aData); // calls recursive helper method
	}
	
	private Node add(Node aNode, T aData) // recursive helper method
	{
		if(aNode == null) // leaf
			aNode = new Node(aData);
		else if(aData.compareTo(aNode.data) < 0)// Go left
			aNode.left = add(aNode.left, aData);
		else if(aData.compareTo(aNode.data) > 0) // Go right
			aNode.right = add(aNode.right, aData);
		return aNode;
	}
	
	public void printPreorder()
	{
		printPreorder(root);
	}
	
	private void printPreorder(Node aNode)
	{
		if(aNode == null)
			return;
		System.out.println(aNode.data); // root
		printPreorder(aNode.left); // Left
		printPreorder(aNode.right); //Right
	}
	
	public void printInorder()
	{
		printInorder(root);
	}
	
	private void printInorder(Node aNode)
	{
		if(aNode == null)
			return;
		printInorder(aNode.left); // Left
		System.out.println(aNode.data); // Process
		printInorder(aNode.right); //Right
	}
	
	public boolean search(T aData)
	{
		return search(root, aData);
	}
	
	private boolean search(Node aNode, T aData)
	{
		if(aNode == null)
			return false;
		else if(aData.compareTo(aNode.data) < 0) // Go left
			return search(aNode.left, aData);
		else if(aData.compareTo(aNode.data) > 0) // Go right
			return search(aNode.right, aData);
		else
			return true;
	}
	
	public void remove(T aData)
	{
		root = remove(root, aData);
	}
	
	private Node remove(Node aNode, T aData)
	{
		//Finds node
		if(aNode == null)
			return null;
		else if(aData.compareTo(aNode.data) < 0) // Go left
			aNode.left = remove(aNode.left, aData);
		else if(aData.compareTo(aNode.data) > 0) // Go right
			aNode.right = remove(aNode.right, aData);
		
		else
		{
			if(aNode.right == null)
				return null;
			else if(aNode.left == null)
				return null;
			Node temp = findMin(aNode.right);
			aNode.data = temp.data;
			aNode.right = remove(aNode.right, aData);
		}
		return aNode;
	}
	
	public void printPostOrder()
	{
		printPostOrder(root);
	}
	
	private void printPostOrder(Node aNode)
	{
		if(aNode == null)
			return;
		printPostOrder(aNode.left); // Go left
		printPostOrder(aNode.right); // Go right
		System.out.println(aNode.data); // root

		
	}
	private Node findMin(Node aNode)
	{
	if(aNode == null)
		return null;
	else if(aNode.left == null)
		return aNode;
	else
		return findMin(aNode.left);
	}
} // end of LinkedBST class
