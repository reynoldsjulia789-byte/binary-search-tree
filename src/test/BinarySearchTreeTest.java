package test;

import main.BinarySearchTree;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest
{
    @Test
    @DisplayName("adds items on right side of tree")
    public void addItemsRight()
    {
        BinarySearchTree<Integer> test;

        test = new BinarySearchTree<>(1, 2, 3, 4, 5);

        assertEquals("1, 2, 3, 4, 5", test.inOrder());
    }

    @Test
    @DisplayName("adds items on left side of tree")
    public void addItemsLeft()
    {
        BinarySearchTree<Integer> test;

        test = new BinarySearchTree<>(5, 4, 3, 2, 1);

        assertEquals("1, 2, 3, 4, 5", test.inOrder());
    }

    @Nested
    @DisplayName("sort tests")
    class sortTests
    {
        @Test
        @DisplayName("sorts ints correctly")
        public void sortsInts()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(3, 5, 2, 1, 4);

            assertEquals("1, 2, 3, 4, 5", test.inOrder());
        }

        @Test
        @DisplayName("sorts Strings correctly")
        public void sortsStrings()
        {
            BinarySearchTree<String> test;

            test = new BinarySearchTree<>("orange", "apple", "pear", "banana", "raspberry");

            assertEquals("apple, banana, orange, pear, raspberry", test.inOrder());
        }
    }
}
