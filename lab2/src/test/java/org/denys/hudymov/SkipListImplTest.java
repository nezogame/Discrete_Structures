package org.denys.hudymov;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkipListImplTest {
    SkipListImpl list = new SkipListImpl();

    @Test
    public void AddNodeToHead() {
        list.add(1);
        assertEquals(1, list.getHead().getKey());
    }

    @Test
    public void AddSixNode() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        /*Node<Integer> first = Node.<Integer>builder().key(1).value(1).build();
        Node<Integer> second = Node.<Integer>builder().key(2).value(2).build();
        Node<Integer> third = Node.<Integer>builder().key(3).value(3).build();
        first.setNext(second);
        second.setNext(third);
        assertEquals(first,list.getHead());*/
        list.print();
    }

    @Test
    public void AddTreeNode() {
        list.add(2);
        list.add(1);
        list.add(3);

        /*Node<Integer> first = Node.<Integer>builder().key(1).value(1).build();
        Node<Integer> second = Node.<Integer>builder().key(2).value(2).build();
        Node<Integer> third = Node.<Integer>builder().key(3).value(3).build();
        first.setNext(second);
        second.setNext(third);
        assertEquals(first,list.getHead());*/
        list.print();
    }

    @Test
    public void findFirstNode() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        var node = list.find(1);

        assertEquals(1, node.get().getKey());
    }

    @Test
    public void findUnexcitingNode() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        var node = list.find(0);

        assertEquals(Optional.empty(), node);
    }

    @Test
    public void findLast() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        var node = list.find(6);

        assertEquals(6, node.get().getKey());
    }

    @Test
    public void deleteFirst() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.print();
        System.out.println("--------");
        list.delete(1);
        list.print();
        assertEquals(2, list.getHead().getKey());
    }

    @Test
    public void addSecond() {
        list.limit(5);
        list.add(9);
        list.add(10);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        System.out.println("<Before ONE>--------");
        list.print();
        list.add(1);
        System.out.println("<AFTER ONE>--------");
        list.print();
        list.delete(1);

        System.out.println("--------");
        list.print();
    }

    @Test
    public void deleteUnexcited() {
        list.add(1);
        list.add(2);
        list.add(3);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            list.delete(0);
        });
        var actualMessage = exception.getMessage();
        var expectedMessage = "Key not found in the Skip List";
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void deleteLast() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.print();
        list.delete(3);
        assertEquals(1, list.getHead().getKey());
        System.out.println("-------");
        list.print();
    }

    @Test
    public void size() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(5);
        list.add(8);
        list.add(4);
        list.add(9);
        list.add(7);
        var s = list.size();
        assertEquals(9, s);
        list.print();
    }

    @Test
    public void levelSize() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(5);
        list.add(8);
        list.add(4);
        list.add(9);
        list.add(7);
        list.print();

        var s = list.levelSize(0);
        assertEquals(9, s);
    }

    @Test
    public void delete() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(5);
        list.add(8);
        list.add(4);
        list.add(9);
        list.add(7);
        list.print();

        list.clean();
        assertNull(list.getHead());
    }

    @Test
    public void copy() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(5);
        list.add(8);
        list.add(4);
        list.add(9);
        list.add(7);
        System.out.println("<Original>");
        list.print();

        var copy = list.copy();
        System.out.println("<Copy>");
        copy.delete(1);
        copy.print();

        System.out.println("<Original>");
        list.print();
    }
}