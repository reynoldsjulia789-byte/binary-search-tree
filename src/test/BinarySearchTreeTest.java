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

    @Nested
    @DisplayName("tree height tests")
    class heightTests
    {
        @Test
        @DisplayName("returns correct height of tree")
        public void height()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(5, 1, 3, 2, 0, 8, 6, 9, 7);

            assertEquals(4, test.height());
        }

        @Test
        @DisplayName("returns height of 0 for empty tree")
        public void heightEmptyTree()
        {
            BinarySearchTree<Integer> test = new BinarySearchTree<>();

            assertEquals(0, test.height());
        }

        @Test
        @DisplayName("returns height of 1 for tree with root only")
        public void heightRootOnlyTree()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(1);

            assertEquals(1, test.height());
        }
    }
}
