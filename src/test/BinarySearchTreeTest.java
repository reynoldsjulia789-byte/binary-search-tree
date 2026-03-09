package test;

import main.BinarySearchTree;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinarySearchTreeTest
{
    @Test
    @DisplayName("adds all items")
    public void addItems()
    {
        BinarySearchTree<Integer> test;

        test = new BinarySearchTree<>(1, 2, 3);

        assertEquals("1 2 3", test.inorderToString());
    }

    @Test
    @DisplayName("sorts ints correctly")
    public void sortsInts()
    {
        BinarySearchTree<Integer> test;

        test = new BinarySearchTree<>(3, 5, 2, 1, 4);

        assertEquals("1 2 3 4 5", test.inorderToString());
    }

    @Test
    @DisplayName("sorts Strings correctly")
    public void sortsStrings()
    {
        BinarySearchTree<String> test;

        test = new BinarySearchTree<>("orange", "apple", "pear", "banana", "raspberry");

        assertEquals("apple banana orange pear raspberry", test.inorderToString());
    }

}
