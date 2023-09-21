package org.denys.hudymov.deque;

import lombok.*;

import java.util.Optional;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DequeImpl<T> implements Deque<T> {
    private Node<T> front;
    private Node<T> tail;

    @Override
    public void addToFront(T vale) {
        Node<T> node = Node.<T>builder()
                .value(vale)
                .build();
        if (isEmpty()) {
            addFirstValue(node);
        } else {
            Node<T> prevHead = getFront();
            front.setPrev(node);
            setFront(node);
            getFront().setNext(prevHead);
        }
    }

    @Override
    public void addToTail(T vale) {
        Node<T> node = Node.<T>builder()
                .value(vale)
                .build();
        if (isEmpty()) {
            addFirstValue(node);
        } else {
            Node<T> prevTail = getTail();
            node.setPrev(getTail());
            prevTail.setNext(node);
            setTail(node);
        }
    }

    @Override
    public void deleteFront() {
        if (isEmpty()) {
            System.out.println("Deque already clean");
        } else if (countSize() == 1) {
            front = null;
            tail = null;
        } else {
            front = front.getNext();
            front.setPrev(null);
        }
    }

    @Override
    public void deleteTail() {
        if (isEmpty()) {
            System.out.println("Deque already clean");
        } else if (countSize() == 1) {
            front = null;
            tail = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
    }

    @Override
    public Optional<T> readFrontElement() {
        if (front == null) {
            return Optional.empty();
        }
        return Optional.of(front.getValue());
    }

    @Override
    public Optional<T> readTailElement() {
        if (tail == null) {
            return Optional.empty();
        }
        return Optional.of(tail.getValue());
    }

    @Override
    public void swapLastAndFirst() {
        Node<T> node = getTail();
        if (!isEmpty()) {
            Node<T> beforeLastNode = getTail().getPrev();
            beforeLastNode.setNext(null);
            node.setNext(getFront().getNext());
            setTail(getFront());
            beforeLastNode.setNext(getTail());
            setFront(node);
            getTail().setNext(null);
        }
    }

    @Override
    public boolean isEmpty() {
        return countSize() == 0;
    }

    @Override
    public int countSize() {
        int size = 0;
        Optional<Node<T>> next = Optional.ofNullable(getFront());
        if (next.isEmpty()) {
            return size;
        }
        while (next.isPresent()) {
            size++;
            next = Optional.ofNullable(next.get().getNext());
        }
        return size;
    }

    @Override
    public void reverse() {
        if (isEmpty()) {
            return;
        }
        setTail(recursiveSwap(getFront()));
        getTail().setNext(null);
    }

    @Override
    public boolean isPresent(T value) {
        var i = 0;

        Node<T> node = getFront();
        while (countSize() != i) {
            if (node.getValue() == value) {
                return true;
            }
            node = node.getNext();
            i++;
        }
        return false;
    }

    @Override
    public void clean() {
        while (countSize() != 0) {
            deleteFront();
        }
        setTail(null);
    }

    @Override
    public void print() {
        Optional<Node<T>> next = Optional.ofNullable(getFront());
        System.out.print("[ ");
        if (!(next.isPresent())) {
            System.out.print("Deque is empty");
        }
        while (next.isPresent()) {
            System.out.print(next.get().getValue() + " ");
            next = Optional.ofNullable(next.get().getNext());
        }
        System.out.println("]");
    }

    private void addFirstValue(Node<T> node) {
        setFront(node);
        setTail(node);
    }

    private Node<T> recursiveSwap(Node<T> node) {
        if (node.getNext() == null) {
            setFront(node);
            return node;
        }
        var temp = recursiveSwap(node.getNext());
        temp.setNext(node);
        return node;
    }
}
