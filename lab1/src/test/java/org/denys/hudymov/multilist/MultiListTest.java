package org.denys.hudymov.multilist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiListTest {
    MultiList tree = new MultiList();

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
        tree.print();
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
        tree.print();
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
        tree.print();
    }
}