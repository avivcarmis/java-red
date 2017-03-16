package com.javared.test;

import org.junit.Test;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.List;

/**
 * Created by avivc on 3/15/2017.
 */
public class RedAsyncTestRunner extends BlockJUnit4ClassRunner {

    public RedAsyncTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

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

    private static boolean isNormalTest(FrameworkMethod method) {
        return method.getMethod().getParameterCount() == 0;
    }

    private static boolean isAsyncTest(FrameworkMethod method) {
        Class<?>[] types = method.getMethod().getParameterTypes();
        return types.length == 1 && types[0] == RedTestContext.class;
    }

    private static class AsyncInvokeMethod extends InvokeMethod {

        private final FrameworkMethod testMethod;

        private final Object target;

        private AsyncInvokeMethod(FrameworkMethod testMethod, Object target) {
            super(testMethod, target);
            this.testMethod = testMethod;
            this.target = target;
        }

        @Override
        public void evaluate() throws Throwable {
            if (isNormalTest(testMethod)) {
                super.evaluate();
            }
            else {
                RedTestContext testContext = new RedTestContext();
                long testTimeout = 0;
                Class<? extends Throwable> expectedException = Test.None.class;
                Test testAnnotation = testMethod.getAnnotation(Test.class);
                if (testAnnotation != null) {
                    testTimeout = testAnnotation.timeout();
                    expectedException = testAnnotation.expected();
                }
                try {
                    testMethod.invokeExplosively(target, testContext);
                } catch (Throwable t) {
                    if (expectedException == Test.None.class || !expectedException.isInstance(t)) {
                        throw t;
                    }
                }
                testContext.uniteOptimistically().waitForCompletion(); // TODO add timeout
                testContext.checkAssertions();
            }
        }

    }

}
