package main;

public class Main
{
    public static void main(String[] args)
    {
        BinarySearchTree<Integer> intTree;

        System.out.println("Binary Search Tree Demo\r\n");

        System.out.println("numbers inserted:\t5 3 9 1 4 6 2\r\n");

        intTree = new BinarySearchTree<>(5, 3, 9, 1, 4, 6, 2);

        System.out.println("preorder:\t" + intTree.preorderToString());
        System.out.println("inorder:\t" + intTree.inorderToString());
        System.out.println("postorder:\t" + intTree.postorderToString());
    }
}
