package com.javared.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.callbacks.Callback;
import com.javared.future.callbacks.EmptyCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Created by Mamot on 3/14/2017.
 */
public interface RedFuture {

    RedFuture addSuccessCallback(EmptyCallback callback);

    RedFuture addSuccessCallback(Executor executor, EmptyCallback callback);

    RedFuture addFailureCallback(Callback<Throwable> callback);

    RedFuture addFailureCallback(Executor executor, Callback<Throwable> callback);

    RedFuture addFinallyCallback(EmptyCallback callback);

    RedFuture addFinallyCallback(Executor executor, EmptyCallback callback);

    ListenableFuture<?> getListenableFuture();

    void waitForCompletion() throws ExecutionException, InterruptedException;

    boolean isResolved();

    // Constructors

    static OpenRedFuture future() {
        return new OpenRedFuture();
    }

    static <T> OpenRedFutureOf<T> futureOf() {
        return new OpenRedFutureOf<>();
    }

    static RedFuture resolved() {
        OpenRedFuture future = future();
        future.resolve();
        return future;
    }

    static <T> RedFutureOf<T> resolvedOf(T value) {
        OpenRedFutureOf<T> future = futureOf();
        future.resolve(value);
        return future;
    }

    static RedFuture failed(Throwable t) {
        OpenRedFuture future = future();
        future.fail(t);
        return future;
    }

    static <T> RedFutureOf<T> failedOf(Throwable t) {
        OpenRedFutureOf<T> future = futureOf();
        future.fail(t);
        return future;
    }

    static RedFutureHub hub() {
        return new RedFutureHub();
    }

}
