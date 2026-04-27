package main;

import java.util.Queue;
import java.util.LinkedList;

public class BinarySearchTree<Type extends Comparable<Type>>
{
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
     * Finds the height of the binary search tree
     * @return returns the height of the tree
     */
    public int height()
    {
        if (this.root == null)
        {
            return 0;
        }

        return this.root.height;
    } // end of height (entire tree)

    /**
     * Inserts the given data into the binary tree.
     * Duplicate data not allowed. Null data not allowed.
     * @param data the data to insert into the tree
     * @return true if successful, false if not
     */
    public boolean insert(Type data)
    {
        if (data == null)
        {
            return false;
        }

        try
        {
            this.root = insert(this.root, data, null);
            return true;
        }
        catch (IllegalArgumentException caught)
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
     * @throws IllegalArgumentException if passed duplicate data
     */
    private Node insert(Node node, Type data, Node parent) throws IllegalArgumentException
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

        return node.balance();
    } // end of insert helper

    /**
     * Returns the location (depth of the Node with the matching data)
     * in the tree if found, -1 if not
     * @param data the data to search for
     * @return returns the location of the data in the tree or -1 if not found
     * @throws IllegalArgumentException if the data to look up is null
     */
    public int lookUp(Type data) throws IllegalArgumentException
    {
        Node match;

        if (data == null)
        {
            throw new IllegalArgumentException("can't look up null data. null data not allowed in tree");
        }

        match = find(data);

        if (match == null)
        {
            return -1;
        }
        return match.depth();
    }

    /**
     * Searches for the Node that holds the specified data.
     * @param data the data to search for
     * @return returns the Node with the matching data, or null if not found.
     * @throws IllegalArgumentException if the data to find is null
     */
    private Node find(Type data) throws IllegalArgumentException
    {
        if (data == null)
        {
            throw new IllegalArgumentException("can't look up null data. null data not allowed in tree");
        }

        return find(this.root, data);
    } // end of find

    /**
     * Recursive find method. Recurs down the tree from this Node searching for the given data.
     * @param node the node to search
     * @param data data to search for
     * @return returns the node with the matching data, null if not found
     */
    private Node find(Node node, Type data)
    {
        int comparedData;

        if (node == null)
        {
            return null;
        }

        comparedData = data.compareTo(node.data);

        if (comparedData == 0)
        {
            return node;
        }
        else if (comparedData < 0) // data is less than the data in the node
        {
            return find(node.leftChild, data); // look on the leftChild side of the tree
        }
        else // data is greater than the data in the node
        {
            return find(node.rightChild, data); // look on the rightChild side of the tree
        }
    } // end of find helper

    /**
     * Removes the specified data from the tree if found in the tree
     * @param data the data to be removed (if found)
     * @return returns the removed data if successful, null if not
     */
    public Type remove(Type data)
    {
        Node toRemove;

        if (this.root == null)
        {
            return null;
        }

        toRemove = find(data);

        if (toRemove == null)
        {
            return null;   // can't remove something that doesn't exist
        }

        rebalanceTree(remove(toRemove)); // rebalances the tree from the point of deletion up to root

        return toRemove.data;
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

        if ((toRemove.leftChild != null) && (toRemove.rightChild != null)) // toRemove has 2 children
        {
            toSwap        = getNodeToSwap(toRemove);
            toRemove.data = toSwap.data;

            return remove(toSwap); // remove the swapped Node instead
        }

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

        if (child != null)
        {
            child.parent = parent;
        }

        return parent;
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
        newNode = node.balance();

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

        return inOrder(root, builder)
                .delete(builder.length() - 2, builder.length()) // remove the extra ", "
                .toString();
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

        return postorder(root, builder)
                .delete(builder.length() - 2, builder.length())  // remove the extra ", "
                .toString();
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

        return preorder(root, builder)
                .delete(builder.length() - 2, builder.length())  // remove the extra ", "
                .toString();
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
        Queue<Node>   queue;
        Node          curr;

        if (this.root == null)
        {
            return "";
        }

        queue       = new LinkedList<>();
        builder = new StringBuilder();

        queue.add(this.root);

        while (!queue.isEmpty())
        {
            curr = queue.remove();

            if (curr.leftChild != null)
            {
                queue.add(curr.leftChild);
            }

            if (curr.rightChild != null)
            {
                queue.add(curr.rightChild);
            }

            builder.append(curr.data).append(", ");
        }

        return builder
                .delete(builder.length() - 2, builder.length()) // remove the extra ", "
                .toString();
    } // end of levelOrder

