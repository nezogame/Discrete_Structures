package org.denys.hudymov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
@Builder(toBuilder = true)
public class SkipListImpl implements SkipList<Integer> {
    private final Random rand = new Random();
    private int maxLevel;
    private Node<Integer> head;
    private boolean limited;

    @Override
    public int size() {
        int count = 0;
        var current = head; // Start from the head node
        while (current.getBelow() != null) {
            current = current.getBelow(); // Move down to the bottom level
        }
        while (current != null) {
            count++;
            current = current.getNext(); // Traverse horizontally at the bottom level
        }
        return count;
    }

    @Override
    public int levelSize(int level) {
        if (level < 0 || level > maxLevel) {
            throw new IllegalArgumentException("Invalid level");
        }

        int count = 0;
        var current = head;
        while (current.getLevel() > level && current.getBelow() != null) {
            current = current.getBelow(); // Traverse down to the desired level
        }

        while (current != null) {
            count++;
            current = current.getNext(); // Traverse horizontally at the desired level
        }
        return count;
    }

    @Override
    public Node<Integer> add(Integer value) {

        if (head == null) {
            head = Node.<Integer>builder().key(value).value(value).build();
            return head;
        }

        var level = 0;
        if (isLimited()) {
            while (rand.nextFloat(1) > 0.75 && level < maxLevel) {
                level++;
            }
        } else {
            while (rand.nextFloat(1) > 0.75) {
                level++;
            }
        }
        var newNode = Node.<Integer>builder().key(value).value(value).level(level).build();
        if(head.getValue().compareTo(value) > 0){
            List<Node<Integer>> prevNodes = new ArrayList<>();
            findPrevHelper(prevNodes, head, value);
            for (int i = 0; i <= maxLevel; i++) {
                head = newNode.toBuilder()
                        .level(i)
                        .next(prevNodes.get(i))
                        .below(i>0 ? head:null)
                        .build();
            }
            return newNode;
        }
        if (level > maxLevel) {

            maxLevel = level;
            for (int i = head.getLevel() + 1; i <= maxLevel; i++) {
                var nodeBelow = Node.<Integer>builder()
                        .key(head.getKey())
                        .value(head.getValue())
                        .level(i)
                        .build();
                nodeBelow.setBelow(head);
                head = nodeBelow;
            }
        }
        List<Node<Integer>> prevNodes = new ArrayList<>();
        findPrevHelper(prevNodes, head, value);

        addHelper(prevNodes, newNode, level);
        return newNode;
    }

    private void findPrevHelper(List<Node<Integer>> prevNodes, Node<Integer> current, Integer value) {
        while (current.getNext() != null && current.getNext().getValue().compareTo(value) < 0) {
            current = current.getNext();
        }
        if (current.getBelow() != null) {
            findPrevHelper(prevNodes, current.getBelow(), value);
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
    public Optional<Node<Integer>> find(int searchKey) {
        Node<Integer> current = head; // Start from the top-left corner

        while (current != null) {
            if (current.getNext() == null || current.getNext().getKey() > searchKey) {
                if (current.getKey() == searchKey) {
                    return Optional.of(current); // Node found
                }
                if (current.getBelow() != null) {
                    current = current.getBelow(); // Move down one level
                } else {
                    break; // Reached the bottom level, key not found
                }
            } else {
                current = current.getNext(); // Move to the right
            }
        }

        return Optional.empty(); // Key not found in the skip list
    }

    @Override
    public void delete(int deleteKey) throws NoSuchElementException {
        Optional<Node<Integer>> nodeToDelete = find(deleteKey);
        if (nodeToDelete.isEmpty()) {
            throw new NoSuchElementException("Key not found in the Skip List");
        }
        List<Node<Integer>> prevNodes = new ArrayList<>();
        findPrevHelper(prevNodes, getHead(), deleteKey);

        if (head.getKey() == deleteKey) {
            head = prevNodes.get(0).getNext();
            for (int i = head.getLevel()+1; i <= maxLevel; i++) {
                head = getHead().toBuilder()
                        .level(i)
                        .below(head)
                        .next(prevNodes.get(i).getNext().getKey() != head.getKey() ? prevNodes.get(i).getNext(): prevNodes.get(i).getNext().getNext())
                        .build();
            }
            /*for (var pNode : prevNodes) {
                var nodeBelow = nodeToDelete.get().toBuilder()
                        .level(pNode.getLevel())
                        .build();
                nodeBelow.setBelow(head);
                head = nodeBelow;
            }*/

        } else {
            Collections.reverse(prevNodes);
            for (var pNode : prevNodes) {
                pNode.setNext(nodeToDelete.get().getNext());
                if(nodeToDelete.get().getBelow()!=null) {
                    nodeToDelete = Optional.of(nodeToDelete.get().getBelow());
                }
            }

        }
    }

    @Override
    public SkipList<Integer> copy() {
        SkipListImpl copyList = SkipListImpl.<Integer>builder()
                .build();
        copyList.head = copyNodeRecursively(head); // Start the recursion at the head of the original list
        copyList.maxLevel = maxLevel; // Set the max level of the copy list
        return copyList;
    }

    private Node<Integer> copyNodeRecursively(Node<Integer> original) {
        if (original == null) {
            return null; // Base case: null node
        }

        // Copy the current node
        var newNode = original.toBuilder()
                .below(null)
                .next(null)
                .build();

        // Recursively copy the next and below nodes
        newNode.setNext(copyNodeRecursively(original.getNext()));
        newNode.setBelow(copyNodeRecursively(original.getBelow()));

        return newNode;
    }

    @Override
    public void clean() {
        head = null;
    }

    @Override
    public void print() {
        if(head==null){
            System.out.println("List is empty");
            return;
        }
        var levelNode = head;
        var currentLevel = head.getLevel();
        while (currentLevel >= 0) {
            var currentNode = levelNode;
            System.out.println("Level " + currentLevel + ":");


            while (currentNode != null) {
                System.out.print("Key: " + currentNode.getKey()+ " ->");
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
