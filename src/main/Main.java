package main;

public class Main
{
    public static void main(String[] args)
    {
        BinarySearchTree<Integer> intTree;

        intTree = new BinarySearchTree<>(5, 3, 9, 1, 4, 6, 2);

        System.out.println("preorder:   " + intTree.preorderToString());
        System.out.println("inorder:    " + intTree.inorderToString());
        System.out.println("postorder:  " + intTree.postorderToString());
    }
}
