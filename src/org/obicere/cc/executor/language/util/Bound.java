package org.obicere.cc.executor.language.util;

/**
 * Specifies a mathematical bound over two numbers. The bound is inclusive
 * on both ends.
 * <p>
 * So if given two numbers: <code>a</code> and <code>b</code>
 * <pre>
 * if a <= b:
 *     [a, b]
 * else:
 *     [b, a]
 * </pre>
 *
 * @author Obicere
 * @version 1.0
 */
public class Bound implements Comparable<Bound> {

    private final int min;
    private final int max;

    private final int delta;

    public Bound(final int a, final int b) {
        if (a <= b) {
            min = a;
            max = b;
        } else {
            min = b;
            max = a;
        }
        delta = max - min;
    }

    public int getDelta() {
        return delta;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Bound && boundEquals((Bound) obj);
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return min * 17 + max * 31;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(10); // 10 is absolute min string length
        builder.append("Bound[");
        builder.append(min);
        builder.append(",");
        builder.append(max);
        builder.append("]");
        return builder.toString();
    }

    private boolean boundEquals(final Bound bound) {
        return bound.min == min;
    }


    @Override
    public int compareTo(final Bound o) {
        return Integer.compare(min, o.getMin());
    }
}