package com.javared.future;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mamot on 3/15/2017.
 */
abstract class FutureTest {

    private static final String SCHEDULER_THREAD_NAME = "SCHEDULER_THREAD_NAME";

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1, r -> {
        Thread thread = new Thread(r);
        thread.setName(SCHEDULER_THREAD_NAME);
        return thread;
    });

    boolean isCallbackThread() {
        return Thread.currentThread().getName().equals(SCHEDULER_THREAD_NAME);
    }

    RedFuture success(long timeout) {
        OpenRedFuture future = RedFuture.future();
        SCHEDULER.schedule((Runnable) future::resolve, timeout, TimeUnit.MILLISECONDS);
        return future;
    }

    <T> RedFutureOf<T> successOf(long timeout, T value) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        SCHEDULER.schedule(() -> future.resolve(value), timeout, TimeUnit.MILLISECONDS);
        return future;
    }

    RedFuture failure(long timeout, Throwable throwable) {
        OpenRedFuture future = RedFuture.future();
        SCHEDULER.schedule(() -> future.fail(throwable), timeout, TimeUnit.MILLISECONDS);
        return future;
    }

    <T> RedFutureOf<T> failureOf(long timeout, Throwable throwable) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        SCHEDULER.schedule(() -> future.fail(throwable), timeout, TimeUnit.MILLISECONDS);
        return future;
    }

    /**
     * Created by Mamot on 3/15/2017.
     */
    public static class TestException extends Exception {}
}
