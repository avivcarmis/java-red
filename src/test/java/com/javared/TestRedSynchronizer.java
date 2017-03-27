package com.javared;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.javared.executor.RedSynchronizer;
import com.javared.executor.RedVoidSynchronizer;
import com.javared.future.OpenRedFutureOf;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;
import com.javared.test.RedTestContext;
import com.javared.test.RedTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by avivc on 3/27/2017.
 */
@RunWith(RedTestRunner.class)
public class TestRedSynchronizer {

    private static final long FUTURE_DELAY = 100;

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -no markers
     * -function result
     */
    @Test
    public void test1(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.Test1Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -function result
     */
    @Test
    public void test2(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.Test2Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -function result
     */
    @Test
    public void test3(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.Test3Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -function result
     */
    @Test
    public void test4(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.Test4Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -another layer of markers with finish status
     * -function result
     */
    @Test
    public void test5(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.Test5Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -another layer of markers with success status
     * -function result
     */
    @Test
    public void test6(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.Test6Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -another layer of markers with fail status
     * -function result
     */
    @Test
    public void test7(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.Test7Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -no markers
     * -command result
     */
    @Test
    public void test8(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.Test8Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -command result
     */
    @Test
    public void test9(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.Test9Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -command result
     */
    @Test
    public void test10(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.Test10Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -command result
     */
    @Test
    public void test11(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.Test11Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -another layer of markers with finish status
     * -command result
     */
    @Test
    public void test12(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.Test12Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -another layer of markers with success status
     * -command result
     */
    @Test
    public void test13(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.Test13Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -another layer of markers with fail status
     * -command result
     */
    @Test
    public void test14(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.Test14Synchronizer.class);
    }

    // Utils

    private static <T> void runFunctionTest(RedTestContext redTestContext,
                                            Class<? extends RedSynchronizer<RedTestContext, T>> syncClass)
            throws IllegalAccessException, InstantiationException {
        RedTestContext.Fork fork = redTestContext.fork();
        RedSynchronizer<RedTestContext, T> synchronizer = syncClass.newInstance();
        synchronizer.execute(redTestContext).addFailureCallback(redTestContext::fail).addSuccessCallback(result -> {
            if (result != null && result instanceof Boolean && result == booleanSuccess()) {
                fork.complete();
            }
            else if (result != null && result instanceof String && result.equals(stringSuccess())) {
                fork.complete();
            }
            else if (result != null && result instanceof TestObject && result == testObjectSuccess()) {
                fork.complete();
            }
            else {
                fork.fail("unexpected result");
            }
        });
    }

    private static void runCommandTest(RedTestContext redTestContext,
                                       Class<? extends RedVoidSynchronizer<CommandTestContext>> syncClass)
            throws IllegalAccessException, InstantiationException {
        RedTestContext.Fork fork = redTestContext.fork();
        RedVoidSynchronizer<CommandTestContext> synchronizer = syncClass.newInstance();
        CommandTestContext commandTestContext = new CommandTestContext(redTestContext);
        synchronizer.execute(commandTestContext).addFailureCallback(redTestContext::fail).addSuccessCallback(() -> {
            if (commandTestContext.finished.get()) {
                fork.complete();
            }
            else {
                fork.fail("not finished executing");
            }
        });
    }

    private static Boolean booleanSuccess() {
        return true;
    }

    private static String stringSuccess() {
        return "success";
    }

    private static TestObject testObjectSuccess() {
        return TestObject.INSTANCE;
    }

    private static <T> Future<T> futureOf(T value, RedTestContext redTestContext) {
        CompletableFuture<T> future = new CompletableFuture<>();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.complete(value));
        return future;
    }

    private static <T> Future<T> futureOf(@SuppressWarnings("unused") Class<T> tClass,
                                          Throwable t, RedTestContext redTestContext) {
        CompletableFuture<T> future = new CompletableFuture<>();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.completeExceptionally(t));
        return future;
    }

    private static <T> ListenableFuture<T> listenableFutureOf(T value, RedTestContext redTestContext) {
        SettableFuture<T> future = SettableFuture.create();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.set(value));
        return future;
    }

    private static <T> ListenableFuture<T> listenableFutureOf(@SuppressWarnings("unused") Class<T> tClass,
                                                              Throwable t, RedTestContext redTestContext) {
        SettableFuture<T> future = SettableFuture.create();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.setException(t));
        return future;
    }

    private static <T> RedFutureOf<T> redFutureOf(T value, RedTestContext redTestContext) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.resolve(value));
        return future;
    }

    private static <T> RedFutureOf<T> redFutureOf(@SuppressWarnings("unused") Class<T> tClass,
                                                  Throwable t, RedTestContext redTestContext) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.fail(t));
        return future;
    }

    private static class CommandTestContext {

        private final RedTestContext redTestContext;

        private final AtomicBoolean finished;

        private CommandTestContext(RedTestContext redTestContext) {
            this.redTestContext = redTestContext;
            this.finished = new AtomicBoolean();
        }

    }

    private static class TestObject {

        private static final TestObject INSTANCE = new TestObject();

    }

    private static class TestException extends Exception {

        private static final TestException INSTANCE = new TestException();

    }

    // Test synchronizer

    public static class TestSynchronizers {

        public static class Test1Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                return produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
            }

        }

        public static class Test2Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).succeed().produceFutureOf(String.class).byExecuting(() -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    return futureOf(stringSuccess(), context);
                });
            }

        }

        public static class Test3Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1, marker2).fail().produceListenableFutureOf(TestObject.class).byExecuting(() -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    return listenableFutureOf(testObjectSuccess(), context);
                });
            }

        }

        public static class Test4Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).finish().produceRedFutureOf(Boolean.class).byExecuting(() -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    return redFutureOf(booleanSuccess(), context);
                });
            }

        }

        public static class Test5Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).succeed().andMarkers(marker2).finish().produce(String.class)
                        .byExecuting(() -> {
                            timingTester.validatePassed(FUTURE_DELAY);
                            return stringSuccess();
                        });
            }

        }

        public static class Test6Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).fail().andMarkers(marker2).succeed().produceFutureOf(TestObject.class)
                        .byExecuting(() -> {
                            timingTester.validatePassed(FUTURE_DELAY);
                            return futureOf(testObjectSuccess(), context);
                        });
            }

        }

        public static class Test7Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1).finish().andMarkers(marker2).fail().produceListenableFutureOf(Boolean.class)
                        .byExecuting(() -> {
                            timingTester.validatePassed(FUTURE_DELAY);
                            return listenableFutureOf(booleanSuccess(), context);
                        });
            }

        }

        public static class Test8Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                return execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () -> {
                    context.finished.set(true);
                    pendingMarker.complete();
                }));
            }

        }

        public static class Test9Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker ->
                        context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker ->
                        context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).succeed().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class Test10Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1, marker2).fail().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class Test11Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).finish().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class Test12Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).succeed().andMarkers(marker2).finish().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class Test13Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).fail().andMarkers(marker2).succeed().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class Test14Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1).finish().andMarkers(marker2).fail().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

    }

}
