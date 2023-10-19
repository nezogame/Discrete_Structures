package org.denys.hudymov;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface SkipList<T extends Comparable<? super T>> {
    int size();

    int levelSize(int level);

    Node<T> add(T value);

    Optional<Node<T>> find(int key);

    void delete(int key) throws NoSuchElementException;

    SkipList<T> copy();

    void clean();

    void print();
}
