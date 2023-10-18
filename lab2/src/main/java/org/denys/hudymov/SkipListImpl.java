package org.denys.hudymov;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkipListImpl<T extends Comparable<? super T>> implements SkipList<Integer> {
    private final Random rand = new Random();
    private int maxLevel;
    private Node<Integer> head;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int levelSize() {
        return 0;
    }

    @Override
    public Node<Integer> add(Integer value) {

        if (head == null) {
            head = Node.<Integer>builder().key(value).value(value).build();
            return head;
        }

        var level = 0;
        while (rand.nextFloat(1) > 0.75) {
            level++;
        }
        var newNode = Node.<Integer>builder().key(value).value(value).level(level).build();
        if (level > maxLevel) {
            maxLevel = level;
            for (int i = head.getLevel() + 1; i <= maxLevel; i++) {
                var nodeBelow = Node.<Integer>builder().key(head.getKey()).value(head.getValue()).level(i).build();
                nodeBelow.setBelow(head);
                head = nodeBelow;
            }
        }
        List<Node<Integer>> prevNodes = new ArrayList<>();
        addFindPrevHelper(prevNodes, head, value);

        addHelper(prevNodes, newNode, level);
        return newNode;
    }

    private void addFindPrevHelper(List<Node<Integer>> prevNodes, Node<Integer> current, Integer value) {
        while (current.getNext() != null && current.getNext().getValue().compareTo(value) < 1) {
            current = current.getNext();
        }
        if (current.getBelow() != null) {
            addFindPrevHelper(prevNodes, current.getBelow(), value);
        }
        prevNodes.add(current);
    }

    private Node<Integer> addHelper(List<Node<Integer>> prevNodes, Node<Integer> newNode, Integer level) {
        newNode.setNext(prevNodes.get(level).getNext());

        if (level == 0) {
            var currentNode = newNode.toBuilder()
                    .level(level)
                    .build();
            prevNodes.get(level).setNext(currentNode);
            return currentNode;
        }

        var nodeUpper = newNode.toBuilder()
                .level(level)
                .build();
        var tmp = addHelper(prevNodes, newNode, level - 1);
        nodeUpper.setBelow(tmp);

        prevNodes.get(level).setNext(nodeUpper);
        return nodeUpper;
    }

    @Override
    public Node<Integer> find(int key) {
        return null;
    }

    @Override
    public Node<Integer> delete(int key) {
        return null;
    }

    @Override
    public SkipList<Integer> copy() {
        return null;
    }

    @Override
    public void clean() {

    }

    @Override
    public void print() {
        var levelNode = head;
        var currentLevel = head.getLevel();
        while (currentLevel >= 0) {
            var currentNode = levelNode;
            System.out.println("Level " + currentLevel + ":");


            while (currentNode != null) {
                System.out.print("Key: " + currentNode.getKey() + ", LEVEL: " + currentNode.getLevel() + "->");
                currentNode = currentNode.getNext();
            }
            System.out.println("Nil");
            if (levelNode.getBelow() != null) {
                levelNode = levelNode.getBelow();
            }
            currentLevel--;
        }
    }

    //problems with understanding task in lab
    private int randomLevelEverySecond() {
        int level = 1;
        Random random = new Random();
        while ((random.nextInt() & 1) == 1 && level < maxLevel) {
            level++;
        }
        return level;
    }

}
