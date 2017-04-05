package io.github.avivcarmis.javared.future.callbacks;

/**
 * A simple callback interface for receiving a typed value.
 * @param <T> the type of the callback parameter
 */
public interface Callback<T> {

    /**
     * Implementation of the callback
     * @param t the resulted callback parameter
     */
    void call(T t);

}
