package org.denys.hudymov;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Node {
    private Integer value;
    private int row;
    private int column;
    private Node nextColumn;
    private Node nextRow;
}

