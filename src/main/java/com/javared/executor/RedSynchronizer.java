package com.javared.executor;

import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

/**
 * A class to implement execution of a Red Synchronizer which receive INPUT typed
 * inputs and returns OUTPUT typed outputs
 *
 * @param <INPUT>  type of the input of the execution
 * @param <OUTPUT> type of the output of the execution
 */
abstract public class RedSynchronizer<INPUT, OUTPUT> extends BaseRedSynchronizer {

    // Public

    /**
     * Receive an input and executes it, returns a {@link RedFutureOf}
     * of the execution output.
     *
     * @param input input to execute
     * @return {@link RedFutureOf} of the execution output
     */
    public RedFutureOf<OUTPUT> execute(INPUT input) {
        try {
            Result<OUTPUT> result = handle(input);
            return result == null ? null : result._future;
        } catch (Throwable t) {
            return RedFuture.failedOf(t);
        }
    }

    // Private

    /**
     * Implements the execution flow of the Synchronizer
     * @param input input to handle
     * @return the result producing the output of the execution
     * @throws Throwable to enable throwable catching
     */
    abstract protected Result<OUTPUT> handle(INPUT input) throws Throwable;

}
