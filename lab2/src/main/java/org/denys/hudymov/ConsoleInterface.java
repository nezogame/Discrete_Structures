package org.denys.hudymov;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleInterface {

    SkipListImpl skipList = new SkipListImpl();

    Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("If you want you can define max level of Skip List if no enter 0:");
        int size = scanner.nextInt();
        if(size>0){
            skipList.setMaxLevel(size);
            skipList.setLimited(true);
        }


        SkipList<Integer> copy = null;
        while (true) {
            System.out.println("Skip List Menu:");
            System.out.println("1. Add a node");
            System.out.println("2. Find a node");
            System.out.println("3. Delete a node");
            System.out.println("4. Copy the Skip List");
            System.out.println("5. Print Skip List");
            System.out.println("6. Clear Skip List");
            System.out.println("7. Print copy of Skip List");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter a key/value to add: ");
                    int valueToAdd = scanner.nextInt();
                    skipList.add(valueToAdd);
                }
                case 2 -> {
                    System.out.print("Enter a key to find: ");
                    int keyToFind = scanner.nextInt();
                    Optional<Node<Integer>> foundNode = skipList.find(keyToFind);
                    if (foundNode.isPresent()) {
                        System.out.println("Found: " + foundNode.get().getValue());
                    } else {
                        System.out.println("Not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter a key to delete: ");
                    int keyToDelete = scanner.nextInt();
                    try {
                        skipList.delete(keyToDelete);
                        System.out.println("Deleted.");
                    } catch (NoSuchElementException e) {
                        System.out.println("Key not found.");
                    }
                }
                case 4 -> {
                    copy = skipList.copy();
                    System.out.println("Skip List copied.");
                }
                case 5 -> {
                    System.out.println("Skip List:");
                    skipList.print();
                }
                case 6 -> {
                    skipList.clean();
                    System.out.println("Skip List cleared.");
                }
                case 7 -> {
                    System.out.println("Skip List COPY.");
                    if (copy != null) {
                        copy.print();
                    }
                }
                case 0 -> {
                    System.out.println("Exiting Skip List Console Interface.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
