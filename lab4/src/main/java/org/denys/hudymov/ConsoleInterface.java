package org.denys.hudymov;

import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ConsoleInterface {

    private Matrix matrix;
    private Matrix secondMatrix;

    private Scanner scanner;

    public void run() {
        matrix.initializeMatrix();
        secondMatrix.initializeMatrix();
        while (true) {
            System.out.println("Matrix Menu:");
            System.out.println("1. Add a value to the matrix");
            System.out.println("2. Print the matrix");
            System.out.println("3. Multiply matrix by scalar");
            System.out.println("4. Transpose the matrix");
            System.out.println("5. Add a value to the SECOND matrix");
            System.out.println("6. Print the SECOND matrix");
            System.out.println("7. Add another matrix");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter row: ");
                    int row = scanner.nextInt();
                    System.out.print("Enter column: ");
                    int column = scanner.nextInt();
                    System.out.print("Enter value: ");
                    int value = scanner.nextInt();
                    try {
                        matrix.add(row, column, value);
                    }catch (IllegalArgumentException e){
                        System.err.println(e.getMessage());
                    }
                }
                case 2 -> matrix.print();
                case 3 -> {
                    System.out.print("Enter scalar value: ");
                    int scalar = scanner.nextInt();
                    matrix.multiplyByScalar(scalar);
                }
                case 4 -> matrix.transpose();
                case 5 -> {
                    System.out.print("Enter row: ");
                    int row = scanner.nextInt();
                    System.out.print("Enter column: ");
                    int column = scanner.nextInt();
                    System.out.print("Enter value: ");
                    int value = scanner.nextInt();
                    try {
                        secondMatrix.add(row, column, value);
                    }catch (IllegalArgumentException e){
                        System.err.println(e.getMessage());
                    }

                }
                case 6 -> secondMatrix.print();
                case 7 -> matrix.addMatrix(secondMatrix);
                case 0 -> {
                    System.out.println("Exiting Matrix Console Interface.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
