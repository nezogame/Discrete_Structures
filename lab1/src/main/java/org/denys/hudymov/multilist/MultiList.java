package org.denys.hudymov.multilist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiList implements GenericTree<Integer> {
    private Node<Integer> root;

    @Override
    public int countElements() {
        if (getRoot() == null) {
            return 0;
        }
        AtomicInteger count = new AtomicInteger(0);
        var node = traversal(-1, getRoot(), count);
        return count.get();
    }

    @Override
    public int countSizeOfLayer(int layerNumber) {
        if (getRoot() == null || layerNumber < 0) {
            return 0;
        }

        AtomicInteger count = new AtomicInteger(0);
        countSizeOfLayer(getRoot(), layerNumber, 0, count);
        return count.get();
    }

    private void countSizeOfLayer(Node<Integer> node, int targetLayer, int currentLayer, AtomicInteger count) {
        if (currentLayer == targetLayer) {
            count.incrementAndGet();
        }

        // Recursively check child nodes
        if (node.getChild() != null) {
            countSizeOfLayer(node.getChild(), targetLayer, currentLayer + 1, count);
        }

        // Recursively check next sibling nodes at the same level
        if (node.getNext() != null) {
            countSizeOfLayer(node.getNext(), targetLayer, currentLayer, count);
        }
    }

    @Override
    public void addElementAt(int index, int value) throws IndexOutOfBoundsException {
        Node<Integer> newNode = Node.<Integer>builder()
                .value(value)
                .build();
        add(newNode, index, false);
    }

    @Override
    public void addChildAt(int index, int value) throws IndexOutOfBoundsException {
        Node<Integer> newNode = Node.<Integer>builder()
                .value(value)
                .build();
        add(newNode, index, true);
    }

    private void add(Node<Integer> newNode, int index, boolean child) throws IndexOutOfBoundsException {
        if (getRoot() == null || index == 0) {
            newNode.setNext(getRoot());
            setRoot(newNode);
            return;
        }
        AtomicInteger count = new AtomicInteger(0);
        var node = traversal(index, getRoot(), count);
        if (count.get() != index) {
            throw new IndexOutOfBoundsException("This index {" + index + "} doesn't exist. Maximum index if {" + count + "}");
        } else if (child) {
            newNode.setNext(node.getChild());
            node.setChild(newNode);
        } else {
            newNode.setNext(node.getNext());
            node.setNext(newNode);
        }
    }

    @Override
    public void deleteLayerWithChildren(int layerNumber) {
        if (getRoot() == null || layerNumber <= 0) {
            setRoot(null);
            return;
        }

        deleteLayerWithChildren(getRoot(), layerNumber - 1, 0);
    }

    private void deleteLayerWithChildren(Node<Integer> node, int targetLayer, int currentLayer) {
        if (currentLayer == targetLayer) {
            node.setChild(null);
        }

        // Recursively check child nodes
        if (node.getChild() != null) {
            deleteLayerWithChildren(node.getChild(), targetLayer, currentLayer + 1);
        }

        // Recursively check next sibling nodes at the same level
        if (node.getNext() != null) {
            deleteLayerWithChildren(node.getNext(), targetLayer, currentLayer);
        }
    }

    @Override
    public void deleteBranch(int index, boolean deleteChild) throws IndexOutOfBoundsException {
        if (getRoot() == null || index < 0) {
            setRoot(null);
            return;
        }

        AtomicInteger count = new AtomicInteger(-1);
        deleteBranch(getRoot(), index, count, deleteChild);
        if (count.get() != index) {
            throw new IndexOutOfBoundsException("This index {" + index + "} doesn't exist. Maximum index is {" + count + "}");
        }
    }

    private void deleteBranch(Node<Integer> node, int targetIndex, AtomicInteger currentIndex, boolean deleteChild) {
        currentIndex.incrementAndGet();

        if (targetIndex == currentIndex.get() && deleteChild) {
            node.setChild(null);
            return;
        } else if (targetIndex == currentIndex.get()) {
            node.setNext(null);
            return;
        }

        // Recursively check child nodes
        if (node.getChild() != null) {
            deleteBranch(node.getChild(), targetIndex, currentIndex, deleteChild);
        }

        // Recursively check next sibling nodes at the same level
        if (node.getNext() != null) {
            deleteBranch(node.getNext(), targetIndex, currentIndex, deleteChild);
        }
    }

    @Override
    public MultiList copy() {
        if (getRoot() == null) {
            return new MultiList(); // Return an empty multi-list
        }

        Node<Integer> copiedRoot = copyTree(getRoot());
        MultiList copiedMultiList = new MultiList();
        copiedMultiList.setRoot(copiedRoot);

        return copiedMultiList;
    }

    private Node<Integer> copyTree(Node<Integer> originalNode) {
        var copiedNode = Node.<Integer>builder()
                .value(originalNode.getValue())
                .build();

        // Recursively copy child nodes
        if (originalNode.getChild() != null) {
            Node<Integer> copiedChild = copyTree(originalNode.getChild());
            copiedNode.setChild(copiedChild);
        }

        // Recursively copy next sibling nodes
        if (originalNode.getNext() != null) {
            Node<Integer> copiedNext = copyTree(originalNode.getNext());
            copiedNode.setNext(copiedNext);
        }

        return copiedNode;
    }

    @Override
    public void clean() {
        deleteLayerWithChildren(0);
    }

    @Override
    public void print() {
        if (getRoot() == null) {
            System.out.println("Multi-list is empty");
        }

        AtomicInteger index = new AtomicInteger(0);
        printTree(getRoot(), "", index);
    }

    private void printTree(Node<Integer> node, String indent, AtomicInteger index) {
        ;
        if (node == null) {
            return;
        }

        // Print the current node's value
        System.out.println(indent + index.incrementAndGet() + ":" + node.getValue());

        // Recursively print child nodes with increased indentation
        if (node.getChild() != null) {
            printTree(node.getChild(), indent + "    ", index);
        }

        // Recursively print next sibling nodes at the same level
        if (node.getNext() != null) {
            printTree(node.getNext(), indent, index);
        }
    }


    private Node<Integer> traversal(int index, Node<Integer> current, AtomicInteger count) {
        if (!Objects.equals(index, count.get())) {
            count.incrementAndGet();
        }
        var node = current;
        if (Objects.equals(index, count.get())) {
            return node;
        }
        if (current.getChild() != null) {
            node = traversal(index, current.getChild(), count);
        }
        if (Objects.equals(index, count.get())) {
            return node;
        }
        if (current.getNext() != null) {
            node = traversal(index, current.getNext(), count);
        }
        return node;
    }
}
