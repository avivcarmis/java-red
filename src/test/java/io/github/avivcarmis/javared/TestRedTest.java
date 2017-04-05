package io.github.avivcarmis.javared;

import io.github.avivcarmis.javared.future.callbacks.EmptyCallback;
import io.github.avivcarmis.javared.test.RedTestContext;
import io.github.avivcarmis.javared.test.RedTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test the functionality of the Async Test module.
 * Tests the {@link RedTestRunner} to validate correct flow control and handling of failures.
 * Tests the {@link RedTestContext} to validate forking, scheduling and assertions.
 * Tests the {@link RedTestContext.Fork} to validate completion and failing of tests.
 */
@RunWith(RedTestRunner.class)
public class TestRedTest {

    /**
     * Validates execution and success of regular Junit test
     * under the {@link RedTestRunner} runner
     */
    @Test
    public void testSimpleSuccess() {}

    /**
     * Validates execution and failing of regular Junit test
     * under the {@link RedTestRunner} runner
     */
    @Test(expected = TestException.class)
    public void testSimpleException() {
        throw new TestException();
    }

    /**
     * Validates execution and success of async test
     */
    @Test
    public void testAsyncSuccess(@SuppressWarnings("unused") RedTestContext redTestContext) {}

    /**
     * Validates execution and failing of async test
     */
    @Test(expected = TestException.class)
    public void testAsyncException(@SuppressWarnings("unused") RedTestContext redTestContext) {
        throw new TestException();
    }

    // Scheduler

    /**
     * Tests {@link RedTestContext} late tasks scheduling with explicit time unit
     */
    @Test(timeout = 300)
    public void testSchedulerNanos(RedTestContext redTestContext) throws InterruptedException {
        long time = System.nanoTime();
        AtomicBoolean tooEarly = new AtomicBoolean(false);
        AtomicBoolean enterDelayedBlock = new AtomicBoolean(false);
        redTestContext.scheduleTask(100 * 1000 * 1000, TimeUnit.NANOSECONDS, () -> {
            tooEarly.set(System.nanoTime() - time < 100 * 1000 * 1000);
            enterDelayedBlock.set(true);
        });
        Thread.sleep(150);
        Assert.assertFalse(tooEarly.get());
        Assert.assertTrue(enterDelayedBlock.get());
    }

    /**
     * Tests {@link RedTestContext} late tasks scheduling with default time unit
     */
    @Test(timeout = 300)
    public void testSchedulerDefaultTimeUnit(RedTestContext redTestContext) throws InterruptedException {
        long time = System.currentTimeMillis();
        AtomicBoolean tooEarly = new AtomicBoolean(false);
        AtomicBoolean enterDelayedBlock = new AtomicBoolean(false);
        redTestContext.scheduleTask(100, () -> {
            tooEarly.set(System.currentTimeMillis() - time < 100);
            enterDelayedBlock.set(true);
        });
        Thread.sleep(150);
        Assert.assertFalse(tooEarly.get());
        Assert.assertTrue(enterDelayedBlock.get());
    }

    // Timing validator

    /**
     * Tests success of {@link RedTestContext.TimingValidator}
     */
    @Test(timeout = 300)
    public void testTimingValidatorSuccess(RedTestContext redTestContext) throws InterruptedException {
        RedTestContext.TimingValidator validator = redTestContext.timingValidator();
        validator.validateNotPassed(1000);
        validator.validateNotPassed(1000 * 1000, TimeUnit.MICROSECONDS);
        Thread.sleep(100);
        validator.validatePassed(50);
        validator.validatePassed(50 * 1000, TimeUnit.MICROSECONDS);
    }

    /**
     * Tests {@link RedTestContext.TimingValidator} not passed failure
     * with explicit time unit
     */
    @Test(timeout = 300, expected = RedTestContext.TimingValidator.PassedException.class)
    public void testTimingValidatorNotPassedTimeUnit(RedTestContext redTestContext) throws InterruptedException {
        RedTestContext.TimingValidator validator = redTestContext.timingValidator();
        Thread.sleep(100);
        validator.validateNotPassed(50 * 1000, TimeUnit.MICROSECONDS);
    }

