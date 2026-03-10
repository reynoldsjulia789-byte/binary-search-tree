package main;

import java.util.Objects;

public class BinarySearchTree<Type extends Comparable<Type>>
{
    private class Node
    {
        Node left;
        Node right;
        Type data;

        public Node(Type data)
        {
            this.left  = null;
            this.right = null;
            this.data  = data;
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
     * Uses a recursive helper.
     */
    public void insert(Type data)
    {
        root = insert(root, data);
    }

    /**
     * Recursive insert -- given a node pointer, recur down and
     * insert the given data into the tree. Returns the new
     * node pointer (the standard way to communicate
     * a changed pointer back to the caller).
     */
    private Node insert(Node node, Type data)
    {
        if (node == null)
        {
            node = new Node(data);
        }
        else
        {
            if (data.compareTo(node.data) < 0)
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
     * @return true if data is contained in the tree, false if not
     */
    public boolean lookup(Type data)
    {
        return lookup(this.root, data);
    }

    /**
     * Recursive helper for lookup method. Given a node, recur down searching for the given data.
     * @param node node to search
     * @param data data to search for
     * @return returns true if data is found, false if not
     */
    private boolean lookup(Node node, Type data)
    {
        if (node == null)
        {
            return false;
        }

        if (Objects.equals(data, node.data))
        {
            return true;
        }
        else if (data.compareTo(node.data) < 0)
        {
            return lookup(node.left, data);
        }
        else
        {
            return lookup(node.right, data);
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

    public String postorderToString()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return postorderToString(root, builder).toString().trim();
    }

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

    public String preorderToString()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return preorderToString(root, builder).toString().trim();
    }

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

    @Override
    public String toString()
    {
        return inorderToString();
    }
}
