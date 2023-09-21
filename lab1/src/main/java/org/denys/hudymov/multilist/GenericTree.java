package org.denys.hudymov.multilist;

public interface GenericTree<T> {
    int countElements();

    int countSizeOfLayer(int layerNumber);

    void addElementAt(int index, int value) throws IndexOutOfBoundsException;

    void addChildAt(int index, int value) throws IndexOutOfBoundsException;

    void deleteLayerWithChildren(int layerNumber);

    void deleteBranch(int branchIndex);

    void copy();

    void clean();

    void print();
}
