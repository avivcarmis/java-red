package io.github.avivcarmis.javared;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.github.avivcarmis.javared.future.*;
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
 * Runs different test suits to test the entire functionality of {@link RedFuture},
 * {@link RedFutureOf}, {@link OpenRedFuture}, {@link OpenRedFutureOf} and {@link RedFutureHub}.
 */
@RunWith(Enclosed.class)
public class TestRedFuture {

    /**
     * Millisecond delay time for resolving of all test futures.
     */
    private static final long FUTURE_SLEEP_TIME = 100;

    /**
     * Millisecond duration for sleeping at the end of tests
     * to make sure no extra callbacks are made.
     */
    private static final long VALIDATION_SLEEP_TIME = 100;

    /**
     * Test the attaching and invocation of all {@link RedFuture} callbacks
     */
    public static class TestFutureCallbacks {

        // Success on current thread

        /**
         * Tests successfully resolved {@link RedFuture} callbacks on current thread.
         */
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

        /**
         * Tests successfully following {@link RedFuture} callbacks on current thread.
         */
        @Test
        public void testFutureFollowSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(successfulFuture());
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

        /**
         * Tests successfully following {@link RedFuture} callbacks on current thread.
         */
        @Test
        public void testFutureFollowListenableSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(successfulListenableFuture(null));
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

        /**
         * Tests failed {@link RedFuture} callbacks on current thread.
         */
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

        /**
         * Tests failed following {@link RedFuture} callbacks on current thread.
         */
        @Test
        public void testFutureFollowFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(failingFuture(new TestException()));
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

        /**
         * Tests failed following {@link RedFuture} callbacks on current thread.
         */
        @Test
        public void testFutureFollowListenableFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(failingListenableFuture(new TestException()));
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

        /**
         * Tests successfully resolved {@link RedFuture} callbacks on current thread.
         */
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

        /**
         * Tests successfully following {@link RedFuture} callbacks on a given thread.
         */
        @Test
        public void testFutureFollowSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(TEST_EXECUTOR, successfulFuture());
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

        /**
         * Tests successfully following {@link RedFuture} callbacks on a given thread.
         */
        @Test
        public void testFutureFollowListenableSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(TEST_EXECUTOR, successfulListenableFuture(null));
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

        /**
         * Tests failed {@link RedFuture} callbacks on a given thread.
         */
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

        /**
         * Tests failed following {@link RedFuture} callbacks on a given thread.
         */
        @Test
        public void testFutureFollowFailureOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(TEST_EXECUTOR, failingFuture(new TestException()));
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

        /**
         * Tests failed following {@link RedFuture} callbacks on a given thread.
         */
        @Test
        public void testFutureFollowListenableFailureOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFuture future = RedFuture.future();
            future.follow(TEST_EXECUTOR, failingListenableFuture(new TestException()));
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

    /**
     * Test the attaching and invocation of all {@link RedFutureOf} callbacks
     */
    public static class TestFutureOfCallbacks {

        // success on current thread

        /**
         * Tests successfully resolved {@link RedFutureOf} callbacks on current thread.
         */
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

        /**
         * Tests successfully following {@link RedFutureOf} callbacks on current thread.
         */
        @Test
        public void testFutureOfFollowSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(successfulFutureOf(object));
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

        /**
         * Tests successfully following {@link RedFutureOf} callbacks on current thread.
         */
        @Test
        public void testFutureOfFollowListenableSuccessOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(successfulListenableFuture(object));
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

        /**
         * Tests failed {@link RedFutureOf} callbacks on current thread.
         */
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

        /**
         * Tests failed following {@link RedFutureOf} callbacks on current thread.
         */
        @Test
        public void testFutureOfFollowFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(failingFutureOf(new TestException()));
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

        /**
         * Tests failed following {@link RedFutureOf} callbacks on current thread.
         */
        @Test
        public void testFutureOfFollowListenableFailureOnCurrentThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(failingFutureOf(new TestException()));
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

        /**
         * Tests successfully resolved {@link RedFutureOf} callbacks on current thread.
         */
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

        /**
         * Tests successfully following {@link RedFutureOf} callbacks on a given thread.
         */
        @Test
        public void testFutureOfFollowSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(TEST_EXECUTOR, successfulFutureOf(object));
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

