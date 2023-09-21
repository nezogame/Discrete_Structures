package org.denys.hudymov.multilist;

import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.Optional;
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
        return 0;
    }

    @Override
    public int countSizeOfLayer(int layerNumber) {
        return 0;
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

    }

    @Override
    public void deleteBranch(int branchIndex) {

    }

    @Override
    public void copy() {

    }

    @Override
    public void clean() {

    }

    @Override
    public void print() {
        System.out.println(traversePreOrder(getRoot()));
    }

    private String traversePreOrder(Node<Integer> root) {

        if (root == null) {
            return "List is empty";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getValue());

        String pointerRight = "└──";
        String pointerLeft = (root.getNext() != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.getChild(), root.getNext() != null);
        traverseNodes(sb, "", pointerRight, root.getNext(), false);

        return sb.toString();
    }

    private void traverseNodes(StringBuilder sb, String padding, String pointer, Node<Integer> node,
                               boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getValue());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getNext() != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getChild(), node.getNext() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getNext(), false);
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

        if (current.getNext() != null) {
            node = traversal(index, current.getNext(), count);
        }
        return node;
    }
}
