package io.github.avivcarmis.javared.test;

import org.junit.Test;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Overrides {@link BlockJUnit4ClassRunner} behaviour, so that when a {@link Test}
 * method is called, a single {@link RedTestContext} parameter is allowed.
 *
 * If a {@link RedTestContext} parameter does not exist, the original {@link BlockJUnit4ClassRunner}
 * behaviour is invoked.
 *
 * If a {@link RedTestContext} parameter exists, a {@link RedTestContext} instance is created
 * prior to the test, then the test method is invoked with the new context instance.
 * Once the test method returns, the runner waits for all the context forks to successfully
 * finish (see {@link RedTestContext#fork()}), and eventually it looks for un-thrown assertion errors.
 */
public class RedTestRunner extends BlockJUnit4ClassRunner {

    // Constructors

    public RedTestRunner(Class<?> aClass) throws InitializationError {
        super(aClass);
    }

    // private

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new AsyncInvokeMethod(method, test);
    }

    @Override
    protected void validateTestMethods(List<Throwable> errors) {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);
        for (FrameworkMethod method : methods) {
            method.validatePublicVoid(false, errors);
            if (!isNormalTest(method) && !isAsyncTest(method)) {
                errors.add(new Exception("Method " + method.getName() + " can either have no parameters, or" +
                        "one AsyncTest parameter"));
            }
        }
    }

    /**
     * @return true if the method has no parameters, being a simple JUnit test, false otherwise
     */
    private static boolean isNormalTest(FrameworkMethod method) {
        return method.getMethod().getParameterCount() == 0;
    }

    /**
     * @return true if the method has one {@link RedTestContext} parameter, being an async test,
     * false otherwise.
     */
    private static boolean isAsyncTest(FrameworkMethod method) {
        Class<?>[] types = method.getMethod().getParameterTypes();
        return types.length == 1 && types[0] == RedTestContext.class;
    }

    /**
     * Overrides the JUnit method invoker to implement async behaviour
     */
    private static class AsyncInvokeMethod extends InvokeMethod {

        // Fields

        private final FrameworkMethod _testMethod;

        private final Object _target;

        // Constructors

        private AsyncInvokeMethod(FrameworkMethod testMethod, Object target) {
            super(testMethod, target);
            this._testMethod = testMethod;
            this._target = target;
        }

        // Public

        @Override
        public void evaluate() throws Throwable {
            if (isNormalTest(_testMethod)) {
                super.evaluate();
            }
            else {
                RedTestContext testContext = new RedTestContext(Thread.currentThread());
                long testTimeout = 60000;
                Test testAnnotation = _testMethod.getAnnotation(Test.class);
                if (testAnnotation != null) {
                    if (testAnnotation.timeout() > 0) {
                        testTimeout = testAnnotation.timeout();
                    }
                }
                _testMethod.invokeExplosively(_target, testContext);
                try {
                    testContext.unite().waitForCompletion(testTimeout, TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    throw new AssertionError("test took more than " + testTimeout + "ms to complete");
                } catch (ExecutionException e) {
                    throw e.getCause();
                } catch (InterruptedException ignored) {}
                testContext.checkAssertions();
                testContext.close();
            }
        }

    }

}
