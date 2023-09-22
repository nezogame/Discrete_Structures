package org.denys.hudymov.multilist;

import java.util.Scanner;

public class TreeConsoleInterface {
    public static final String RED = "\033[0;31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final GenericTree<Integer> tree;
    Scanner scanner;

    public TreeConsoleInterface() {
        this.tree = new MultiList();
        scanner = new Scanner(System.in);
    }

    public void run() {
        GenericTree<Integer> copy = null;
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Count Elements");
            System.out.println("2. Count Size of Layer");
            System.out.println("3. Add Element At");
            System.out.println("4. Add Child At");
            System.out.println("5. Delete Layer With Children");
            System.out.println("6. Delete Branch");
            System.out.println("7. Copy Tree");
            System.out.println("8. Clean Tree");
            System.out.println("9. Print Tree");
            System.out.println("10. Print Copy of the Tree");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    int elementCount = tree.countElements();
                    System.out.println("Total Elements: " + elementCount);
                }
                case 2 -> {
                    System.out.print("Enter layer number: ");
                    int layerNumber = scanner.nextInt();
                    int sizeOfLayer = tree.countSizeOfLayer(layerNumber);
                    System.out.println("Size of Layer " + layerNumber + ": " + sizeOfLayer);
                }
                case 3 -> {
                    System.out.print("Enter index: ");
                    int index = scanner.nextInt();
                    System.out.print("Enter value: ");
                    int value = scanner.nextInt();
                    try {
                        tree.addElementAt(index, value);
                        System.out.println("Element added successfully.");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid index.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter index: ");
                    int childIndex = scanner.nextInt();
                    System.out.print("Enter value: ");
                    int childValue = scanner.nextInt();
                    try {
                        tree.addChildAt(childIndex, childValue);
                        System.out.println("Child added successfully.");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(RED + "Invalid index." + ANSI_RESET);
                    }
                }
                case 5 -> {
                    System.out.print("Enter layer number to delete: ");
                    int layerToDelete = scanner.nextInt();
                    tree.deleteLayerWithChildren(layerToDelete);
                    System.out.println("Layer " + layerToDelete + " and its children deleted.");
                }
                case 6 -> {
                    System.out.print("Enter branch index to delete: ");
                    int branchIndex = scanner.nextInt();
                    System.out.print("Delete child (true/false): ");
                    boolean deleteChild = scanner.nextBoolean();
                    try {
                        tree.deleteBranch(branchIndex, deleteChild);
                        System.out.println("Branch deleted successfully.");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(RED + "Invalid index." + ANSI_RESET);
                    }
                }
                case 7 -> {
                    copy = tree.copy();
                    System.out.println("Tree copied.");
                    copy.print();
                }
                case 8 -> {
                    tree.clean();
                    System.out.println("Tree cleaned.");
                }
                case 9 -> tree.print();
                case 10 -> {
                    if (copy != null) {
                        copy.print();
                    } else {
                        System.out.println(RED + "Copy is null" + ANSI_RESET);
                    }
                }
                case 0 -> {
                    System.out.println("Exiting Tree Console Interface.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
