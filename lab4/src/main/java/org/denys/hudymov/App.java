package org.denys.hudymov;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        var matrix = Matrix.builder()
                .rows(3)
                .columns(4).build();
        var secondMatrix = Matrix.builder()
                .rows(3)
                .columns(4).build();
        var matrixInterface = ConsoleInterface.builder()
                .matrix(matrix)
                .secondMatrix(secondMatrix)
                .scanner(new Scanner(System.in))
                .build();
        matrixInterface.run();
        /*matrix.initializeMatrix();
        matrix.add(0, 0, 2);
        matrix.add(0, 1, 6);
        matrix.add(1, 0, 1);
        matrix.add(1, 1, 7);

        matrix.testPrint();
        matrix.transpose();
        matrix.testPrint();

        matrix.multiplyByScalar(2);
        matrix.testPrint();


        var secondMatrix = Matrix.builder()
                .rows(2)
                .columns(2).build();

        secondMatrix.initializeMatrix();
        secondMatrix.add(0, 0, 1);
        secondMatrix.add(0, 1, 1);
        secondMatrix.add(1, 0, 0);
        secondMatrix.add(1, 1, 1);
        secondMatrix.testPrint();

        matrix.addMatrix(secondMatrix);
        matrix.testPrint();*/
    }
}
