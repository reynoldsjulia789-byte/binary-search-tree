package main;

import java.util.Objects;

public class BinarySearchTree<Type extends Comparable<Type>>
{
    private class Node
    {
        Node leftChild;
        Node rightChild;
        Node parent;
        Type data;
        int  balanceFactor;

        Node(Type data)
        {
            this.leftChild      = null;
            this.rightChild     = null;
            this.parent         = null;
            this.data           = data;
            this.balanceFactor  = 0;
        }

        Node(Type data, Node parent)
        {
            this(data);
            this.parent = parent;
        }

        @Override
        public String toString()
        {
            return this.data.toString();
        }
    }// end of private Node class

    // Root node pointer. Will be null for an empty tree.
    private Node root;

    /**
     * Creates an empty binary tree -- a null root pointer.
     */
    public BinarySearchTree()
    {
        this.root = null;
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
        if (this.root == null)
        {
            this.root = new Node(data);
            return true;
        }

        try
        {
            insert(this.root, data);
            return true;
        }
        catch (Exception caught)
        {
            return false;
        }
    }

    /** TODO: balance tree after each insert
     * Recursive insert: given a node pointer, recur down and
     * insert the given data into the tree. Returns the new
     * node pointer.
     * @param node node to look at
     * @param data the data to insert
     * @return returns the new node if inserted, throws an exception if not
     */
    private Node insert(Node node, Type data)
    {
        if (data.compareTo(node.data) == 0)
        {
            throw new IllegalArgumentException("duplicate data not allowed in tree");
        }
        else if (data.compareTo(node.data) < 0)
        {
            if (node.leftChild == null)
            {
                node.leftChild = new Node (data, node);
                return node.leftChild;
            }
            else
            {
                insert(node.leftChild, data);
            }
        }
        else
        {
            if (node.rightChild == null)
            {
                node.rightChild = new Node (data, node);
                return node.rightChild;
            }
            else
            {
                insert(node.rightChild, data);
            }
        }

        return node;
    } // end of insert method

    /**
     * Look up data in binary tree
     * @param data the data to look for
     * @return returns the node with the matching data, null if not found
     */
    private Node find(Type data)
    {
        return find(this.root, data, 1);
    }

    /**
     * Recursive helper for find method. Given a node, recur down searching for the given data.
     * @param node node to search
     * @param data data to search for
     * @return returns the node with the matching data, null if not found
     */
    private Node find(Node node, Type data, int level)
    {
        if (node == null)
        {
            return null; // base case 1: node is null - reached end of tree and did not find
        }

        if (Objects.equals(data, node.data))
        {
            return node; // base case 2: data was found - return node
        }
        else if (data.compareTo(node.data) < 0) // data is less than the data in the node
        {
            return find(node.leftChild, data, level + 1); // look on the leftChild side of the tree
        }
        else // data is greater than the data in the node
        {
            return find(node.rightChild, data, level + 1); // look on the rightChild side of the tree
        }
    }

    /** TODO: deal with node that has 2 children
     * TODO: balance tree after each remove
     * Removes the data from the tree, if found
     * @param data data to remove
     * @return returns the removed node if found, null if not
     */
    public Node remove (Type data)
    {
        Node toRemove;
        enum Side {LEFT, RIGHT, ROOT}
        Side parentSide;

        toRemove = find(data);

        if (toRemove == null)
        {
            return null;
        }

        // determine if the node to be removed is on the parent's left or right side
        if (toRemove.parent == null)
        {
            parentSide = Side.ROOT;
        }
        else if (toRemove.parent.leftChild == toRemove)
        {
            parentSide = Side.LEFT;
        }
        else
        {
            parentSide = Side.RIGHT;
        }

        // remove the node
        if (toRemove.leftChild == null && toRemove.rightChild == null) // toRemove has no children
        {
            if (parentSide == Side.LEFT)
            {
                toRemove.parent.leftChild = null;
            }
            else if (parentSide == Side.RIGHT)
            {
                toRemove.parent.rightChild = null;
            }
            else // removing root w/ no children
            {
                this.root = null;
            }
        }
        else if (toRemove.leftChild != null && toRemove.rightChild == null) // toRemove has a left child
        {
            if (parentSide == Side.LEFT)
            {
                toRemove.parent.leftChild = toRemove.leftChild;
            }
            else if (parentSide == Side.RIGHT)
            {
                toRemove.parent.rightChild = toRemove.leftChild;
            }
            else // remove root w/ left child
            {
                this.root = toRemove.leftChild;
            }
        }
        else if (toRemove.leftChild == null) // toRemove has a right child
        {
            if (parentSide == Side.LEFT)
            {
                toRemove.parent.leftChild = toRemove.rightChild;
            }
            else if (parentSide == Side.ROOT)
            {
                toRemove.parent.rightChild = toRemove.rightChild;
            }
            else // remove root w/ right child
            {
                this.root = toRemove.rightChild;
            }
        }
        else // toRemove has 2 children
        {
            // find next closest value node w/ no children, swap value, then delete that node

        }

        return toRemove;
    } // end of Remove method

