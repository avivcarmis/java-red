package com.javared.executor;

import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

abstract public class RedSynchronizer<INPUT, OUTPUT> extends BaseRedSynchronizer {

    // Public

    public RedFutureOf<OUTPUT> execute(INPUT input) {
        try {
            Result<OUTPUT> result = handle(input);
            return result == null ? null : result._future;
        } catch (Throwable t) {
            return RedFuture.failedOf(t);
        }
    }

    // Private

    abstract protected Result<OUTPUT> handle(INPUT input) throws Throwable;

}