        /**
         * Tests successfully following {@link RedFutureOf} callbacks on a given thread.
         */
        @Test
        public void testFutureOfFollowListenableSuccessOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            Object object = new Object();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(TEST_EXECUTOR, successfulListenableFuture(object));
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

        /**
         * Tests failed {@link RedFutureOf} callbacks on a given thread.
         */
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

        /**
         * Tests failed following {@link RedFutureOf} callbacks on a given thread.
         */
        @Test
        public void testFutureOfFollowOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(TEST_EXECUTOR, failingFutureOf(new TestException()));
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

        /**
         * Tests failed following {@link RedFutureOf} callbacks on a given thread.
         */
        @Test
        public void testFutureOfFollowListenableOnTestThread() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            long time = System.currentTimeMillis();
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            future.follow(TEST_EXECUTOR, failingListenableFuture(new TestException()));
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

    /**
     * Test resolving and failing functionality of {@link OpenRedFuture} and {@link OpenRedFutureOf} classes
     */
    public static class TestResolving {

        // future post callbacks

        /**
         * Test the resolving of {@link OpenRedFuture} through {@link OpenRedFuture#resolve()}
         */
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

        /**
         * Test the resolving of {@link OpenRedFuture} through {@link OpenRedFuture#tryResolve()}
         */
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

        /**
         * Test the failing of {@link OpenRedFuture} through {@link OpenRedFuture#fail(Throwable)}
         */
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

        /**
         * Test the failing of {@link OpenRedFuture} through {@link OpenRedFuture#tryFail(Throwable)}
         */
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

        /**
         * Test the resolving of {@link OpenRedFutureOf} through {@link OpenRedFutureOf#resolve(Object)}
         */
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

        /**
         * Test the resolving of {@link OpenRedFutureOf} through {@link OpenRedFutureOf#tryResolve(Object)}
         */
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

        /**
         * Test the failing of {@link OpenRedFutureOf} through {@link OpenRedFutureOf#fail(Throwable)}
         */
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

        /**
         * Test the failing of {@link OpenRedFutureOf} through {@link OpenRedFutureOf#tryFail(Throwable)}
         */
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

        /**
         * Test to validate invocation of callbacks registering after resolve of {@link OpenRedFuture}
         */
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

        /**
         * Test to validate invocation of callbacks registering after try-resolve of {@link OpenRedFuture}
         */
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

        /**
         * Test to validate invocation of callbacks registering after fail of {@link OpenRedFuture}
         */
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

        /**
         * Test to validate invocation of callbacks registering after try-fail of {@link OpenRedFuture}
         */
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

        /**
         * Test to validate invocation of callbacks registering after resolve of {@link OpenRedFutureOf}
         */
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

        /**
         * Test to validate invocation of callbacks registering after try-resolve of {@link OpenRedFutureOf}
         */
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

        /**
         * Test to validate invocation of callbacks registering after fail of {@link OpenRedFutureOf}
         */
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

        /**
         * Test to validate invocation of callbacks registering after try-fail of {@link OpenRedFutureOf}
         */
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

    /**
     * Test all waiting operations of {@link RedFuture} and {@link RedFutureOf} classes
     */
    public static class TestWaitOperations {

        // RedFuture wait

        /**
         * Test the functionality of {@link RedFuture#isResolved()}
         */
        @Test
        public void testIsResolved() throws Throwable {
            OpenRedFuture future = RedFuture.future();
            Assert.assertFalse(future.isResolved());
            future.resolve();
            Assert.assertTrue(future.isResolved());
        }

        /**
         * Test the functionality of {@link RedFuture#waitForCompletion()}
         */
        @Test
        public void testWaitForCompletionNoTimeout() throws Throwable {
            long time = System.currentTimeMillis();
            RedFuture future = successfulFuture();
            future.waitForCompletion();
            Assert.assertTrue(System.currentTimeMillis() >= time + FUTURE_SLEEP_TIME);
            Assert.assertTrue(future.isResolved());
        }

