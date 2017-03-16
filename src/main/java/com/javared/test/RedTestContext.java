package com.javared.test;

import com.javared.future.RedFutureHub;
import com.javared.future.callbacks.EmptyCallback;
import org.junit.Assert;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by avivc on 3/16/2017.
 */
public class RedTestContext extends RedFutureHub {

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

    public final Assertions assertions;
    
    private final AtomicReference<Throwable> _firstFailure;

    private final Thread _executingThread;

    RedTestContext(Thread executingThread) {
        _executingThread = executingThread;
        assertions = new Assertions();
        _firstFailure = new AtomicReference<>(null);
    }

    public void scheduleTimeout(long delay, TimeUnit unit, Runnable runnable) {
        SCHEDULER.schedule(runnable, delay, unit);
    }

    public void fail(String message) {
        assertion(() -> Assert.fail(message));
    }

    public void fail() {
        assertion(Assert::fail);
    }

    private void assertion(EmptyCallback callback) {
        try {
            callback.call();
        } catch (Throwable t) {
            _firstFailure.compareAndSet(null, t);
            _executingThread.interrupt();
        }
    }

    void checkAssertions() throws Throwable {
        if (_firstFailure.get() != null) {
            throw _firstFailure.get();
        }
    }

    public class Assertions {

        public void assertTrue(String message, boolean condition) {
            assertion(() -> Assert.assertTrue(message, condition));
        }

        public void assertTrue(boolean condition) {
            assertion(() -> Assert.assertTrue(condition));
        }

        public void assertFalse(String message, boolean condition) {
            assertion(() -> Assert.assertFalse(message, condition));
        }

        public void assertFalse(boolean condition) {
            assertion(() -> Assert.assertFalse(condition));
        }

        public void assertEquals(String message, Object expected, Object actual) {
            assertion(() -> Assert.assertEquals(message, expected, actual));
        }

        public void assertEquals(Object expected, Object actual) {
            assertion(() -> Assert.assertEquals(expected, actual));
        }

        public void assertNotEquals(String message, Object unexpected, Object actual) {
            assertion(() -> Assert.assertNotEquals(message, unexpected, actual));
        }

        public void assertNotEquals(Object unexpected, Object actual) {
            assertion(() -> Assert.assertNotEquals(unexpected, actual));
        }

        public void assertNotEquals(String message, long unexpected, long actual) {
            assertion(() -> Assert.assertNotEquals(message, unexpected, actual));
        }

        public void assertNotEquals(long unexpected, long actual) {
            assertion(() -> Assert.assertNotEquals(unexpected, actual));
        }

        public void assertNotEquals(String message, double unexpected, double actual, double delta) {
            assertion(() -> Assert.assertNotEquals(message, unexpected, actual, delta));
        }

        public void assertNotEquals(double unexpected, double actual, double delta) {
            assertion(() -> Assert.assertNotEquals(unexpected, actual, delta));
        }

        public void assertNotEquals(float unexpected, float actual, float delta) {
            assertion(() -> Assert.assertNotEquals(unexpected, actual, delta));
        }

        public void assertArrayEquals(String message, Object[] expecteds, Object[] actuals) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals));
        }

        public void assertArrayEquals(Object[] expecteds, Object[] actuals) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals));
        }

        public void assertArrayEquals(String message, boolean[] expecteds, boolean[] actuals) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals));
        }

        public void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals));
        }

        public void assertArrayEquals(String message, byte[] expecteds, byte[] actuals) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals));
        }

        public void assertArrayEquals(byte[] expecteds, byte[] actuals) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals));
        }

        public void assertArrayEquals(String message, char[] expecteds, char[] actuals) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals));
        }

        public void assertArrayEquals(char[] expecteds, char[] actuals) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals));
        }

        public void assertArrayEquals(String message, short[] expecteds, short[] actuals) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals));
        }

        public void assertArrayEquals(short[] expecteds, short[] actuals) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals));
        }

        public void assertArrayEquals(String message, int[] expecteds, int[] actuals) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals));
        }

        public void assertArrayEquals(int[] expecteds, int[] actuals) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals));
        }

        public void assertArrayEquals(String message, long[] expecteds, long[] actuals) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals));
        }

        public void assertArrayEquals(long[] expecteds, long[] actuals) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals));
        }

        public void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals, delta));
        }

        public void assertArrayEquals(double[] expecteds, double[] actuals, double delta) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals, delta));
        }

        public void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta) {
            assertion(() -> Assert.assertArrayEquals(message, expecteds, actuals, delta));
        }

        public void assertArrayEquals(float[] expecteds, float[] actuals, float delta) {
            assertion(() -> Assert.assertArrayEquals(expecteds, actuals, delta));
        }

        public void assertEquals(String message, double expected, double actual, double delta) {
            assertion(() -> Assert.assertEquals(message, expected, actual, delta));
        }

        public void assertEquals(String message, float expected, float actual, float delta) {
            assertion(() -> Assert.assertEquals(message, expected, actual, delta));
        }

        public void assertNotEquals(String message, float unexpected, float actual, float delta) {
            assertion(() -> Assert.assertNotEquals(message, unexpected, actual, delta));
        }

        public void assertEquals(long expected, long actual) {
            assertion(() -> Assert.assertEquals(expected, actual));
        }

        public void assertEquals(String message, long expected, long actual) {
            assertion(() -> Assert.assertEquals(message, expected, actual));
        }

        public void assertEquals(double expected, double actual, double delta) {
            assertion(() -> Assert.assertEquals(expected, actual, delta));
        }

        public void assertEquals(float expected, float actual, float delta) {
            assertion(() -> Assert.assertEquals(expected, actual, delta));
        }

        public void assertNotNull(String message, Object object) {
            assertion(() -> Assert.assertNotNull(message, object));
        }

        public void assertNotNull(Object object) {
            assertion(() -> Assert.assertNotNull(object));
        }

        public void assertNull(String message, Object object) {
            assertion(() -> Assert.assertNull(message, object));
        }

        public void assertNull(Object object) {
            assertion(() -> Assert.assertNull(object));
        }

        public void assertSame(String message, Object expected, Object actual) {
            assertion(() -> Assert.assertSame(message, expected, actual));
        }

        public void assertSame(Object expected, Object actual) {
            assertion(() -> Assert.assertSame(expected, actual));
        }

        public void assertNotSame(String message, Object unexpected, Object actual) {
            assertion(() -> Assert.assertNotSame(message, unexpected, actual));
        }

        public void assertNotSame(Object unexpected, Object actual) {
            assertion(() -> Assert.assertNotSame(unexpected, actual));
        }

    }
    
}
