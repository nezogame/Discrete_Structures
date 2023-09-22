package org.denys.hudymov.multilist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiListTest {
    MultiList tree = new MultiList();

    public void init() {
        tree.addElementAt(0, 1);
        tree.addElementAt(1, 23);
        tree.addChildAt(1, 13);
        tree.addChildAt(3, 12);
        tree.addElementAt(4, 22);
        tree.addChildAt(5, 28);
        tree.addElementAt(6, 30);
        tree.addChildAt(2, 8);
        tree.addElementAt(3, 43);
        tree.addElementAt(4, 25);
        tree.addChildAt(7, 18);
        tree.addChildAt(9, 31);
    }

    @Test
    public void addAtFirstLayerThreeNode() {
        Node<Integer> root = Node.<Integer>builder()
                .value(3)
                .build();
        Node<Integer> second = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> third = Node.<Integer>builder()
                .value(2)
                .build();
        root.setNext(second);
        second.setNext(third);
        tree.addElementAt(0, 3);
        tree.addElementAt(1, 1);
        tree.addElementAt(2, 2);
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void addTwoChildToRoot() {
        Node<Integer> root = Node.<Integer>builder()
                .value(3)
                .build();
        Node<Integer> second = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> third = Node.<Integer>builder()
                .value(2)
                .build();
        root.setChild(second);
        second.setChild(third);
        tree.addElementAt(0, 3);
        tree.addChildAt(1, 1);
        tree.addChildAt(2, 2);
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void addThreeElement() {
        Node<Integer> root = Node.<Integer>builder()
                .value(3)
                .build();
        Node<Integer> second = Node.<Integer>builder()
                .value(2)
                .build();
        Node<Integer> third = Node.<Integer>builder()
                .value(1)
                .build();
        root.setChild(second);
        second.setNext(third);
        tree.addElementAt(0, 3);
        tree.addChildAt(1, 1);
        tree.addChildAt(1, 2);
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void testAddNextAndChildElements() {
        init();
        Node<Integer> root = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> node23 = Node.<Integer>builder()
                .value(23)
                .build();
        Node<Integer> node13 = Node.<Integer>builder()
                .value(13)
                .build();
        Node<Integer> node8 = Node.<Integer>builder()
                .value(8)
                .build();
        Node<Integer> node43 = Node.<Integer>builder()
                .value(43)
                .build();
        Node<Integer> node25 = Node.<Integer>builder()
                .value(25)
                .build();
        Node<Integer> node12 = Node.<Integer>builder()
                .value(12)
                .build();
        Node<Integer> node22 = Node.<Integer>builder()
                .value(22)
                .build();
        Node<Integer> node18 = Node.<Integer>builder()
                .value(18)
                .build();
        Node<Integer> node31 = Node.<Integer>builder()
                .value(31)
                .build();
        Node<Integer> node28 = Node.<Integer>builder()
                .value(28)
                .build();
        Node<Integer> node30 = Node.<Integer>builder()
                .value(30)
                .build();
        root.setNext(node23);
        root.setChild(node13);
        node13.setChild(node8);
        node8.setNext(node43);
        node43.setNext(node25);
        node23.setChild(node12);
        node12.setNext(node22);
        node12.setChild(node18);
        node22.setChild(node31);
        node31.setNext(node28);
        node28.setNext(node30);
        assertEquals(12, tree.countElements());
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void testCountTree() {
        tree.addElementAt(0, 1);
        tree.addElementAt(1, 23);
        tree.addChildAt(1, 13);
        assertEquals(3, tree.countElements());
    }

    @Test
    public void testCountEmptyTree() {
        assertEquals(0, tree.countElements());
    }

    @Test
    public void testCountSizeOfLayer() {
        init();
        assertEquals(2, tree.countSizeOfLayer(0));
        assertEquals(3, tree.countSizeOfLayer(1));
        assertEquals(7, tree.countSizeOfLayer(2));
    }

    @Test
    public void testThirdLayerDeletion() {
        init();
        Node<Integer> root = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> node23 = Node.<Integer>builder()
                .value(23)
                .build();
        Node<Integer> node13 = Node.<Integer>builder()
                .value(13)
                .build();
        Node<Integer> node12 = Node.<Integer>builder()
                .value(12)
                .build();
        Node<Integer> node22 = Node.<Integer>builder()
                .value(22)
                .build();
        root.setNext(node23);
        root.setChild(node13);
        node23.setChild(node12);
        node12.setNext(node22);
        tree.deleteLayerWithChildren(2);
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void testSecondLayerDeletion() {
        init();
        Node<Integer> root = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> node23 = Node.<Integer>builder()
                .value(23)
                .build();

        root.setNext(node23);
        tree.deleteLayerWithChildren(1);
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void testFirstLayerDeletion() {
        init();
        tree.deleteLayerWithChildren(0);
        assertNull(tree.getRoot());
    }

    @Test
    public void testLayerDeletionInEmptyTree() {
        tree.deleteLayerWithChildren(1);
        assertNull(tree.getRoot());
    }

    @Test
    public void testNotExistingLayerDeletion() {
        init();
        Node<Integer> root = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> node23 = Node.<Integer>builder()
                .value(23)
                .build();
        Node<Integer> node13 = Node.<Integer>builder()
                .value(13)
                .build();
        Node<Integer> node8 = Node.<Integer>builder()
                .value(8)
                .build();
        Node<Integer> node43 = Node.<Integer>builder()
                .value(43)
                .build();
        Node<Integer> node25 = Node.<Integer>builder()
                .value(25)
                .build();
        Node<Integer> node12 = Node.<Integer>builder()
                .value(12)
                .build();
        Node<Integer> node22 = Node.<Integer>builder()
                .value(22)
                .build();
        Node<Integer> node18 = Node.<Integer>builder()
                .value(18)
                .build();
        Node<Integer> node31 = Node.<Integer>builder()
                .value(31)
                .build();
        Node<Integer> node28 = Node.<Integer>builder()
                .value(28)
                .build();
        Node<Integer> node30 = Node.<Integer>builder()
                .value(30)
                .build();
        root.setNext(node23);
        root.setChild(node13);
        node13.setChild(node8);
        node8.setNext(node43);
        node43.setNext(node25);
        node23.setChild(node12);
        node12.setNext(node22);
        node12.setChild(node18);
        node22.setChild(node31);
        node31.setNext(node28);
        node28.setNext(node30);
        tree.deleteLayerWithChildren(4);
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void testDeleteNextBranch() {
        init();
        Node<Integer> root = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> node23 = Node.<Integer>builder()
                .value(23)
                .build();
        Node<Integer> node13 = Node.<Integer>builder()
                .value(13)
                .build();
        Node<Integer> node12 = Node.<Integer>builder()
                .value(12)
                .build();
        Node<Integer> node8 = Node.<Integer>builder()
                .value(8)
                .build();
        Node<Integer> node43 = Node.<Integer>builder()
                .value(43)
                .build();
        Node<Integer> node25 = Node.<Integer>builder()
                .value(25)
                .build();
        Node<Integer> node18 = Node.<Integer>builder()
                .value(18)
                .build();
        root.setNext(node23);
        root.setChild(node13);
        node23.setChild(node12);
        node12.setChild(node18);
        node13.setChild(node8);
        node8.setNext(node43);
        node43.setNext(node25);
        tree.deleteBranch(6, false);
        assertEquals(root, tree.getRoot());
    }

    @Test
    public void testDeleteChildBranch() {
        init();
        Node<Integer> root = Node.<Integer>builder()
                .value(1)
                .build();
        Node<Integer> node23 = Node.<Integer>builder()
                .value(23)
                .build();
        Node<Integer> node12 = Node.<Integer>builder()
                .value(12)
                .build();
        Node<Integer> node22 = Node.<Integer>builder()
                .value(22)
                .build();
        Node<Integer> node18 = Node.<Integer>builder()
                .value(18)
                .build();
        Node<Integer> node31 = Node.<Integer>builder()
                .value(31)
                .build();
        Node<Integer> node28 = Node.<Integer>builder()
                .value(28)
                .build();
        Node<Integer> node30 = Node.<Integer>builder()
                .value(30)
                .build();
        root.setNext(node23);
        node23.setChild(node12);
        node12.setNext(node22);
        node12.setChild(node18);
        node22.setChild(node31);
        node31.setNext(node28);
        node28.setNext(node30);
        tree.deleteBranch(0, true);
        assertEquals(root, tree.getRoot());
    }

    @Test()
    public void testDeleteBranchEmptyTree() {
        init();
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            tree.deleteBranch(12, true);
        });
        var actualMessage = exception.getMessage();
        var expectedMessage = "This index {12} doesn't exist. Maximum index is {11}";
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeleteRootBranch() {
        init();
        tree.deleteBranch(-1, true);
        assertNull(tree.getRoot());
    }

    @Test
    public void testClen() {
        init();
        tree.clean();
        assertNull(tree.getRoot());
    }

    @Test
    public void testCopy() {
        init();
        GenericTree<Integer> treeCopy = tree.copy();
        assertEquals(treeCopy, tree);
    }
}