    /**
     * Returns the inOrder traversal of the tree, the levelOrder traversal of the tree,
     * and the height of the tree
     * @return String representing the binary tree
     */
    @Override
    public String toString()
    {
        return "inOrder: [" + inOrder() + "],  levelOrder: [" + levelOrder() + "],  height: " + height();
    } // end of toString

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
         * Calculates the distance of this node from the root of the tree
         * @return returns the depth of the node from the root of the tree
         */
        private int depth()
        {
            Node curr;
            int  result;

            result = 0;

            for (curr = this; curr.parent != null; curr = curr.parent)
            {
                result++;
            }

            return result;
        } // end of depth

        /**
         * Balances this Node by determining the necessary rotations &
         * calling the appropriate rotation methods
         * @return returns the balanced Node
         */
        private Node balance()
        {
            this.updateStats();

            // node is left-heavy
            if (this.balanceFactor < -1)
            {
                // check if left child is right-heavy
                // if so, left-right rotate
                if (this.leftChild != null && this.leftChild.balanceFactor > 0)
                {
                    this.leftChild = this.leftChild.leftRotate();
                }

                return rightRotate();
            }

            // node is right-heavy
            if (this.balanceFactor > 1)
            {
                // check if right child is left-heavy
                // if so, right-left rotate
                if (this.rightChild != null && this.rightChild.balanceFactor < 0)
                {
                    this.rightChild = this.rightChild.rightRotate();
                }

                return leftRotate();
            }

            return this;
        } // end of balance

        /**
         * Performs a left rotation on this Node
         * @return returns the new root of the subtree, caller must attach
         * returned node to the correct spot
         */
        private Node leftRotate()
        {
            Node newRoot, orphan, parentOfNewRoot;

            newRoot            = this   .rightChild;
            orphan             = newRoot.leftChild;
            parentOfNewRoot    = this   .parent;

            // rotate
            newRoot.leftChild  = this;
            this   .parent     = newRoot;
            newRoot.parent     = parentOfNewRoot;

            // re-attach orphan
            this   .rightChild = orphan;

            if (orphan != null)
            {
                orphan.parent  = this;
            }

            // update balance factors
            this   .updateStats();
            newRoot.updateStats();

            return newRoot;
        } // end of leftRotate

        /**
         * Performs a right rotation on this Node
         * @return returns the new root of the subtree, caller must attach
         * returned node to the correct spot
         */
        private Node rightRotate()
        {
            Node newRoot, orphan, parentOfNewRoot;

            newRoot            = this   .leftChild;
            orphan             = newRoot.rightChild;
            parentOfNewRoot    = this   .parent;

            // rotate
            newRoot.rightChild = this;
            this   .parent     = newRoot;
            newRoot.parent     = parentOfNewRoot;

            // re-attach orphan
            this   .leftChild  = orphan;

            if (orphan != null)
            {
                orphan.parent  = this;
            }

            // update balance factors
            this   .updateStats();
            newRoot.updateStats();

            return newRoot;
        } // end of rightRotate

        /**
         * A String representation of the node
         * @return returns the Node's data, the balance factor, the height,
         * the depth, and the number of children of the node
         */
        @Override
        public String toString()
        {
            int totalChildren;

            if (this.leftChild == null && this.rightChild == null)
            {
                totalChildren = 0;
            }
            else if (this.leftChild != null && this.rightChild != null)
            {
                totalChildren = 2;
            }
            else
            {
                totalChildren = 1;
            }

            return "data: " + this.data.toString() +
                    ",  balance: " + this.balanceFactor +
                    ",  height: " + this.height +
                    ",  depth: " + depth() +
                    ",  children: " + totalChildren;
        } // end of Node toString
    } // end of private Node class
} // end of BinarySearchTree class
