package test;

import main.BinarySearchTree;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest
{
    @Test
    @DisplayName("adds items to the right and rebalances")
    public void addItemsRight()
    {
        BinarySearchTree<Integer> test;

        test = new BinarySearchTree<>(1, 2, 3, 4, 5);

        // ints should appear sorted when traversed inOrder depth first
        assertEquals("1, 2, 3, 4, 5", test.inOrder());

        // ints by level to indicate if tree rotated properly on insertions
        assertEquals("2, 1, 4, 3, 5", test.levelOrder());

        // height of tree should indicate it was balanced as ints were added
        assertEquals(3, test.height());
    }

    @Test
    @DisplayName("adds items to the left and rebalances")
    public void addItemsLeft()
    {
        BinarySearchTree<Integer> test;

        test = new BinarySearchTree<>(5, 4, 3, 2, 1);

        // ints should appear sorted when traversed inOrder depth first
        assertEquals("1, 2, 3, 4, 5", test.inOrder());

        // ints by level to indicate if tree rotated properly on insertions
        assertEquals("4, 2, 5, 1, 3", test.levelOrder());

        // height of tree should indicate it was balanced as ints were added
        assertEquals(3, test.height());
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

    @Nested
    @DisplayName("tree traversal tests")
    class treeTraversal
    {
        @Test
        @DisplayName("returns items by level ordered from left to right after")
        public void levelOrder()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(5, 3, 7, 1, 4, 6, 8);

            assertEquals("5, 3, 7, 1, 4, 6, 8", test.levelOrder());
        }
    }

    @Nested
    @DisplayName("remove tests")
    class removeTests
    {
        @Test
        @DisplayName("removes node with 0 children")
        public void remove0()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(1, 2, 3, 4, 5);

            test.remove(3);

            // ints should appear sorted when traversed inOrder depth first
            assertEquals("1, 2, 4, 5", test.inOrder());

            // ints by level to indicate layout of tree
            assertEquals("2, 1, 4, 5", test.levelOrder());

            // height should reflect balance
            assertEquals(3, test.height());
        }

        @Test
        @DisplayName("removes node with 1 child")
        public void remove1()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(1, 2, 4, 5);

            test.remove(4);

            // ints should appear sorted when traversed inOrder depth first
            assertEquals("1, 2, 5", test.inOrder());

            // ints by level to indicate layout of tree
            assertEquals("2, 1, 5", test.levelOrder());

            // height should reflect balance
            assertEquals(2, test.height());
        }

        @Test
        @DisplayName("removes node with 2 chldren")
        public void remove2()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(1, 2, 3, 4, 5);

            test.remove(4);

            // ints should appear sorted when traversed inOrder depth first
            assertEquals("1, 2, 3, 5", test.inOrder());

            // ints by level to indicate layout of tree
            assertEquals("2, 1, 3, 5", test.levelOrder());

            // height should reflect balance
            assertEquals(3, test.height());
        }

        @Test
        @DisplayName("tree with multiple rotations")
        public void bigTree()
        {
            BinarySearchTree<Integer> test;

            test = new BinarySearchTree<>(1, 2, 3, 4, 5, 6, 11, 13, 12, 10, 9, 8, 7);

            // check everything was inserted correctly

            // ints should appear sorted when traversed inOrder depth first
            assertEquals("1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13", test.inOrder());

            // ints by level to indicate layout of tree (check rotations were performed correctly)
            assertEquals("6, 4, 11, 2, 5, 9, 12, 1, 3, 8, 10, 13, 7", test.levelOrder());

            // height of tree should indicate it was balanced as ints were added
            assertEquals(5, test.height());

            test.remove(13);

            // 13 was removed
            assertEquals("1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12", test.inOrder());

            // tree was rebalanced
            assertEquals("6, 4, 9, 2, 5, 8, 11, 1, 3, 7, 10, 12", test.levelOrder());
            assertEquals(4, test.height());

            test.remove(9);

            // 13 was removed
            assertEquals("1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12", test.inOrder());

            // tree was rebalanced
            assertEquals("6, 4, 8, 2, 5, 7, 11, 1, 3, 10, 12", test.levelOrder());
            assertEquals(4, test.height());
        }
    }
}
