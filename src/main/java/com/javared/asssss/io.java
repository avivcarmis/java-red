package com.javared.asssss;

import com.javared.executor.RedExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by avivc on 3/22/2017.
 */
public class io extends RedExecutor {

    Future<String> future = new Future<String>() {
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    public static class User {

        boolean westernTimezone;

        void save(Runnable finish) {

        }

    }

    public Result<Boolean> io() {
        Result<User> userResult = produce(User.class).byExecuting(User::new);
        Result<String> timezoneResult = produceFutureOf(String.class).byExecuting(() -> future);
        Result<Boolean> result = once(userResult, timezoneResult).succeed().produce(Boolean.class).byExecuting((user, timezone) ->
                user.westernTimezone && timezone.equals("western"));
        Marker marker = once(userResult, result).finish().execute((result1, f0, f1) -> {
            if (f1 != null && f1) {
                f0.save(result1::resolve);
            } else {
                result1.resolve();
            }
        });
        once(marker).succeed().execute(result1 -> {

        });
        return result;
    }

}