    /**
     * Tests {@link RedTestContext.TimingValidator} not passed failure
     * with default time unit
     */
    @Test(timeout = 300, expected = RedTestContext.TimingValidator.PassedException.class)
    public void testTimingValidatorNotPassed(RedTestContext redTestContext) throws InterruptedException {
        RedTestContext.TimingValidator validator = redTestContext.timingValidator();
        Thread.sleep(100);
        validator.validateNotPassed(50);
    }

    /**
     * Tests {@link RedTestContext.TimingValidator} passed failure
     * with explicit time unit
     */
    @Test(timeout = 300, expected = RedTestContext.TimingValidator.NotPassedException.class)
    public void testTimingValidatorPassedTimeUnit(RedTestContext redTestContext) throws InterruptedException {
        RedTestContext.TimingValidator validator = redTestContext.timingValidator();
        validator.validatePassed(50 * 1000, TimeUnit.MICROSECONDS);
    }

    /**
     * Tests {@link RedTestContext.TimingValidator} passed failure
     * with default time unit
     */
    @Test(timeout = 300, expected = RedTestContext.TimingValidator.NotPassedException.class)
    public void testTimingValidatorPassed(RedTestContext redTestContext) throws InterruptedException {
        RedTestContext.TimingValidator validator = redTestContext.timingValidator();
        validator.validatePassed(50);
    }

    // Context fail

    /**
     * Tests the context direct failing through {@link RedTestContext#fail(Throwable)}
     */
    @Test(expected = TestException.class)
    public void testDirectContextFail(RedTestContext redTestContext) {
        redTestContext.fail(new TestException());
    }

