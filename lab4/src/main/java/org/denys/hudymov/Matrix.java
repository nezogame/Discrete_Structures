package org.denys.hudymov;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Matrix {
    private int rows;
    private int columns;
    private Node head;

    public void add(int row, int column, Integer value) throws IllegalArgumentException {
        if (row >= rows || column >= columns) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
        if (value==null||value==0){
            value=null;
        }
        Node current = head.getNextRow();
        while (current != null && current.getRow() != row) {
            current = current.getNextRow();
        }

        if (current != null) {
            Node node = current.getNextColumn();
            while (node != null && node.getColumn() != column) {
                node = node.getNextColumn();
            }
            if (node != null) {
                node.setValue(value);
            }
        }
    }

    public void initializeMatrix() {
        head = new Node(null, -1, -1, null, null); // Dummy head node
        var current = head;
        for (int i = 0; i < rows; i++) {
            Node newRow = new Node(null, i, -1, null, null); // Dummy nodes for each row
            current.setNextRow(newRow);
            current = newRow;

            Node prev = current;
            for (int j = 0; j < columns; j++) {
                Node newNode = new Node(null, i, j, null, null);
                prev.setNextColumn(newNode);
                prev = newNode;
            }
        }
    }

    public void testPrint() {
        Node current = head.getNextRow();
        System.out.println("----------------------");
        while (current != null) {
            Node node = current.getNextColumn();
            while (node != null) {
                System.out.print("(" + node.getRow() + "," + node.getColumn() + "," + node.getValue() + ") ");
                node = node.getNextColumn();
            }
            System.out.println();
            current = current.getNextRow();
        }
        System.out.println("----------------------");
    }

    public void print() {
        Node current = head.getNextRow();
        System.out.println("----------------------");
        while (current != null) {
            Node node = current.getNextColumn();
            while (node != null) {
                System.out.print(node.getValue()+" ") ;
                node = node.getNextColumn();
            }
            System.out.println();
            current = current.getNextRow();
        }
        System.out.println("----------------------");
    }

        // Методи для операцій над матрицею: множення, транспонування, додавання
    public void multiplyByScalar(int scalar) {
        Node currentRow = this.head.getNextRow(); // Start from the first row after the dummy node
        while (currentRow!=null&&currentRow.getRow()<=this.rows) {
            Node currentNode = currentRow.getNextColumn(); // Start from the first column of the current row

            while (currentNode!=null&&currentNode.getColumn()<this.columns) {
                if(currentNode.getValue()!=null){
                    add( currentNode.getRow(), currentNode.getColumn(), currentNode.getValue()*scalar);
                }
                currentNode = currentNode.getNextColumn(); // Move to the next column
            }

            currentRow = currentRow.getNextRow(); // Move to the next row
        }
    }

    public void transpose() {
        Matrix newMatrix = Matrix.builder()
                .rows(this.columns)
                .columns(this.rows)
                .build();
        newMatrix.initializeMatrix();

        Node currentRow = this.head.getNextRow(); // Start from the first row after the dummy node

        while (currentRow!=null&&currentRow.getRow()<this.rows) {
            Node currentNode = currentRow.getNextColumn(); // Start from the first column of the current row

            while (currentNode!=null&&currentNode.getColumn()<this.columns) {
                newMatrix.add(currentNode.getColumn(), currentNode.getRow(), currentNode.getValue());
                currentNode = currentNode.getNextColumn(); // Move to the next column
            }

            currentRow = currentRow.getNextRow(); // Move to the next row
        }

        this.head = newMatrix.getHead(); // Update the head reference to the transposed matrix
        this.rows = newMatrix.getRows();
        this.columns = newMatrix.getColumns();
    }

    public void addMatrix(Matrix addedMatrix) throws IllegalArgumentException {
        if (this.rows != addedMatrix.getRows() || this.columns != addedMatrix.getColumns()) {
            throw new IllegalArgumentException("Matrices should have the same dimensions");
        }

        Matrix result = Matrix.builder()
                .rows(this.rows)
                .columns(this.columns)
                .build();
        result.initializeMatrix();

        Node currentRowThis = this.head.getNextRow();
        Node currentRowOther = addedMatrix.head.getNextRow();

        while (currentRowThis != null) {
            Node currentNodeThis = currentRowThis.getNextColumn();
            Node currentNodeOther = currentRowOther.getNextColumn();

            while (currentNodeThis != null) {
                var thisValue=currentNodeThis.getValue()!=null?currentNodeThis.getValue():0;
                var otherValue=currentNodeOther.getValue()!=null?currentNodeOther.getValue():0;
                int sum = thisValue + otherValue;
                add( currentNodeThis.getRow(), currentNodeThis.getColumn(), sum);


                currentNodeThis = currentNodeThis.getNextColumn();
                currentNodeOther = currentNodeOther.getNextColumn();

            }

            currentRowThis = currentRowThis.getNextRow();
            currentRowOther = currentRowOther.getNextRow();
        }

    }

}

