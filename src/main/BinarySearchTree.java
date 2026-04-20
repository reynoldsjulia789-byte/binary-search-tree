package main;

import java.util.Objects;

public class BinarySearchTree<Type extends Comparable<Type>>
{
    private class Node
    {
        protected Node left;
        protected Node right;
        protected Node parent;
        protected Type data;
        protected int  balanceFactor;

        protected Node(Type data)
        {
            this.left           = null;
            this.right          = null;
            this.parent         = null;
            this.data           = data;
            this.balanceFactor  = 0;
        }

        protected void setLeft(Node left)
        {
            this.left = left;
        }

        protected void setRight(Node right)
        {
            this.right = right;
        }

        protected void setParent(Node parent)
        {
            this.parent = parent;
        }

        protected void setData(Type data)
        {
            this.data = data;
        }

        protected void setBalanceFactor(int balanceFactor)
        {
            this.balanceFactor = balanceFactor;
        }

        protected Node getLeft()
        {
            return left;
        }

        protected Node getRight()
        {
            return right;
        }

        protected Node getParent()
        {
            return parent;
        }

        protected int getBalanceFactor()
        {
            return balanceFactor;
        }

        protected Type getData()
        {
            return data;
        }
    }

    // Root node pointer. Will be null for an empty tree.
    private Node root;

    /**
     * Creates an empty binary tree -- a null root pointer.
     */
    public BinarySearchTree()
    {
        root = null;
    }

    /**
     * Creates a binary tree with data.
     * @param data data to insert into the tree
     */
    @SafeVarargs
    public BinarySearchTree(Type... data)
    {
        this();

        for (Type datum : data)
        {
            insert(datum);
        }
    }

    /**
     * Inserts the given data into the binary tree.
     * Uses a recursive helper. Duplicate data not allowed.
     * @param data the data to insert into the tree
     * @return true if successful, false if not
     */
    public boolean insert(Type data)
    {
        try
        {
            this.root = insert(this.root, data);

            return true;
        }
        catch (Exception caught)
        {
            return false;
        }
    }

    /**
     * Recursive insert: given a node pointer, recur down and
     * insert the given data into the tree. Returns the new
     * node pointer.
     * @param node node to look at
     * @param data the data to insert
     * @return node
     */
    private Node insert(Node node, Type data)
    {
        if (node == null)
        {
            node = new Node(data);
        }
        else
        {
            if (data.compareTo(node.data) == 0)
            {
                throw new IllegalArgumentException("duplicate data not allowed in tree");
            }
            else if (data.compareTo(node.data) < 0)
            {
                node.left = insert(node.left, data);
            }
            else
            {
                node.right = insert(node.right, data);
            }
        }

        return(node); // in any case, return the new pointer to the caller
    }

    /**
     * Look up data in binary tree
     * @param data the data to look for
     * @return returns the level the data is at in the tree if found, -1 if not found
     */
    public int find(Type data)
    {
        return find(this.root, data, 1);
    }

    /**
     * Recursive helper for find method. Given a node, recur down searching for the given data.
     * @param node node to search
     * @param data data to search for
     * @return returns the level the data is at in the tree if found, -1 if not found
     */
    private int find(Node node, Type data, int level)
    {
        if (node == null)
        {
            return -1; // base case 1: node is null - reached end of tree and did not find
        }

        if (Objects.equals(data, node.data))
        {
            return level; // base case 2: data was found - return level the data was found at
        }
        else if (data.compareTo(node.data) < 0) // data is less than the data in the node
        {
            return find(node.left, data, level + 1); // look on the left side of the tree
        }
        else
        {
            return find(node.right, data, level + 1); // look on the right side of the tree
        }
    }

    /**
     * Prints the tree contents in order from smallest to largest
     * @return String representing the binary tree
     */
    public String inorderToString()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return inorderToString(root, builder).toString().trim();
    }

    /**
     * Recursive helper for inorder ToString
     * @param node node of the tree
     * @param builder string builder object
     * @return StringBuilder with tree data in order
     */
    private StringBuilder inorderToString(Node node, StringBuilder builder)
    {
        if (node != null)
        {
            inorderToString(node.left, builder);

            builder.append(node.data).append(" ");

            inorderToString(node.right, builder);
        }

        return builder;
    }

    /**
     * Creates a postorder string representing the binary tree
     * @return String representing the binary tree
     */
    public String postorderToString()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return postorderToString(root, builder).toString().trim();
    }

    /**
     * Recursive helper for postorder ToString
     * @param node node of the tree
     * @param builder string builder object
     * @return StringBuilder with tree data in order
     */
    private StringBuilder postorderToString(Node node, StringBuilder builder)
    {
        if (node != null)
        {
            postorderToString(node.left, builder);
            postorderToString(node.right, builder);

            builder.append(node.data).append(" ");
        }

        return builder;
    }

    /**
     * Creates a preorder String representing the binary tree
     * @return String representing the binary tree
     */
    public String preorderToString()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return preorderToString(root, builder).toString().trim();
    }

    /**
     * Recursive helper for preorder ToString
     * @param node node of the tree
     * @param builder string builder object
     * @return StringBuilder with tree data in order
     */
    private StringBuilder preorderToString(Node node, StringBuilder builder)
    {
        if (node != null)
        {
            builder.append(node.data).append(" ");

            preorderToString(node.left, builder);
            preorderToString(node.right, builder);
        }

        return builder;
    }

    /**
     * toString method - uses inorderToString
     * @return String representing the binary tree
     */
    @Override
    public String toString()
    {
        return inorderToString();
    }
}
