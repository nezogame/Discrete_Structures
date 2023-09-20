package org.denys.hudymov;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DequeImplTest {
    Deque<Integer> deque = new DequeImpl();

    public void init() {
        deque.addToFront(3);
        deque.addToFront(4);
        deque.addToFront(5);
        deque.addToFront(6);
    }

    @Test
    public void addToFrontTest() {
        init();
        deque.print();
        assertEquals(6, deque.readFrontElement().orElseThrow());
        assertEquals(3, deque.readTailElement().orElseThrow());
        assertEquals(4, deque.countSize());
    }

    @Test
    public void addToTailTest() {
        deque.addToTail(2);
        deque.addToTail(1);
        deque.addToTail(0);
        deque.print();
        assertEquals(2, deque.readFrontElement().orElseThrow());
        assertEquals(0, deque.readTailElement().orElseThrow());
        assertEquals(3, deque.countSize());
    }

    @Test
    public void cleanTest() {
        init();


    }

    @Test
    public void deleteFrontTest() {
        init();
        deque.print();
        deque.deleteFront();
        deque.print();
        assertEquals(5, deque.readFrontElement().orElseThrow());
        assertEquals(3, deque.readTailElement().orElseThrow());
        assertEquals(3, deque.countSize());
    }

    @Test
    public void deleteTailTest() {
        init();
        deque.print();
        deque.deleteTail();
        deque.print();
        deque.addToFront(7);
        deque.deleteTail();
        deque.print();
        assertEquals(7, deque.readFrontElement().orElseThrow());
        assertEquals(5, deque.readTailElement().orElseThrow());
        assertEquals(3, deque.countSize());
    }

    @Test
    public void swapLastAndFirstTest(){
        init();
        deque.print();
        deque.swapLastAndFirst();
        deque.print();
        assertEquals(3, deque.readFrontElement().orElseThrow());
        assertEquals(6, deque.readTailElement().orElseThrow());
        assertEquals(4, deque.countSize());
    }

    @Test
    public void swapEmptyDeque(){
        deque.print();
        deque.swapLastAndFirst();
        assertEquals(Optional.ofNullable(null), deque.readFrontElement());
        assertEquals(Optional.ofNullable(null), deque.readTailElement());
        assertEquals(0, deque.countSize());
    }

    @Test
    public void clean(){
        init();
        deque.clean();
        assertEquals(Optional.ofNullable(null), deque.readFrontElement());
        assertEquals(Optional.ofNullable(null), deque.readTailElement());
        assertEquals(0, deque.countSize());
    }

    @Test
    public void reverse(){
        init();
        deque.addToTail(2);
        deque.print();
        deque.reverse();
        deque.print();
        assertEquals(2, deque.readFrontElement().orElseThrow());
        assertEquals(6, deque.readTailElement().orElseThrow());
        assertEquals(5, deque.countSize());
    }

    @Test
    public void reverseEmpty(){
        deque.print();
        deque.reverse();
        assertEquals(Optional.ofNullable(null), deque.readFrontElement());
        assertEquals(Optional.ofNullable(null), deque.readTailElement());
        assertEquals(0, deque.countSize());
    }

    @Test
    public void findFirstByValue(){
        init();
        deque.print();
        assertEquals(true, deque.isPresent(6));
        assertEquals(4, deque.countSize());
    }

    @Test
    public void findLastByValue(){
        init();
        deque.print();
        assertEquals(true, deque.isPresent(3));
        assertEquals(4, deque.countSize());
    }

    @Test
    public void findNotPresentByValue(){
        init();
        deque.print();
        assertEquals(false, deque.isPresent(9));
        assertEquals(4, deque.countSize());
    }

}