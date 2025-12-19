/*
 * Written by John Llamas
 */
public class LinkedBST<T extends Comparable<T>>
{
    class Node {
        T data;
        Node left, right;
        Node(T d) { data = d; }
    }

    private Node root;

    public void add(T item) 
    {
        root = addRec(root, item);
    }

    private Node addRec(Node n, T item) 
    {
        if (n == null) return new Node(item);
        if (item.compareTo(n.data) < 0)
            n.left = addRec(n.left, item);
        else
            n.right = addRec(n.right, item);
        return n;
    }

    public boolean search(T item)
    {
        return searchRec(root, item);
    }

    private boolean searchRec(Node n, T item)
    {
        if (n == null) return false;
        int c = item.compareTo(n.data);
        if (c == 0) return true;
        if (c < 0) return searchRec(n.left, item);
        return searchRec(n.right, item);
    }

    public void remove(T item)
    {
        root = removeRec(root, item);
    }

    private Node removeRec(Node n, T item) 
    {
        if (n == null) return null;
        int c = item.compareTo(n.data);
        if (c < 0) n.left = removeRec(n.left, item);
        else if (c > 0) n.right = removeRec(n.right, item);
        else {
            // node to remove
            if (n.left == null) return n.right;
            if (n.right == null) return n.left;
            n.data = findMin(n.right).data;
            n.right = removeRec(n.right, n.data);
        }
        return n;
    }

    private Node findMin(Node n)
    {
        return n.left == null ? n : findMin(n.left);
    }

    public void printInorder()
    {
        inorder(root);
    }

    private void inorder(Node n) 
    {
        if (n == null) return;
        inorder(n.left);
        System.out.println(n.data);
        inorder(n.right);
    }

    public void printPreorder()
    {
        preorder(root);
    }

    private void preorder(Node n)
    {
        if (n == null) return;
        System.out.println(n.data);
        preorder(n.left);
        preorder(n.right);
    }

    public void printPostorder()
    {
        postorder(root);
    }

    private void postorder(Node n)
    {
        if (n == null) return;
        postorder(n.left);
        postorder(n.right);
        System.out.println(n.data);
    }

    // Get rightmost node
    public T findMax()
    {
        Node n = root;
        if (n == null) return null;
        while (n.right != null) n = n.right;
        return n.data;
    }

    public void removeGreaterThan(double area)
    {
        root = removeGreaterThanRec(root, area);
    }

    private Node removeGreaterThanRec(Node n, double area)
    {
        if (n == null) return null;
        Shape s = (Shape) n.data;
        if (s.getArea() > area) return removeGreaterThanRec(n.left, area);
        n.left = removeGreaterThanRec(n.left, area);
        n.right = removeGreaterThanRec(n.right, area);
        return n;
    }

    public Node getRoot() 
    { 
    	return root;
    }
}