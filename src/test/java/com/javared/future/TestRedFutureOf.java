package com.javared.future;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Mamot on 3/15/2017.
 */
public class TestRedFutureOf extends FutureTest {

    private static final String TEST_THREAD_NAME = "TEST_THREAD_NAME";

    private static final Executor TEST_EXECUTOR = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName(TEST_THREAD_NAME);
        return thread;
    });

    @Test
    public void testSuccessOnCurrentThread() throws Throwable {
        CountDownLatch lock = new CountDownLatch(3);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        long time = System.currentTimeMillis();
        Object object = new Object();
        RedFutureOf<Object> future = successOf(100, object);
        future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
        future.addSuccessCallback(() -> {
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("success too soon"));
            }
            if (!isCallbackThread()) {
                failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addSuccessCallback(o -> {
            if (o != object) {
                failure.compareAndSet(null, new RuntimeException("unexpected future value"));
            }
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("success too soon"));
            }
            if (!isCallbackThread()) {
                failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addFinallyCallback(() -> {
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("finally too soon"));
            }
            if (!isCallbackThread()) {
                failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
            }
            lock.countDown();
        });
        lock.await(300, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        if (failure.get() != null) {
            throw failure.get();
        }
    }

    @Test
    public void testFailureOnCurrentThread() throws Throwable {
        CountDownLatch lock = new CountDownLatch(2);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        long time = System.currentTimeMillis();
        RedFutureOf<Object> future = failureOf(100, new TestException());
        future.addFailureCallback(throwable -> {
            if (!(throwable instanceof TestException)) {
                failure.compareAndSet(null, throwable);
            }
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("success too soon"));
            }
            if (!isCallbackThread()) {
                failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addFinallyCallback(() -> {
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("finally too soon"));
            }
            if (!isCallbackThread()) {
                failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addSuccessCallback(() ->
                failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
        future.addSuccessCallback(o ->
                failure.compareAndSet(null, new RuntimeException("should not have reached result success block")));
        lock.await(300, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        if (failure.get() != null) {
            throw failure.get();
        }
    }

    @Test
    public void testSuccessOnTestThread() throws Throwable {
        CountDownLatch lock = new CountDownLatch(3);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        long time = System.currentTimeMillis();
        Object object = new Object();
        RedFutureOf<Object> future = successOf(100, object);
        future.addFailureCallback(TEST_EXECUTOR, throwable -> failure.compareAndSet(null, throwable));
        future.addSuccessCallback(TEST_EXECUTOR, () -> {
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("success too soon"));
            }
            if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addSuccessCallback(TEST_EXECUTOR, o -> {
            if (o != object) {
                failure.compareAndSet(null, new RuntimeException("unexpected future value"));
            }
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("success too soon"));
            }
            if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addFinallyCallback(TEST_EXECUTOR, () -> {
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("finally too soon"));
            }
            if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
            }
            lock.countDown();
        });
        lock.await(300, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        if (failure.get() != null) {
            throw failure.get();
        }
    }

    @Test
    public void testFailureOnTestThread() throws Throwable {
        CountDownLatch lock = new CountDownLatch(2);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        long time = System.currentTimeMillis();
        RedFutureOf<Object> future = failureOf(100, new TestException());
        future.addFailureCallback(TEST_EXECUTOR, throwable -> {
            if (!(throwable instanceof TestException)) {
                failure.compareAndSet(null, throwable);
            }
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("success too soon"));
            }
            if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addFinallyCallback(TEST_EXECUTOR, () -> {
            if (System.currentTimeMillis() - time < 100) {
                failure.compareAndSet(null, new RuntimeException("finally too soon"));
            }
            if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
            }
            lock.countDown();
        });
        future.addSuccessCallback(TEST_EXECUTOR, () ->
                failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
        future.addSuccessCallback(o ->
                failure.compareAndSet(null, new RuntimeException("should not have reached result success block")));
        lock.await(300, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        if (failure.get() != null) {
            throw failure.get();
        }
    }

}