    /**
     * Tests a forked task context failing through {@link RedTestContext#fail(Throwable)}
     */
    @Test(expected = TestException.class)
    public void testForkedContextFail(RedTestContext redTestContext) {
        redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () -> redTestContext.fail(new TestException()));
    }

    /**
     * Tests a forked task context failing through {@link RedTestContext#fail(String)}
     */
    @Test(expected = RuntimeException.class)
    public void testForkedContextFailWithMessage(RedTestContext redTestContext) {
        redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () -> redTestContext.fail("message"));
    }

    /**
     * Tests a forked task context failing through {@link RedTestContext#fail()}
     */
    @Test(expected = RuntimeException.class)
    public void testForkedContextFailEmpty(RedTestContext redTestContext) {
        redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, redTestContext::fail);
    }

    // Single fork

    /**
     * Tests a forked task fork failing through {@link RedTestContext.Fork#fail(Throwable)}
     */
    @Test(expected = TestException.class)
    public void testSingleFailingFork(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () -> fork.fail(new TestException()));
    }

    /**
     * Tests a success of a singly forked task who's fork has
     * successfully completed after some delay
     */
    @Test
    public void testSingleSuccessfulFork(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () -> {
            if (!redTestContext.isTestActive()) {
                fork.fail("test is not active");
            }
            else {
                fork.complete();
            }
        });
    }

    // Double fork

    /**
     * Tests a success of a multiple-forked task who's fork has
     * successfully completed after some delay
     */
    @Test
    public void testDoubleSuccessfulFork(RedTestContext redTestContext) {
        RedTestContext.Fork fork1 = redTestContext.fork();
        redTestContext.scheduleTask(50, TimeUnit.MILLISECONDS, () -> {
            if (!redTestContext.isTestActive()) {
                fork1.fail("test is not active on fork1");
            }
            else {
                fork1.complete();
            }
        });
        RedTestContext.Fork fork2 = redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () -> {
            if (!redTestContext.isTestActive()) {
                fork2.fail("test is not active on fork2");
            }
            else {
                fork2.complete();
            }
        });
    }

    /**
     * Tests a failure of a multiple-forked task who's one fork has
     * successfully completed and the other failed
     */
    @Test(expected = RuntimeException.class)
    public void testDoubleSuccessfulAndFailingFork(RedTestContext redTestContext) {
        RedTestContext.Fork fork1 = redTestContext.fork();
        redTestContext.scheduleTask(50, TimeUnit.MILLISECONDS, () -> {
            if (!redTestContext.isTestActive()) {
                fork1.fail("test is not active on fork1");
            }
            else {
                fork1.complete();
            }
        });
        RedTestContext.Fork fork2 = redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () -> {
            if (!redTestContext.isTestActive()) {
                fork2.fail("test is not active on fork2");
            }
            fork2.fail();
        });
    }

    /**
     * Tests a failure of a multiple-forked task who's one fork has
     * successfully completed and a nested fork is failing
     */
    @Test
    public void testDoubleNestedFork(RedTestContext redTestContext) {
        RedTestContext.Fork fork1 = redTestContext.fork();
        RedTestContext.Fork fork2 = redTestContext.fork();
        redTestContext.scheduleTask(50, TimeUnit.MILLISECONDS, () -> {
            if (!redTestContext.isTestActive()) {
                fork1.fail("test is not active on fork1");
            }
            else {
                fork1.complete();
                redTestContext.scheduleTask(50, TimeUnit.MILLISECONDS, () -> {
                    if (!redTestContext.isTestActive()) {
                        fork2.fail("test is not active on fork2");
                    }
                    else {
                        fork2.complete();

                    }
                });
            }
        });
    }

    // Assertions

    /**
     * Tests all the assertion methods to validate no false negative error.
     */
    @Test
    public void testPositiveAssertions(RedTestContext redTestContext) {
        redTestContext.assertions.assertArrayEquals("OBJECTS", OBJECTS1, OBJECTS2);
        redTestContext.assertions.assertArrayEquals(OBJECTS1, OBJECTS2);
        redTestContext.assertions.assertArrayEquals("BOOLEANS", BOOLEANS1, BOOLEANS2);
        redTestContext.assertions.assertArrayEquals(BOOLEANS1, BOOLEANS2);
        redTestContext.assertions.assertArrayEquals("BYTES", BYTES1, BYTES2);
        redTestContext.assertions.assertArrayEquals(BYTES1, BYTES2);
        redTestContext.assertions.assertArrayEquals("CHARS", CHARS1, CHARS2);
        redTestContext.assertions.assertArrayEquals(CHARS1, CHARS2);
        redTestContext.assertions.assertArrayEquals("SHORTS", SHORTS1, SHORTS2);
        redTestContext.assertions.assertArrayEquals(SHORTS1, SHORTS2);
        redTestContext.assertions.assertArrayEquals("INTS", INTS1, INTS2);
        redTestContext.assertions.assertArrayEquals(INTS1, INTS2);
        redTestContext.assertions.assertArrayEquals("LONGS", LONGS1, LONGS2);
        redTestContext.assertions.assertArrayEquals(LONGS1, LONGS2);
        redTestContext.assertions.assertArrayEquals("DOUBLES", DOUBLES1, DOUBLES2, 0);
        redTestContext.assertions.assertArrayEquals(DOUBLES1, DOUBLES2, 0);
        redTestContext.assertions.assertArrayEquals("FLOATS", FLOATS1, FLOATS2, 0);
        redTestContext.assertions.assertArrayEquals(FLOATS1, FLOATS2, 0);
        redTestContext.assertions.assertEquals("OBJECT", OBJECT1, OBJECT1);
        redTestContext.assertions.assertEquals(OBJECT1, OBJECT1);
        redTestContext.assertions.assertEquals("BOOLEAN", BOOLEAN2, BOOLEAN3);
        redTestContext.assertions.assertEquals(BOOLEAN2, BOOLEAN3);
        redTestContext.assertions.assertEquals("BYTE", BYTE2, BYTE3);
        redTestContext.assertions.assertEquals(BYTE2, BYTE3);
        redTestContext.assertions.assertEquals("CHAR", CHAR2, CHAR3);
        redTestContext.assertions.assertEquals(CHAR2, CHAR3);
        redTestContext.assertions.assertEquals("SHORT", SHORT2, SHORT3);
        redTestContext.assertions.assertEquals(SHORT2, SHORT3);
        redTestContext.assertions.assertEquals("INT", INT2, INT3);
        redTestContext.assertions.assertEquals(INT2, INT3);
        redTestContext.assertions.assertEquals("LONG", LONG2, LONG3);
        redTestContext.assertions.assertEquals(LONG2, LONG3);
        redTestContext.assertions.assertEquals("FLOAT", FLOAT2, FLOAT3);
        redTestContext.assertions.assertEquals(FLOAT2, FLOAT3);
        redTestContext.assertions.assertEquals("DOUBLE", DOUBLE2, DOUBLE3);
        redTestContext.assertions.assertEquals(DOUBLE2, DOUBLE3);
        redTestContext.assertions.assertEquals("FLOAT", FLOAT2, FLOAT3, 0);
        redTestContext.assertions.assertEquals(FLOAT2, FLOAT3, 0);
        redTestContext.assertions.assertEquals("DOUBLE", DOUBLE2, DOUBLE3, 0);
        redTestContext.assertions.assertEquals(DOUBLE2, DOUBLE3, 0);
        redTestContext.assertions.assertNotEquals("OBJECT", OBJECT1, OBJECT2);
        redTestContext.assertions.assertNotEquals(OBJECT1, OBJECT2);
        redTestContext.assertions.assertNotEquals("BOOLEAN", BOOLEAN1, BOOLEAN3);
        redTestContext.assertions.assertNotEquals(BOOLEAN1, BOOLEAN3);
        redTestContext.assertions.assertNotEquals("BYTE", BYTE1, BYTE3);
        redTestContext.assertions.assertNotEquals(BYTE1, BYTE3);
        redTestContext.assertions.assertNotEquals("CHAR", CHAR1, CHAR3);
        redTestContext.assertions.assertNotEquals(CHAR1, CHAR3);
        redTestContext.assertions.assertNotEquals("SHORT", SHORT1, SHORT3);
        redTestContext.assertions.assertNotEquals(SHORT1, SHORT3);
        redTestContext.assertions.assertNotEquals("INT", INT1, INT3);
        redTestContext.assertions.assertNotEquals(INT1, INT3);
        redTestContext.assertions.assertNotEquals("LONG", LONG1, LONG3);
        redTestContext.assertions.assertNotEquals(LONG1, LONG3);
        redTestContext.assertions.assertNotEquals("FLOAT", FLOAT1, FLOAT3);
        redTestContext.assertions.assertNotEquals(FLOAT1, FLOAT3);
        redTestContext.assertions.assertNotEquals("DOUBLE", DOUBLE1, DOUBLE3);
        redTestContext.assertions.assertNotEquals(DOUBLE1, DOUBLE3);
        redTestContext.assertions.assertNotEquals("FLOAT", FLOAT1, FLOAT3, 0);
        redTestContext.assertions.assertNotEquals(FLOAT1, FLOAT3, 0);
        redTestContext.assertions.assertNotEquals("DOUBLE", DOUBLE1, DOUBLE3, 0);
        redTestContext.assertions.assertNotEquals(DOUBLE1, DOUBLE3, 0);
        redTestContext.assertions.assertNull("null", null);
        redTestContext.assertions.assertNull(null);
        redTestContext.assertions.assertNotNull("not null", OBJECT1);
        redTestContext.assertions.assertNotNull(OBJECT1);
        redTestContext.assertions.assertSame("same", OBJECT1, OBJECT1);
        redTestContext.assertions.assertSame(OBJECT1, OBJECT1);
        redTestContext.assertions.assertNotSame("not same", OBJECT1, OBJECT2);
        redTestContext.assertions.assertNotSame(OBJECT1, OBJECT2);
        redTestContext.assertions.assertFalse(false);
        redTestContext.assertions.assertFalse("false", false);
        redTestContext.assertions.assertTrue(true);
        redTestContext.assertions.assertTrue("true", true);
    }

    /**
     * Tests all the assertion methods to validate no false positive error.
     */
    @Test(expected = AssertionError.class)
    public void testNegativeAssertions(RedTestContext redTestContext) {
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("OBJECTS", OBJECTS1, OBJECTS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(OBJECTS1, OBJECTS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("BOOLEANS", BOOLEANS1, BOOLEANS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(BOOLEANS1, BOOLEANS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("BYTES", BYTES1, BYTES3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(BYTES1, BYTES3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("CHARS", CHARS1, CHARS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(CHARS1, CHARS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("SHORTS", SHORTS1, SHORTS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(SHORTS1, SHORTS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("INTS", INTS1, INTS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(INTS1, INTS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("LONGS", LONGS1, LONGS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(LONGS1, LONGS3));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("DOUBLES", DOUBLES1, DOUBLES3, 0));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(DOUBLES1, DOUBLES3, 0));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals("FLOATS", FLOATS1, FLOATS3, 0));
        negativeAssert(() -> redTestContext.assertions.assertArrayEquals(FLOATS1, FLOATS3, 0));
        negativeAssert(() -> redTestContext.assertions.assertEquals("OBJECT", OBJECT2, OBJECT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(OBJECT2, OBJECT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("BOOLEAN", BOOLEAN2, BOOLEAN1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(BOOLEAN2, BOOLEAN1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("BYTE", BYTE2, BYTE1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(BYTE2, BYTE1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("CHAR", CHAR2, CHAR1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(CHAR2, CHAR1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("SHORT", SHORT2, SHORT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(SHORT2, SHORT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("INT", INT2, INT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(INT2, INT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("LONG", LONG2, LONG1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(LONG2, LONG1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("FLOAT", FLOAT2, FLOAT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(FLOAT2, FLOAT1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("DOUBLE", DOUBLE2, DOUBLE1));
        negativeAssert(() -> redTestContext.assertions.assertEquals(DOUBLE2, DOUBLE1));
        negativeAssert(() -> redTestContext.assertions.assertEquals("FLOAT", FLOAT2, FLOAT1, 0));
        negativeAssert(() -> redTestContext.assertions.assertEquals(FLOAT2, FLOAT1, 0));
        negativeAssert(() -> redTestContext.assertions.assertEquals("DOUBLE", DOUBLE2, DOUBLE1, 0));
        negativeAssert(() -> redTestContext.assertions.assertEquals(DOUBLE2, DOUBLE1, 0));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("OBJECT", OBJECT2, OBJECT2));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(OBJECT2, OBJECT2));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("BOOLEAN", BOOLEAN2, BOOLEAN3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(BOOLEAN2, BOOLEAN3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("BYTE", BYTE2, BYTE3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(BYTE2, BYTE3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("CHAR", CHAR2, CHAR3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(CHAR2, CHAR3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("SHORT", SHORT2, SHORT3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(SHORT2, SHORT3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("INT", INT2, INT3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(INT2, INT3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("LONG", LONG2, LONG3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(LONG2, LONG3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("FLOAT", FLOAT2, FLOAT3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(FLOAT2, FLOAT3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("DOUBLE", DOUBLE2, DOUBLE3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(DOUBLE2, DOUBLE3));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("FLOAT", FLOAT2, FLOAT3, 0));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(FLOAT2, FLOAT3, 0));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals("DOUBLE", DOUBLE2, DOUBLE3, 0));
        negativeAssert(() -> redTestContext.assertions.assertNotEquals(DOUBLE2, DOUBLE3, 0));
        negativeAssert(() -> redTestContext.assertions.assertNull("null", OBJECT1));
        negativeAssert(() -> redTestContext.assertions.assertNull(OBJECT1));
        negativeAssert(() -> redTestContext.assertions.assertNotNull("not null", null));
        negativeAssert(() -> redTestContext.assertions.assertNotNull(null));
        negativeAssert(() -> redTestContext.assertions.assertSame("same", OBJECT1, OBJECT2));
        negativeAssert(() -> redTestContext.assertions.assertSame(OBJECT1, OBJECT2));
        negativeAssert(() -> redTestContext.assertions.assertNotSame("not same", OBJECT1, OBJECT1));
        negativeAssert(() -> redTestContext.assertions.assertNotSame(OBJECT1, OBJECT1));
        negativeAssert(() -> redTestContext.assertions.assertFalse(true));
        negativeAssert(() -> redTestContext.assertions.assertFalse("false", true));
        negativeAssert(() -> redTestContext.assertions.assertTrue(false));
        negativeAssert(() -> redTestContext.assertions.assertTrue("true", false));
    }

    /**
     * Tests an assertion method which runs on a nested callback,
     * to validate no false negative error.
     */
    @Test
    public void testForkedPositiveAssertion(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () ->  {
            redTestContext.assertions.assertNull(null);
            fork.complete();
        });
    }

    /**
     * Tests an assertion method which runs on a nested callback,
     * to validate no false positive error.
     */
    @Test(expected = AssertionError.class)
    public void testForkedNegativeAssertion(RedTestContext redTestContext) {
        redTestContext.fork();
        redTestContext.scheduleTask(100, TimeUnit.MILLISECONDS, () ->
                redTestContext.assertions.assertNull(OBJECT1));
    }

    // Utils

    /**
     * Receive a assertion callback to execute,
     * validates that the callback is throwing an {@link AssertionError}
     */
    private void negativeAssert(EmptyCallback assertion) {
        try {
            assertion.call();
        } catch (AssertionError e) {
            return;
        }
        throw new RuntimeException("assertion not thrown");
    }

    public static class TestException extends RuntimeException {}

    private static final Object OBJECT1 = new Object();
    private static final Object OBJECT2 = new Object();
    private static final Object[] OBJECTS1 = new Object[] {OBJECT1};
    private static final Object[] OBJECTS2 = new Object[] {OBJECT1};
    private static final Object[] OBJECTS3 = new Object[] {OBJECT2};

    private static final boolean BOOLEAN1 = false;
    private static final boolean BOOLEAN2 = true;
    private static final boolean BOOLEAN3 = true;
    private static final boolean[] BOOLEANS1 = new boolean[] {BOOLEAN1};
    private static final boolean[] BOOLEANS2 = new boolean[] {BOOLEAN1};
    private static final boolean[] BOOLEANS3 = new boolean[] {BOOLEAN2};

    private static final byte BYTE1 = 'a';
    private static final byte BYTE2 = 'b';
    private static final byte BYTE3 = 'b';
    private static final byte[] BYTES1 = new byte[] {BYTE1};
    private static final byte[] BYTES2 = new byte[] {BYTE1};
    private static final byte[] BYTES3 = new byte[] {BYTE2};

    private static final char CHAR1 = 'a';
    private static final char CHAR2 = 'b';
    private static final char CHAR3 = 'b';
    private static final char[] CHARS1 = new char[] {CHAR1};
    private static final char[] CHARS2 = new char[] {CHAR1};
    private static final char[] CHARS3 = new char[] {CHAR2};

    private static final short SHORT1 = 'a';
    private static final short SHORT2 = 'b';
    private static final short SHORT3 = 'b';
    private static final short[] SHORTS1 = new short[] {SHORT1};
    private static final short[] SHORTS2 = new short[] {SHORT1};
    private static final short[] SHORTS3 = new short[] {SHORT2};

    private static final int INT1 = 'a';
    private static final int INT2 = 'b';
    private static final int INT3 = 'b';
    private static final int[] INTS1 = new int[] {INT1};
    private static final int[] INTS2 = new int[] {INT1};
    private static final int[] INTS3 = new int[] {INT2};

    private static final long LONG1 = 'a';
    private static final long LONG2 = 'b';
    private static final long LONG3 = 'b';
    private static final long[] LONGS1 = new long[] {LONG1};
    private static final long[] LONGS2 = new long[] {LONG1};
    private static final long[] LONGS3 = new long[] {LONG2};

    private static final double DOUBLE1 = 'a';
    private static final double DOUBLE2 = 'b';
    private static final double DOUBLE3 = 'b';
    private static final double[] DOUBLES1 = new double[] {DOUBLE1};
    private static final double[] DOUBLES2 = new double[] {DOUBLE1};
    private static final double[] DOUBLES3 = new double[] {DOUBLE2};

    private static final float FLOAT1 = 'a';
    private static final float FLOAT2 = 'b';
    private static final float FLOAT3 = 'b';
    private static final float[] FLOATS1 = new float[] {FLOAT1};
    private static final float[] FLOATS2 = new float[] {FLOAT1};
    private static final float[] FLOATS3 = new float[] {FLOAT2};

}
