package main;

import java.util.Objects;
import java.util.Queue;
import java.util.LinkedList;

public class BinarySearchTree<Type extends Comparable<Type>>
{
    private class Node
    {
        Node leftChild;
        Node rightChild;
        Node parent;
        Type data;
        int  balanceFactor;
        int  height;

        /**
         * Constructor for Binary Search Tree Node
         * @param data the data to be stored in the Node
         */
        Node(Type data)
        {
            this.leftChild      = null;
            this.rightChild     = null;
            this.parent         = null;
            this.data           = data;
            this.balanceFactor  = 0;
            this.height         = 1;
        } // end of default constructor

        /**
         * Constructor for Binary Search Tree Node that also
         * specifies the node's parent
         * @param data the data to be stored in the Node
         * @param parent the parent Node of the Node being created
         */
        Node(Type data, Node parent)
        {
            this(data);
            this.parent = parent;
        } // end of constructor

        /**
         * Updates the Node's balance factor and height
         */
        public void updateStats()
        {
            int leftHeight, rightHeight;

            if (this.leftChild != null)
            {
                leftHeight = this.leftChild.height;
            }
            else
            {
                leftHeight = 0;
            }

            if (this.rightChild != null)
            {
                rightHeight = this.rightChild.height;
            }
            else
            {
                rightHeight = 0;
            }

            this.height        = Math.max(leftHeight, rightHeight) + 1;
            this.balanceFactor = rightHeight - leftHeight;
        } // end of updateNodeStats

        /**
         * A String representation of the node
         * @return returns the Node's data as a String
         */
        @Override
        public String toString()
        {
            return this.data.toString();
        } // end of Node toString
    } // end of private Node class

    // Root node pointer. Will be null for an empty tree.
    private Node root;

    /**
     * Creates an empty binary tree -- a null root pointer.
     */
    public BinarySearchTree()
    {
        this.root = null;
    } // end of constructor

    /**
     * Constructs a Binary search tree and inserts the
     * passed data into the tree
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
    } // end of Varargs constructor

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
            this.root = insert(this.root, data, null);
            return true;
        }
        catch (Exception caught)
        {
            return false;
        }
    } // end of insert

    /**
     * Recursive insert: given a node pointer, recur down and
     * insert the given data into the tree. Returns the new
     * node pointer.
     * @param node node to look at
     * @param data the data to insert
     * @param parent the parent of the node to look at
     * @return returns the new node if inserted, throws an exception if not
     */
    private Node insert(Node node, Type data, Node parent)
    {
        int compared;

        if (node == null)
        {
            return new Node(data, parent);
        }

        compared = data.compareTo(node.data);

        if (compared > 0)
        {
            node.rightChild = insert(node.rightChild, data, node);
        }
        else if (compared < 0)
        {
            node.leftChild = insert(node.leftChild, data, node);
        }
        else
        {
            throw new IllegalArgumentException("duplicate data not allowed in tree");
        }

        return balance(node);
    } // end of insert helper

    /**
     * Balances the passed Node by determining the necessary rotations &
     * calling the appropriate rotation methods
     * @param node the Node to balance
     * @return returns the balanced Node
     */
    private Node balance(Node node)
    {
        node.updateStats();

        // node is left-heavy
        if (node.balanceFactor < -1)
        {
            // check if left child is right-heavy
            // if so, left-right rotate
            if (node.leftChild.balanceFactor > 0)
            {
                node.leftChild = leftRotate(node.leftChild);
            }

            return rightRotate(node);
        }

        // node is right-heavy
        if (node.balanceFactor > 1)
        {
            // check if right child is left-heavy
            // if so, right-left rotate
            if(node.rightChild.balanceFactor < 0)
            {
                node.rightChild = rightRotate(node.rightChild);
            }

            return leftRotate(node);
        }

        return node;
    } // end of balance

    /**
     * Performs a left rotation on the specified node
     * @param node the Node to rotate at
     * @return returns the new root of the subtree, be sure to attach
     * returned node to the correct spot and update the returned node's
     * parent
     */
    private Node leftRotate(Node node)
    {
        Node newRoot, orphan, parentOfNewRoot;

        newRoot            = node   .rightChild;
        orphan             = newRoot.leftChild;
        parentOfNewRoot    = node   .parent;

        // rotate
        newRoot.leftChild  = node;
        node   .parent     = newRoot;
        newRoot.parent     = parentOfNewRoot;

        // re-attach orphan
        node   .rightChild = orphan;

        if (orphan != null)
        {
            orphan.parent  = node;
        }

        // update balance factors
        node   .updateStats();
        newRoot.updateStats();

        return newRoot;
    } // end of leftRotate

    /**
     * Performs a right rotation on the specified node
     * @param node the Node to rotate at
     * @return returns the new root of the subtree, be sure to attach
     * returned node to the correct spot and update the returned node's
     * parent
     */
    private Node rightRotate(Node node)
    {
        Node newRoot, orphan, parentOfNewRoot;

        newRoot            = node   .leftChild;
        orphan             = newRoot.rightChild;
        parentOfNewRoot    = node   .parent;

        // rotate
        newRoot.rightChild = node;
        node   .parent     = newRoot;
        newRoot.parent     = parentOfNewRoot;

        // re-attach orphan
        node   .leftChild  = orphan;

        if (orphan != null)
        {
            orphan.parent  = node;
        }

        // update balance factors
        node   .updateStats();
        newRoot.updateStats();

        return newRoot;
    } // end of rightRotate

