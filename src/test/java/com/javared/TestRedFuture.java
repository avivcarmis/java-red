package com.javared;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.javared.future.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Mamot on 3/15/2017.
 */
@RunWith(Enclosed.class)
public class TestRedFuture {

    private static final long FUTURE_SLEEP_TIME = 100;

    private static final long VALIDATION_SLEEP_TIME = 100;

    public static class TestFutureCallbacks {

        // success on current thread

        @Test
        public void testFutureSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = successfulFuture();
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(successfulFuture());
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowListenableSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(successfulListenableFuture(null));
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        // failure on current thread

        @Test
        public void testFutureFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = failingFuture(new TestException());
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(() ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(failingFuture(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(() ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowListenableFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(failingListenableFuture(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(() ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        // success on test thread thread

        @Test
        public void testFutureSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = successfulFuture();
            future.addFailureCallback(TEST_EXECUTOR, throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(TEST_EXECUTOR, () -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(TEST_EXECUTOR, () -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(TEST_EXECUTOR, successfulFuture());
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowListenableSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(TEST_EXECUTOR, successfulListenableFuture(null));
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        // failure on test thread thread

        @Test
        public void testFutureFailureOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = failingFuture(new TestException());
            future.addFailureCallback(TEST_EXECUTOR, throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(TEST_EXECUTOR, () -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(TEST_EXECUTOR, () ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowFailureOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(TEST_EXECUTOR, failingFuture(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(() ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureFollowListenableFailureOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFuture future = RedFuture.future().follow(TEST_EXECUTOR, failingListenableFuture(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(() ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

    }

    public static class TestFutureOfCallbacks {

        // success on current thread

        @Test
        public void testFutureOfSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = successfulFutureOf(object);
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(successfulFutureOf(object));
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowListenableSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(successfulListenableFuture(object));
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        // failure on current thread

        @Test
        public void testFutureOfFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFutureOf<Object> future = failingFutureOf(new TestException());
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(failingFutureOf(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowListenableFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(failingFutureOf(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!isCallbackThread()) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        // success on test thread thread

        @Test
        public void testFutureOfSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = successfulFutureOf(object);
            future.addFailureCallback(TEST_EXECUTOR, throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(TEST_EXECUTOR, () -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(TEST_EXECUTOR, () -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(TEST_EXECUTOR, successfulFutureOf(object));
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(o -> {
                if (o != object) {
                    failure.compareAndSet(null, new RuntimeException("unexpected future value"));
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowListenableSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(TEST_EXECUTOR, successfulListenableFuture(object));
            future.addFailureCallback(throwable -> failure.compareAndSet(null, throwable));
            future.addSuccessCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(o -> {
                if (o != object) {
                    failure.compareAndSet(null, new RuntimeException("unexpected future value"));
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        // failure on test thread thread

        @Test
        public void testFutureOfFailureOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFutureOf<Object> future = failingFutureOf(new TestException());
            future.addFailureCallback(TEST_EXECUTOR, throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(TEST_EXECUTOR, () -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
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
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(TEST_EXECUTOR, failingFutureOf(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(() ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            future.addSuccessCallback(o ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached result success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

        @Test
        public void testFutureOfFollowListenableOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            RedFutureOf<Object> future = RedFuture.futureOf().follow(TEST_EXECUTOR, failingListenableFuture(new TestException()));
            future.addFailureCallback(throwable -> {
                if (!(throwable instanceof TestException)) {
                    failure.compareAndSet(null, throwable);
                }
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("success too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addFinallyCallback(() -> {
                if (System.currentTimeMillis() - time < FUTURE_SLEEP_TIME) {
                    failure.compareAndSet(null, new RuntimeException("finally too soon"));
                }
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            future.addSuccessCallback(() ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached success block")));
            future.addSuccessCallback(o ->
                    failure.compareAndSet(null, new RuntimeException("should not have reached result success block")));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
        }

    }

    public static class TestResolving {

        // future post callbacks

        @Test
        public void testFutureResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.resolve();
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFutureTryResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.tryResolve();
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFutureFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            Throwable throwable = new TestException();
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.fail(throwable);
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
        }

        @Test
        public void testFutureTryFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            Throwable throwable = new TestException();
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.tryFail(throwable);
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
        }

        // future of post callbacks

        @Test
        public void testFutureOfResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Object object = new Object();
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> {
                if (o == object) {
                    correctValueReturned.set(true);
                }
                valueSuccessBlockReached.set(true);
            });
            future.resolve(object);
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(correctValueReturned.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFutureOfTryResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Object object = new Object();
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> {
                if (o == object) {
                    correctValueReturned.set(true);
                }
                valueSuccessBlockReached.set(true);
            });
            future.tryResolve(object);
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(correctValueReturned.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFutureOfFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Throwable throwable = new TestException();
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> valueSuccessBlockReached.set(true));
            future.fail(throwable);
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), false);
        }

        @Test
        public void testFutureOfTryFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Throwable throwable = new TestException();
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> valueSuccessBlockReached.set(true));
            future.tryFail(throwable);
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), false);
        }

        // future pre callbacks

        @Test
        public void testFuturePreResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            future.resolve();
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFuturePreTryResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            future.tryResolve();
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFuturePreFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            Throwable throwable = new TestException();
            future.fail(throwable);
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
        }

        @Test
        public void testFuturePreTryFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            OpenRedFuture future = RedFuture.future();
            Throwable throwable = new TestException();
            future.tryFail(throwable);
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
        }

        // future of pre callbacks

        @Test
        public void testFutureOfPreResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Object object = new Object();
            future.resolve(object);
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> {
                if (o == object) {
                    correctValueReturned.set(true);
                }
                valueSuccessBlockReached.set(true);
            });
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(correctValueReturned.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFutureOfPreTryResolve() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Object object = new Object();
            future.tryResolve(object);
            future.addFailureCallback(throwable -> failureBlockReached.set(true));
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> {
                if (o == object) {
                    correctValueReturned.set(true);
                }
                valueSuccessBlockReached.set(true);
            });
            Assert.assertEquals(successBlockReached.get(), true);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(correctValueReturned.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(failureBlockReached.get(), false);
        }

        @Test
        public void testFutureOfPreFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Throwable throwable = new TestException();
            future.fail(throwable);
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> valueSuccessBlockReached.set(true));
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), false);
        }

        @Test
        public void testFutureOfPreTryFail() throws Throwable {
            AtomicBoolean failureBlockReached = new AtomicBoolean(false);
            AtomicBoolean correctThrowableReturned = new AtomicBoolean(false);
            AtomicBoolean successBlockReached = new AtomicBoolean(false);
            AtomicBoolean finallyBlockReached = new AtomicBoolean(false);
            AtomicBoolean valueSuccessBlockReached = new AtomicBoolean(false);
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Throwable throwable = new TestException();
            future.tryFail(throwable);
            future.addFailureCallback(aThrowable -> {
                if (throwable == aThrowable) {
                    correctThrowableReturned.set(true);
                }
                failureBlockReached.set(true);
            });
            future.addSuccessCallback(() -> successBlockReached.set(true));
            future.addFinallyCallback(() -> finallyBlockReached.set(true));
            future.addSuccessCallback(o -> valueSuccessBlockReached.set(true));
            Assert.assertEquals(failureBlockReached.get(), true);
            Assert.assertEquals(correctThrowableReturned.get(), true);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertEquals(successBlockReached.get(), false);
            Assert.assertEquals(finallyBlockReached.get(), true);
            Assert.assertEquals(valueSuccessBlockReached.get(), false);
        }

    }

    public static class TestWaitOperations {

        @Test
        public void testIsResolved() throws Throwable {
            OpenRedFuture future = RedFuture.future();
            Assert.assertFalse(future.isResolved());
            future.resolve();
            Assert.assertTrue(future.isResolved());
        }

        @Test
        public void testWaitForCompletion() throws Throwable {
            long time = System.currentTimeMillis();
            RedFuture future = successfulFuture();
            future.waitForCompletion();
            Assert.assertTrue(System.currentTimeMillis() >= time + FUTURE_SLEEP_TIME);
            Assert.assertTrue(future.isResolved());
        }

        @Test
        public void testTryGet() throws Throwable {
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Assert.assertNull(future.tryGet());
            Object object = new Object();
            future.resolve(object);
            Assert.assertEquals(future.tryGet(), object);
        }

        @Test
        public void testWaitAndGet() throws Throwable {
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = successfulFutureOf(object);
            Object value = future.waitAndGet();
            Assert.assertTrue(System.currentTimeMillis() >= time + FUTURE_SLEEP_TIME);
            Assert.assertEquals(object, value);
        }

    }

    public static class TestFutureHub {

        // optimistic

        @Test
        public void testOptimisticProvidePostResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            futureOf.resolve(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticProvidePostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.fail(new TestException());
            futureOf.resolve(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticProvidePreResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            future1.resolve();
            future2.resolve();
            futureOf.resolve(new Object());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticProvidePreResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            future1.resolve();
            future2.fail(new TestException());
            futureOf.resolve(new Object());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptPostResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptPostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.setException(new TestException());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptPreResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptPreResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.fail(new TestException());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptProvidePostResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFuture future4 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            future3.resolve();
            future4.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptProvidePostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFuture future4 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            future3.resolve();
            future4.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.setException(new TestException());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptProvidePreResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFuture future4 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            future1.resolve();
            future2.resolve();
            future3.resolve();
            future4.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testOptimisticAdoptProvidePreResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFuture future4 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            future1.resolve();
            future2.resolve();
            future3.resolve();
            future4.resolve();
            futureOf1.resolve(new Object());
            futureOf2.fail(new TestException());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        // pessimistic

        @Test
        public void testPessimisticProvidePostResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            futureOf.resolve(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticProvidePostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            hub.provideFutureOf();
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future2.fail(new TestException());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticProvidePreResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            future1.resolve();
            future2.resolve();
            futureOf.resolve(new Object());
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticProvidePreResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            hub.provideFutureOf();
            future2.fail(new TestException());
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptPostResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptPostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            settableFuture1.setException(new TestException());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptPreResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptPreResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            futureOf2.fail(new TestException());
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptProvidePostResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFuture future4 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            future1.resolve();
            future2.resolve();
            future3.resolve();
            future4.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptProvidePostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            hub.provideFuture();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            settableFuture1.setException(new TestException());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptProvidePreResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = RedFuture.future();
            OpenRedFuture future2 = RedFuture.future();
            OpenRedFuture future3 = RedFuture.future();
            OpenRedFuture future4 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
            SettableFuture<Object> settableFuture1 = SettableFuture.create();
            SettableFuture<Object> settableFuture2 = SettableFuture.create();
            SettableFuture<Object> settableFuture3 = SettableFuture.create();
            SettableFuture<Object> settableFuture4 = SettableFuture.create();
            SettableFuture<Object> settableFuture5 = SettableFuture.create();
            List<RedFuture> redFutureCollection = new LinkedList<>();
            redFutureCollection.add(future2);
            redFutureCollection.add(futureOf1);
            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
            listenableFutureCollection.add(settableFuture1);
            listenableFutureCollection.add(settableFuture2);
            hub.adoptFuture(future1);
            hub.adoptFutures(redFutureCollection);
            hub.adoptFutures(future3, futureOf2);
            hub.adoptListenableFuture(settableFuture3);
            hub.adoptListenableFutures(listenableFutureCollection);
            hub.adoptListenableFutures(settableFuture4, settableFuture5);
            future1.resolve();
            future2.resolve();
            future3.resolve();
            future4.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        @Test
        public void testPessimisticAdoptProvidePreResolveFailure() throws Throwable {
//            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
//            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
//            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
//            RedFutureHub hub = RedFuture.hub();
//            OpenRedFuture future1 = RedFuture.future();
//            OpenRedFuture future2 = RedFuture.future();
//            OpenRedFuture future3 = RedFuture.future();
//            hub.provideFuture();
//            OpenRedFutureOf<Object> futureOf1 = hub.provideFutureOf();
//            OpenRedFutureOf<Object> futureOf2 = hub.provideFutureOf();
//            SettableFuture<Object> settableFuture1 = SettableFuture.create();
//            SettableFuture<Object> settableFuture2 = SettableFuture.create();
//            SettableFuture<Object> settableFuture3 = SettableFuture.create();
//            SettableFuture<Object> settableFuture4 = SettableFuture.create();
//            SettableFuture<Object> settableFuture5 = SettableFuture.create();
//            List<RedFuture> redFutureCollection = new LinkedList<>();
//            redFutureCollection.add(future2);
//            redFutureCollection.add(futureOf1);
//            List<ListenableFuture> listenableFutureCollection = new LinkedList<>();
//            listenableFutureCollection.add(settableFuture1);
//            listenableFutureCollection.add(settableFuture2);
//            hub.adoptFuture(future1);
//            hub.adoptFutures(redFutureCollection);
//            hub.adoptFutures(future3, futureOf2);
//            hub.adoptListenableFuture(settableFuture3);
//            hub.adoptListenableFutures(listenableFutureCollection);
//            hub.adoptListenableFutures(settableFuture4, settableFuture5);
//            futureOf2.fail(new TestException());
//            RedFuture union = hub.unitePessimistically();
//            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
//            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
//            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
//            Assert.assertTrue(reachedFinallyBlock.get());
//            Assert.assertFalse(reachedSuccessBlock.get());
//            Assert.assertTrue(reachedFailureBlock.get());
            RedFutureHub hub = RedFuture.hub();
//            hub.provideFuture();
            OpenRedFutureOf<Object> future = hub.provideFutureOf();
            hub.adoptFuture(RedFuture.future());
            future.fail(new TestException());
            hub.unitePessimistically().addFailureCallback(throwable -> {
                int i = 1;
            });
            Thread.sleep(100);
        }

    }

    // utils

    private static boolean isCallbackThread() {
        return Thread.currentThread().getName().equals(SCHEDULER_THREAD_NAME);
    }

    private static RedFuture successfulFuture() {
        OpenRedFuture future = RedFuture.future();
        SCHEDULER.schedule(future::resolve, FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    private static <T> RedFutureOf<T> successfulFutureOf(T value) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        SCHEDULER.schedule(() -> future.resolve(value), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    private static <T> ListenableFuture<T> successfulListenableFuture(T value) {
        SettableFuture<T> future = SettableFuture.create();
        SCHEDULER.schedule(() -> future.set(value), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    private static RedFuture failingFuture(Throwable throwable) {
        OpenRedFuture future = RedFuture.future();
        SCHEDULER.schedule(() -> future.fail(throwable), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    private static <T> RedFutureOf<T> failingFutureOf(Throwable throwable) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        SCHEDULER.schedule(() -> future.fail(throwable), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    private static <T> ListenableFuture<T> failingListenableFuture(Throwable throwable) {
        SettableFuture<T> future = SettableFuture.create();
        SCHEDULER.schedule(() -> future.setException(throwable), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    private static final String SCHEDULER_THREAD_NAME = "SCHEDULER_THREAD_NAME";

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1, r -> {
        Thread thread = new Thread(r);
        thread.setName(SCHEDULER_THREAD_NAME);
        return thread;
    });

    private static final String TEST_THREAD_NAME = "TEST_THREAD_NAME";

    private static final Executor TEST_EXECUTOR = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName(TEST_THREAD_NAME);
        return thread;
    });

    static class TestException extends Exception {}
}
