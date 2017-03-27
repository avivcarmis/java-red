package com.javared.executor;

import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.OpenRedFuture;
import com.javared.future.OpenRedFutureOf;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

import java.util.concurrent.Future;

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

    abstract protected Result<OUTPUT> handle(INPUT input);

}