    /**
     * Look up data in binary tree
     * @param data the data to look for
     * @return returns the node with the matching data, null if not found
     */
    private Node find(Type data)
    {
        return find(this.root, data, 1);
    } // end of find

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
    } // end of find helper

    /**
     * Removes the specified data from the tree if found in the tree
     * @param data the data to be removed (if found)
     * @return returns true if successful, false if not
     */
    public boolean remove(Type data)
    {
        Node toRemove;

        toRemove = find(data);

        if (toRemove == null)
        {
            return false;   // can't remove something that doesn't exist
        }

        rebalanceTree(remove(toRemove)); // rebalances the tree from the point of deletion up to root

        return true;
    } // end of remove

    /**
     * Private helper that removes the Node from the tree
     * @param toRemove Node to remove
     * @return returns the parent of the deleted node
     */
    private Node remove(Node toRemove)
    {
        Node parent, child, toSwap;

        parent = toRemove.parent;

        if (toRemove.leftChild == null || toRemove.rightChild == null)  // toRemove has 1 or no child
        {
            // determine if a left or right child exist
            if (toRemove.leftChild != null)
            {
                // if leftChild exists, child becomes leftChild
                child = toRemove.leftChild;
            }
            else
            {
                // child becomes rightChild if exists, or becomes null if no children
                child = toRemove.rightChild;
            }

            // remove node
            if (parent == null)
            {
                this.root = child;
            }
            else if (parent.leftChild == toRemove)
            {
                parent.leftChild = child;
            }
            else
            {
                parent.rightChild = child;
            }

            return parent;
        }
        else // toRemove has 2 children
        {
            toSwap        = getNodeToSwap(toRemove);
            toRemove.data = toSwap.data;

            return remove(toSwap); // remove the swapped Node instead
        }
    } // end of remove helper

    /**
     * Private helper method for remove that finds the node to swap with
     * the passed node. This is used in the case that the node being
     * removed has 2 children.
     * @param node the Node to be removed
     * @return returns the smaller, but closest in value Node
     */
    private Node getNodeToSwap(Node node)
    {
        Node curr;

        curr = node.leftChild;

        while (curr.rightChild != null)
        {
            curr = curr.rightChild;
        }

        return curr;
    } // end of getNodeToSwap

    /**
     * Rebalances the tree from a starting node up to the root of the tree.
     * @param node the starting point for the rebalance
     */
    private void rebalanceTree(Node node)
    {
        Node parent, newNode;

        if (node == null)
        {
            return;
        }

        node.updateStats();

        parent  = node.parent;
        newNode = balance(node);

        if (parent == null)
        {
            this.root = newNode;
        }
        else if (parent.leftChild == node)
        {
            parent.leftChild = newNode;
        }
        else
        {
            parent.rightChild = newNode;
        }

        rebalanceTree(parent);
    } // end of rebalanceTree

    /**
     * @param query the node to find the depth of
     * @return returns the depth of the node from the root of the tree
     */
    private int depth(Node query)
    {
        return depth(query, 0);
    } // end of depth

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
    } // end of depth helper

    /**
     * Finds the height of the binary search tree
     * @return returns the height of the tree
     */
    public int height()
    {
        return height(this.root, 0);
    } // end of height (entire tree)

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
    } // end of height helper

    /**
     * Prints the tree contents in order from smallest to largest
     * @return String representing the binary tree
     */
    public String inOrder()
    {
        StringBuilder builder;

        if (this.root == null)
        {
            return "";
        }

        builder = new StringBuilder();

        return inOrder(root, builder).delete(builder.length() - 2, builder.length()).toString().trim();
    } // end of inOrder

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
    } // end of inOrder helper

    /**
     * Creates a postorder string representing the binary tree
     * @return String representing the binary tree
     */
    public String postorder()
    {
        StringBuilder builder;

        if (this.root == null)
        {
            return "";
        }

        builder = new StringBuilder();

        return postorder(root, builder).delete(builder.length() - 2, builder.length()).toString().trim();
    } // end of postorder

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
    } // end of postorder helper

    /**
     * Creates a preorder String representing the binary tree
     * @return String representing the binary tree
     */
    public String preorder()
    {
        StringBuilder builder;

        if (this.root == null)
        {
            return "";
        }

        builder = new StringBuilder();

        return preorder(root, builder).delete(builder.length() - 2, builder.length()).toString().trim();
    } // end of preorder

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
    } // end of preorder helper

    /**
     * Creates a String representing the binary tree with elements ordered
     * by level and from left to right
     * @return String representing the binary tree
     */
    public String levelOrder()
    {
        StringBuilder builder;
        Queue<Node>   q;
        Node          curr;

        if (this.root == null)
        {
            return "";
        }

        q       = new LinkedList<>();
        builder = new StringBuilder();

        q.add(this.root);

        while (!q.isEmpty())
        {
            curr = q.remove();

            if (curr.leftChild != null)
            {
                q.add(curr.leftChild);
            }
            if (curr.rightChild != null)
            {
                q.add(curr.rightChild);
            }

            builder.append(curr.data).append(", ");
        }

        return builder.delete(builder.length() - 2, builder.length()).toString().trim();
    } // end of levelOrder

    /**
     * toString method - uses inOrder
     * @return String representing the binary tree
     */
    @Override
    public String toString()
    {
        return inOrder();
    } // end of toString
} // end of BinarySearchTree class
