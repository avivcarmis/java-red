package io.github.avivcarmis.javared.executor;

import io.github.avivcarmis.javared.future.RedFuture;

/**
 * A class to implement execution of a Red Synchronizer which receive INPUT typed
 * inputs and returns a {@link RedFuture} to indicate the completion of an execution.
 *
 * @param <INPUT>  type of the input of the execution
 */
abstract public class RedVoidSynchronizer<INPUT> extends BaseRedSynchronizer {

    // Public

    /**
     * Receive an input and executes it, returns a {@link RedFuture}
     * of the execution completion.
     *
     * @param input input to execute
     * @return {@link RedFuture} of the execution output
     */
    public RedFuture execute(INPUT input) {
        try {
            Marker result = handle(input);
            return result == null ? null : result._future;
        } catch (Throwable t) {
            return RedFuture.failedOf(t);
        }
    }

    // Private

    /**
     * Implements the execution flow of the Synchronizer
     * @param input input to handle
     * @return the marker indicating the execution completion
     * @throws Throwable to enable throwable catching
     */
    abstract protected Marker handle(INPUT input) throws Throwable;

}
