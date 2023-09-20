package org.denys.hudymov;

import java.util.Optional;

public interface Deque<T> {
    void addToFront(T vale);
    void addToTail(T vale);
    void deleteFront();
    void deleteTail();
    Optional<T> readFrontElement();
    Optional<T> readTailElement();
    void swapLastAndFirst();
    boolean isEmpty();
    int countSize();
    void reverse();
    boolean isPresent(T value);
    void clean();
    void print();
}