        /**
         * Test the success of {@link RedFuture#waitForCompletion(long, TimeUnit)}
         */
        @Test
        public void testWaitForCompletionTimeoutSuccess() throws Throwable {
            long time = System.currentTimeMillis();
            RedFuture future = successfulFuture();
            future.waitForCompletion(FUTURE_SLEEP_TIME * 2, TimeUnit.MILLISECONDS);
            Assert.assertTrue(System.currentTimeMillis() >= time + FUTURE_SLEEP_TIME);
            Assert.assertTrue(future.isResolved());
        }

        /**
         * Test the timeout of {@link RedFuture#waitForCompletion(long, TimeUnit)}
         */
        @Test(expected = TimeoutException.class)
        public void testWaitForCompletionTimeoutFailure() throws Throwable {
            successfulFuture().waitForCompletion(Math.round(FUTURE_SLEEP_TIME * 0.3), TimeUnit.MILLISECONDS);
        }

        // RedFutureOf wait

        /**
         * Test the functionality of {@link RedFutureOf#tryGet()}
         */
        @Test
        public void testTryGet() throws Throwable {
            OpenRedFutureOf<Object> future = RedFuture.futureOf();
            Assert.assertNull(future.tryGet());
            Object object = new Object();
            future.resolve(object);
            Assert.assertEquals(future.tryGet(), object);
        }

        /**
         * Test the functionality of {@link RedFutureOf#waitAndGet()}
         */
        @Test
        public void testWaitAndGetNoTimeout() throws Throwable {
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = successfulFutureOf(object);
            Object value = future.waitAndGet();
            Assert.assertTrue(System.currentTimeMillis() >= time + FUTURE_SLEEP_TIME);
            Assert.assertEquals(object, value);
        }

        /**
         * Test the success of {@link RedFutureOf#waitAndGet(long, TimeUnit)}
         */
        @Test
        public void testWaitAndGetTimeoutSuccess() throws Throwable {
            long time = System.currentTimeMillis();
            Object object = new Object();
            RedFutureOf<Object> future = successfulFutureOf(object);
            Object value = future.waitAndGet(FUTURE_SLEEP_TIME * 2, TimeUnit.MILLISECONDS);
            Assert.assertTrue(System.currentTimeMillis() >= time + FUTURE_SLEEP_TIME);
            Assert.assertEquals(object, value);
        }

        /**
         * Test the timeout of {@link RedFutureOf#waitAndGet(long, TimeUnit)}
         */
        @Test(expected = TimeoutException.class)
        public void testWaitAndGetTimeoutFailure() throws Throwable {
            successfulFutureOf(new Object()).waitAndGet(Math.round(FUTURE_SLEEP_TIME * 0.2), TimeUnit.MILLISECONDS);
        }

    }

    /**
     * Test additional static {@link RedFuture} constructors
     */
    public static class TestCompletedFutures {

        /**
         * Test the constructor of a resolved {@link RedFuture} through {@link RedFuture#resolved()}
         */
        @Test
        public void testResolvedFuture() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFuture future = RedFuture.resolved();
            future.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            future.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            future.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the constructor of a resolved {@link RedFutureOf} through {@link RedFuture#resolvedOf(Object)}
         */
        @Test
        public void testResolvedFutureOf() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            Object object = new Object();
            RedFutureOf<Object> future = RedFuture.resolvedOf(object);
            future.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            future.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            future.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            future.addSuccessCallback(o -> {
                correctValueReturned.set(o == object);
                reachedTypedSuccessBlock.set(true);
            });
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            Assert.assertTrue(reachedTypedSuccessBlock.get());
            Assert.assertTrue(correctValueReturned.get());
        }

        /**
         * Test the constructor of a failed {@link RedFuture} through {@link RedFuture#failed(Throwable)}
         */
        @Test
        public void testFailedFuture() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            TestException exception = new TestException();
            RedFutureOf<Object> future = RedFuture.failedOf(exception);
            future.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            future.addFailureCallback(throwable -> {
                correctValueReturned.set(throwable == exception);
                reachedFailureBlock.set(true);
            });
            future.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            future.addSuccessCallback(o -> reachedTypedSuccessBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedTypedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
            Assert.assertTrue(correctValueReturned.get());
        }

