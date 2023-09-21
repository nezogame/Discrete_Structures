package org.denys.hudymov.deque;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleInterface {
    private final Deque<Integer> deque;

    public ConsoleInterface() {
        this.deque = new DequeImpl<>();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add to Front");
            System.out.println("2. Add to Tail");
            System.out.println("3. Delete Front");
            System.out.println("4. Delete Tail");
            System.out.println("5. Read Front Element");
            System.out.println("6. Read Tail Element");
            System.out.println("7. Swap Last and First");
            System.out.println("8. Check if Empty");
            System.out.println("9. Count Size");
            System.out.println("10. Reverse");
            System.out.println("11. Check if Element is Present");
            System.out.println("12. Clean");
            System.out.println("13. Print");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter value to add to the front: ");
                    Integer valueToFront = Integer.valueOf(scanner.nextLine());
                    deque.addToFront(valueToFront);
                }
                case 2 -> {
                    System.out.print("Enter value to add to the tail: ");
                    Integer valueToTail = Integer.valueOf(scanner.nextLine());
                    deque.addToTail(valueToTail);
                }
                case 3 -> deque.deleteFront();
                case 4 -> deque.deleteTail();
                case 5 -> {
                    Optional<Integer> frontElement = deque.readFrontElement();
                    System.out.println("Front Element: " + frontElement.orElse(null));
                }
                case 6 -> {
                    Optional<Integer> tailElement = deque.readTailElement();
                    System.out.println("Tail Element: " + tailElement.orElse(null));
                }
                case 7 -> deque.swapLastAndFirst();
                case 8 -> System.out.println("Deque is empty: " + deque.isEmpty());
                case 9 -> System.out.println("Deque size: " + deque.countSize());
                case 10 -> deque.reverse();
                case 11 -> {
                    System.out.print("Enter value to check if present: ");
                    String valueToCheck = scanner.nextLine();
                    System.out.println("Element is present: " + deque.isPresent(Integer.valueOf(valueToCheck)));
                }
                case 12 -> deque.clean();
                case 13 -> deque.print();
                case 0 -> {
                    System.out.println("Exiting Deque Console Interface.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
