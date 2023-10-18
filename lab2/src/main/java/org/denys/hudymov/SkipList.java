package org.denys.hudymov;

public interface SkipList<T extends Comparable<? super T>> {
    int size();

    int levelSize();

    Node<T> add(T value);

    Node<T> find(int key);

    Node<T> delete(int key);

    SkipList<T> copy();

    void clean();

    void print();
}
