package org.denys.hudymov;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder(toBuilder = true)
public class Node<N extends Comparable<? super N>> {
    private int key;
    private N value;
    private int level;
    private Node<N> next, below;
}
