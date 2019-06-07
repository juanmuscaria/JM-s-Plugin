package io.github.juanmuscaria.core.utils;

/**
 * Abstraction for some simple cache calls.
 *
 * @author Jikoo
 */
public abstract class Function<V> {

    public abstract boolean run(V value);

}