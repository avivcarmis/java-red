package com.javared.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.callbacks.EmptyCallback;
import com.javared.future.callbacks.TypedCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Created by Mamot on 3/14/2017.
 */
public interface RedFutureOf<T> extends RedFuture {

    RedFutureOf<T> addSuccessCallback(TypedCallback<T> callback);

    RedFutureOf<T> addSuccessCallback(Executor executor, TypedCallback<T> callback);

    T tryGet();

    T waitAndGet() throws ExecutionException, InterruptedException;

    @Override
    RedFutureOf<T> addSuccessCallback(EmptyCallback callback);

    @Override
    RedFutureOf<T> addSuccessCallback(Executor executor, EmptyCallback callback);

    @Override
    RedFutureOf<T> addFailureCallback(TypedCallback<Throwable> callback);

    @Override
    RedFutureOf<T> addFailureCallback(Executor executor, TypedCallback<Throwable> callback);

    @Override
    RedFutureOf<T> addFinallyCallback(EmptyCallback callback);

    @Override
    RedFutureOf<T> addFinallyCallback(Executor executor, EmptyCallback callback);

    @Override
    ListenableFuture<T> getListenableFuture();

}
