package com.javared.future;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.javared.future.callbacks.TypedCallback;

import java.util.concurrent.Executor;

/**
 * Created by Mamot on 3/14/2017.
 */
public class OpenRedFuture extends BaseOpenRedFuture<Void> {

    // Constructors

    protected OpenRedFuture() {}

    // Public

    public void resolve() {
        resolve(null, true);
    }

    public void tryResolve() {
        resolve(null, false);
    }

    public OpenRedFuture follow(RedFuture future) {
        future.addSuccessCallback(this::resolve).addFailureCallback(this::fail);
        return this;
    }

    public OpenRedFuture follow(Executor executor, RedFuture future) {
        future.addSuccessCallback(executor, this::resolve).addFailureCallback(executor, this::fail);
        return this;
    }

    public OpenRedFuture follow(ListenableFuture<?> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(o -> resolve(), this::fail));
        return this;
    }

    public OpenRedFuture follow(Executor executor, ListenableFuture<?> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(o -> resolve(), this::fail), executor);
        return this;
    }

}
