package org.denys.hudymov.multilist;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Node<T> {
    private T value;
    private Node<T> next, child;
}