    /**
     * TODO: finish balance method
     */
    private void balance()
    {

    }

    /**
     * TODO: finish leftRotate method
     */
    private void leftRotate()
    {

    }

    /**
     * TODO: finish rightRotate method
     */
    private void rightRotate()
    {

    }

    /**
     * TODO: finish leftRightRotate method
     */
    private void leftRightRotate()
    {

    }

    /**
     * TODO: finish rightLeftRotate method
     */
    private void rightLeftRotate()
    {

    }

    /**
     * @param query the node to find the depth of
     * @return returns the depth of the node from the root of the tree
     */
    private int depth(Node query)
    {
        return depth(query, 0);
    }

    /**
     * Recursive helper for depth method that takes a node to determine the depth of
     * and the current depth and returns the depth of the node.
     * @param query the node to determine the depth of
     * @param depth the current calculated depth
     * @return returns the depth of the node
     */
    private int depth(Node query, int depth)
    {
        if (query.parent == null)
        {
            return depth;
        }

        return depth(query.parent, depth + 1);
    }

    /**
     * Finds the height of the binary search tree
     * @return returns the height of the tree
     */
    public int height()
    {
        return height(this.root, 0);
    }

    /**
     * Recursive helper that finds the height of the tree from a starting node
     * @param node node to start at
     * @param height current calculated height
     * @return returns the height of the tree
     */
    private int height(Node node, int height)
    {
        int leftHeight, rightHeight;

        if (node == null)
        {
            return height;
        }

        leftHeight  = height(node.leftChild, height + 1);
        rightHeight = height(node.rightChild, height + 1);

        return Math.max(leftHeight, rightHeight);
    }

    /**
     * Prints the tree contents in order from smallest to largest
     * @return String representing the binary tree
     */
    public String inOrder()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return inOrder(root, builder).delete(builder.length() - 2, builder.length()).toString().trim();
    }

    /**
     * Recursive helper for inOrder ToString
     * @param node node of the tree
     * @param builder string builder object
     * @return StringBuilder with tree data in order
     */
    private StringBuilder inOrder(Node node, StringBuilder builder)
    {
        if (node != null)
        {
            inOrder(node.leftChild, builder);

            builder.append(node.data).append(", ");

            inOrder(node.rightChild, builder);
        }

        return builder;
    }

    /**
     * Creates a postorder string representing the binary tree
     * @return String representing the binary tree
     */
    public String postorder()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return postorder(root, builder).delete(builder.length() - 2, builder.length()).toString().trim();
    }

    /**
     * Recursive helper for postorder ToString
     * @param node node of the tree
     * @param builder string builder object
     * @return StringBuilder with tree data in order
     */
    private StringBuilder postorder(Node node, StringBuilder builder)
    {
        if (node != null)
        {
            postorder(node.leftChild, builder);
            postorder(node.rightChild, builder);

            builder.append(node.data).append(", ");
        }

        return builder;
    }

    /**
     * Creates a preorder String representing the binary tree
     * @return String representing the binary tree
     */
    public String preorder()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return preorder(root, builder).delete(builder.length() - 2, builder.length()).toString().trim();
    }

    /**
     * Recursive helper for preorder ToString
     * @param node node of the tree
     * @param builder string builder object
     * @return StringBuilder with tree data in order
     */
    private StringBuilder preorder(Node node, StringBuilder builder)
    {
        if (node != null)
        {
            builder.append(node.data).append(", ");

            preorder(node.leftChild, builder);
            preorder(node.rightChild, builder);
        }

        return builder;
    }

    /**
     * Creates a String representing the binary tree by level
     * @return String representing the binary tree
     */
    public String levelOrder()
    {
        StringBuilder builder;

        builder = new StringBuilder();

        return levelOrder(this.root, builder).delete(builder.length() - 2, builder.length()).toString().trim();
    }

    /** TODO: Finish level order toString method
     * Recursive helper for levelOrder to String
     * @param node node of the tree to start at
     * @param builder string builder object
     * @return StringBuilder with tree data ordered by level
     */
    private StringBuilder levelOrder(Node node, StringBuilder builder)
    {
        return null;
    }

    /**
     * toString method - uses inOrder
     * @return String representing the binary tree
     */
    @Override
    public String toString()
    {
        return inOrder();
    }
}
