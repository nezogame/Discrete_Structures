package org.denys.hudymov.deque;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Node<T> {
    private T value;
    private Node<T> next, prev;
}
