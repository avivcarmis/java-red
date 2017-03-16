package com.javared;

import com.javared.future.OpenRedFuture;
import com.javared.test.RedAsyncTestRunner;
import com.javared.test.RedTestContext;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

/**
 * Created by avivc on 3/16/2017.
 */
@RunWith(RedAsyncTestRunner.class)
public class RedAsyncTestTest {

    @Test
    public void test(RedTestContext testContext) throws Exception {
        OpenRedFuture future = testContext.provideFuture();
        testContext.scheduleTimeout(3, TimeUnit.SECONDS, () -> {
            future.fail(new Exception("ioio"));
        });
    }

}