        /**
         * Test the constructor of a failed {@link RedFutureOf} through {@link RedFuture#failedOf(Throwable)}
         */
        @Test
        public void testFailedFutureOf() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            TestException exception = new TestException();
            RedFuture future = RedFuture.failed(exception);
            future.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            future.addFailureCallback(throwable -> {
                correctValueReturned.set(throwable == exception);
                reachedFailureBlock.set(true);
            });
            future.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
            Assert.assertTrue(correctValueReturned.get());
        }

    }

    /**
     * Test functionality of {@link RedFutureHub} class
     */
    public static class TestFutureHub {

        // Optimistic - provide

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided futures that later were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided futures that later were failed
         */
        @Test
        public void testOptimisticProvidePostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            hub.provideFutureOf();
            RedFuture union = hub.uniteOptimistically();
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided futures that previously were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided futures that previously were failed
         */
        @Test
        public void testOptimisticProvidePreResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            hub.provideFutureOf();
            future2.fail(new TestException());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        // Optimistic - adopt

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of adopted futures that later were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of adopted futures that later were failed
         */
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
            settableFuture1.setException(new TestException());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of adopted futures that previously were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of adopted futures that previously were failed
         */
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
            futureOf2.fail(new TestException());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        // Optimistic - provide and adopt

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided and adopted futures that later were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided and adopted futures that later were failed
         */
        @Test
        public void testOptimisticAdoptProvidePostResolveFailure() throws Throwable {
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
            RedFuture union = hub.uniteOptimistically();
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided and adopted futures that previously were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * optimistic union of provided and adopted futures that previously were failed
         */
        @Test
        public void testOptimisticAdoptProvidePreResolveFailure() throws Throwable {
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
            futureOf2.fail(new TestException());
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        // Pessimistic - provide

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided futures that later were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided futures that later were failed
         */
        @Test
        public void testPessimisticProvidePostResolveFailure() throws Throwable {
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
            future2.fail(new TestException());
            futureOf.resolve(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided futures that previously were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided futures that previously were failed
         */
        @Test
        public void testPessimisticProvidePreResolveFailure() throws Throwable {
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
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        // Pessimistic - adopt

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of adopted futures that later were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of adopted futures that later were failed
         */
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
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of adopted futures that previously were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of adopted futures that previously were failed
         */
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
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        // Pessimistic - provide and adopt

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided and adopted futures that later were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided and adopted futures that later were failed
         */
        @Test
        public void testPessimisticAdoptProvidePostResolveFailure() throws Throwable {
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
            settableFuture1.setException(new TestException());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided and adopted futures that previously were successfully resolved
         */
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

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * pessimistic union of provided and adopted futures that previously were failed
         */
        @Test
        public void testPessimisticAdoptProvidePreResolveFailure() throws Throwable {
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
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        // Cautious - provide

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided futures that later were successfully resolved
         */
        @Test
        public void testCautiousProvidePostResolveSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            future1.resolve();
            future2.resolve();
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            futureOf.resolve(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided futures that later were failed
         */
        @Test
        public void testCautiousProvidePostResolveFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            OpenRedFuture future1 = hub.provideFuture();
            OpenRedFuture future2 = hub.provideFuture();
            OpenRedFutureOf<Object> futureOf = hub.provideFutureOf();
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            future1.resolve();
            future2.fail(new TestException());
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            futureOf.resolve(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided futures that previously were successfully resolved
         */
        @Test
        public void testCautiousProvidePreResolveSuccess() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided futures that previously were failed
         */
        @Test
        public void testCautiousProvidePreResolveFailure() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        // Cautious - adopt

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of adopted futures that later were successfully resolved
         */
        @Test
        public void testCautiousAdoptPostResolveSuccess() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.set(new Object());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of adopted futures that later were failed
         */
        @Test
        public void testCautiousAdoptPostResolveFailure() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            future1.resolve();
            future2.resolve();
            future3.resolve();
            futureOf1.resolve(new Object());
            futureOf2.resolve(new Object());
            settableFuture1.setException(new TestException());
            settableFuture2.set(new Object());
            settableFuture3.set(new Object());
            settableFuture4.set(new Object());
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of adopted futures that previously were successfully resolved
         */
        @Test
        public void testCautiousAdoptPreResolveSuccess() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of adopted futures that previously were failed
         */
        @Test
        public void testCautiousAdoptPreResolveFailure() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        // Cautious - provide and adopt

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided and adopted futures that later were successfully resolved
         */
        @Test
        public void testCautiousAdoptProvidePostResolveSuccess() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
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
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided and adopted futures that later were failed
         */
        @Test
        public void testCautiousAdoptProvidePostResolveFailure() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
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
            Assert.assertFalse(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
            settableFuture5.set(new Object());
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided and adopted futures that previously were successfully resolved
         */
        @Test
        public void testCautiousAdoptProvidePreResolveSuccess() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the completion and callback invocation of {@link RedFutureHub}
         * cautious union of provided and adopted futures that previously were failed
         */
        @Test
        public void testCautiousAdoptProvidePreResolveFailure() throws Throwable {
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
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        // Empty hubs

        /**
         * Tests to validate completion and callback invocation of optimistic union
         * of a {@link RedFutureHub} with no tracked futures
         */
        @Test
        public void testOptimisticEmptyResolve() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            RedFuture union = hub.uniteOptimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Tests to validate completion and callback invocation of pessimistic union
         * of a {@link RedFutureHub} with no tracked futures
         */
        @Test
        public void testPessimisticEmptyResolve() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            RedFuture union = hub.unitePessimistically();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Tests to validate completion and callback invocation of cautious union
         * of a {@link RedFutureHub} with no tracked futures
         */
        @Test
        public void testCautiousEmptyResolve() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            RedFutureHub hub = RedFuture.hub();
            RedFuture union = hub.uniteCautiously();
            union.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            union.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            union.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

    }

    /**
     * Test conversions of supported futures to a {@link RedFuture}
     */
    public static class TestConversions {

        // Future to RedFuture

        /**
         * Test the conversion of successful {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future)}
         */
        @Test
        public void FutureConversionSuccess() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            Callable<String> callable = () -> "test";
            Future<String> javaFuture = TEST_EXECUTOR.submit(callable);
            RedFutureOf<String> redFuture = RedFuture.convert(javaFuture);
            redFuture.addSuccessCallback(() -> {
                reachedSuccessBlock.set(true);
                lock.countDown();
            });
            redFuture.addSuccessCallback(s -> {
                reachedTypedSuccessBlock.set(true);
                correctValueReturned.set(s.equals("test"));
                lock.countDown();
            });
            redFuture.addFinallyCallback(() -> {
                reachedFinallyBlock.set(true);
                lock.countDown();
            });
            redFuture.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertTrue(reachedTypedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the conversion of failing {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future)}
         */
        @Test
        public void FutureConversionFailure() throws Throwable {
            CountDownLatch lock = new CountDownLatch(2);
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            Callable<String> callable = () -> {
                throw new TestException();
            };
            Future<String> javaFuture = TEST_EXECUTOR.submit(callable);
            RedFutureOf<String> redFuture = RedFuture.convert(javaFuture);
            redFuture.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            redFuture.addSuccessCallback(s -> reachedTypedSuccessBlock.set(true));
            redFuture.addFinallyCallback(() -> {
                reachedFinallyBlock.set(true);
                lock.countDown();
            });
            redFuture.addFailureCallback(throwable -> {
                reachedFailureBlock.set(true);
                lock.countDown();
            });
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedTypedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        /**
         * Test the conversion of successful {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future, Executor)}
         */
        @Test
        public void FutureConversionWithExecutor() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicReference<Throwable> failure = new AtomicReference<>();
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            Callable<String> callable = () -> {
                Thread.sleep(100);
                return "test";
            };
            Future<String> javaFuture = TEST_EXECUTOR.submit(callable);
            RedFutureOf<String> redFuture = RedFuture.convert(javaFuture, TEST_EXECUTOR);
            redFuture.addSuccessCallback(() -> {
                reachedSuccessBlock.set(true);
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
                lock.countDown();
            });
            redFuture.addSuccessCallback(s -> {
                reachedTypedSuccessBlock.set(true);
                correctValueReturned.set(s.equals("test"));
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("typed success block on unexpected thread"));
                }
                lock.countDown();
            });
            redFuture.addFinallyCallback(() -> {
                reachedFinallyBlock.set(true);
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
                lock.countDown();
            });
            redFuture.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertTrue(reachedTypedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the conversion of an already resolved {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future)}
         */
        @Test
        public void ResolvedFutureConversion() throws Throwable {
            CountDownLatch lock = new CountDownLatch(3);
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            Callable<String> callable = () -> "test";
            Future<String> javaFuture = TEST_EXECUTOR.submit(callable);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            RedFutureOf<String> redFuture = RedFuture.convert(javaFuture, TEST_EXECUTOR);
            redFuture.addSuccessCallback(() -> {
                reachedSuccessBlock.set(true);
                lock.countDown();
            });
            redFuture.addSuccessCallback(s -> {
                reachedTypedSuccessBlock.set(true);
                correctValueReturned.set(s.equals("test"));
                lock.countDown();
            });
            redFuture.addFinallyCallback(() -> {
                reachedFinallyBlock.set(true);
                lock.countDown();
            });
            redFuture.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            lock.await(300, TimeUnit.MILLISECONDS);
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertTrue(reachedTypedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        // ListenableFuture to RedFuture

        /**
         * Test the conversion of successful {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future)}
         */
        @Test
        public void ListenableFutureConversionSuccess() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            SettableFuture<String> settableFuture = SettableFuture.create();
            RedFutureOf<String> redFuture = RedFuture.convert(settableFuture);
            redFuture.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            redFuture.addSuccessCallback(s -> {
                reachedTypedSuccessBlock.set(true);
                correctValueReturned.set(s.equals("test"));
            });
            redFuture.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            redFuture.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            settableFuture.set("test");
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertTrue(reachedTypedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the conversion of failing {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future)}
         */
        @Test
        public void ListenableFutureConversionFailure() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            SettableFuture<String> settableFuture = SettableFuture.create();
            RedFutureOf<String> redFuture = RedFuture.convert(settableFuture);
            redFuture.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            redFuture.addSuccessCallback(s -> reachedTypedSuccessBlock.set(true));
            redFuture.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            redFuture.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            settableFuture.setException(new TestException());
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertFalse(reachedSuccessBlock.get());
            Assert.assertFalse(reachedTypedSuccessBlock.get());
            Assert.assertTrue(reachedFailureBlock.get());
        }

        /**
         * Test the conversion of successful {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future, Executor)}
         */
        @Test
        public void ListenableFutureConversionWithExecutor() throws Throwable {
            AtomicReference<Throwable> failure = new AtomicReference<>();
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            SettableFuture<String> settableFuture = SettableFuture.create();
            RedFutureOf<String> redFuture = RedFuture.convert(settableFuture, TEST_EXECUTOR);
            redFuture.addSuccessCallback(() -> {
                reachedSuccessBlock.set(true);
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("success block on unexpected thread"));
                }
            });
            redFuture.addSuccessCallback(s -> {
                reachedTypedSuccessBlock.set(true);
                correctValueReturned.set(s.equals("test"));
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("typed success block on unexpected thread"));
                }
            });
            redFuture.addFinallyCallback(() -> {
                reachedFinallyBlock.set(true);
                if (!Thread.currentThread().getName().equals(TEST_THREAD_NAME)) {
                    failure.compareAndSet(null, new RuntimeException("finally block on unexpected thread"));
                }
            });
            redFuture.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            settableFuture.set("test");
            Thread.sleep(VALIDATION_SLEEP_TIME);
            if (failure.get() != null) {
                throw failure.get();
            }
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertTrue(reachedTypedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

        /**
         * Test the conversion of an already resolved {@link Future} to {@link RedFuture}
         * through {@link RedFuture#convert(Future)}
         */
        @Test
        public void ListenableFutureFutureConversion() throws Throwable {
            AtomicBoolean reachedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFailureBlock = new AtomicBoolean(false);
            AtomicBoolean reachedFinallyBlock = new AtomicBoolean(false);
            AtomicBoolean reachedTypedSuccessBlock = new AtomicBoolean(false);
            AtomicBoolean correctValueReturned = new AtomicBoolean(false);
            SettableFuture<String> settableFuture = SettableFuture.create();
            settableFuture.set("test");
            RedFutureOf<String> redFuture = RedFuture.convert(settableFuture, TEST_EXECUTOR);
            redFuture.addSuccessCallback(() -> reachedSuccessBlock.set(true));
            redFuture.addSuccessCallback(s -> {
                reachedTypedSuccessBlock.set(true);
                correctValueReturned.set(s.equals("test"));
            });
            redFuture.addFinallyCallback(() -> reachedFinallyBlock.set(true));
            redFuture.addFailureCallback(throwable -> reachedFailureBlock.set(true));
            Thread.sleep(VALIDATION_SLEEP_TIME);
            Assert.assertTrue(reachedFinallyBlock.get());
            Assert.assertTrue(reachedSuccessBlock.get());
            Assert.assertTrue(reachedTypedSuccessBlock.get());
            Assert.assertFalse(reachedFailureBlock.get());
        }

    }

    // Utils

    /**
     * Validates whether or not the current thread is named like the scheduler thread name.
     * The scheduler is responsible for delayed resolving of test futures (@see {@link #successfulFuture()}),
     * thus, if not otherwise requested, the thread invoking callback methods should be the same.
     */
    private static boolean isCallbackThread() {
        return Thread.currentThread().getName().equals(SCHEDULER_THREAD_NAME);
    }

    /**
     * @return a {@link RedFuture} that will be successfully resolved
     * within {@link TestRedFuture#FUTURE_SLEEP_TIME} milliseconds
     */
    private static RedFuture successfulFuture() {
        OpenRedFuture future = RedFuture.future();
        SCHEDULER.schedule(future::resolve, FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    /**
     * @return a {@link RedFutureOf} that will be successfully resolved with given value
     * within {@link TestRedFuture#FUTURE_SLEEP_TIME} milliseconds
     */
    private static <T> RedFutureOf<T> successfulFutureOf(T value) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        SCHEDULER.schedule(() -> future.resolve(value), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    /**
     * @return a {@link ListenableFuture} that will be successfully resolved with given value
     * within {@link TestRedFuture#FUTURE_SLEEP_TIME} milliseconds
     */
    private static <T> ListenableFuture<T> successfulListenableFuture(T value) {
        SettableFuture<T> future = SettableFuture.create();
        SCHEDULER.schedule(() -> future.set(value), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    /**
     * @return a {@link RedFuture} that will be failed within {@link TestRedFuture#FUTURE_SLEEP_TIME}
     * milliseconds
     */
    private static RedFuture failingFuture(Throwable throwable) {
        OpenRedFuture future = RedFuture.future();
        SCHEDULER.schedule(() -> future.fail(throwable), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    /**
     * @return a {@link RedFutureOf} that will be failed within {@link TestRedFuture#FUTURE_SLEEP_TIME}
     * milliseconds
     */
    private static <T> RedFutureOf<T> failingFutureOf(Throwable throwable) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        SCHEDULER.schedule(() -> future.fail(throwable), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    /**
     * @return a {@link ListenableFuture} that will be failed within {@link TestRedFuture#FUTURE_SLEEP_TIME}
     * milliseconds
     */
    private static <T> ListenableFuture<T> failingListenableFuture(Throwable throwable) {
        SettableFuture<T> future = SettableFuture.create();
        SCHEDULER.schedule(() -> future.setException(throwable), FUTURE_SLEEP_TIME, TimeUnit.MILLISECONDS);
        return future;
    }

    private static final String SCHEDULER_THREAD_NAME = "SCHEDULER_THREAD_NAME";

    private static final String TEST_THREAD_NAME = "TEST_THREAD_NAME";

    /**
     * ScheduledExecutorService to invoke delayed resolving of futures
     */
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1, r -> {
        Thread thread = new Thread(r);
        thread.setName(SCHEDULER_THREAD_NAME);
        return thread;
    });

    /**
     * Executor to be used to register callbacks with
     */
    private static final ExecutorService TEST_EXECUTOR = Executors.newFixedThreadPool(1, r -> {
        Thread thread = new Thread(r);
        thread.setName(TEST_THREAD_NAME);
        return thread;
    });

    static class TestException extends Exception {}
}
