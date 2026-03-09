package main;

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

    public String inorderToString()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return inorderToString(root, builder).toString().trim();
    }

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
