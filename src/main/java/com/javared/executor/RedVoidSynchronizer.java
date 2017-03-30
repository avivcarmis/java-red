package com.javared.executor;

import com.javared.future.RedFuture;

/**
 * Created by avivc on 3/27/2017.
 */
abstract public class RedVoidSynchronizer<INPUT> extends BaseRedSynchronizer {

    // Public

    public RedFuture execute(INPUT input) {
        try {
            Marker result = handle(input);
            return result == null ? null : result._future;
        } catch (Throwable t) {
            return RedFuture.failedOf(t);
        }
    }

    // Private

    abstract protected Marker handle(INPUT input) throws Throwable;

}
