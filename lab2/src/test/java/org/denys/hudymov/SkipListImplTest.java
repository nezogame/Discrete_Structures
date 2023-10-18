package org.denys.hudymov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SkipListImplTest {
    SkipListImpl<Integer> list= new SkipListImpl<>();

    @Test
    public void AddNodeToHead(){
        list.add(1);
        assertEquals(1,list.getHead().getKey());
    }

    @Test
    public void AddTreeNode(){
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
}