//package com.javared.asssss;
//
//import com.javared.executor.RedSynchronizer;
//import com.javared.future.RedFuture;
//
//public class io extends RedSynchronizer<String, Boolean> {
//
//    public static class User {}
//
//    @Override
//    protected Result<Boolean> handle(String s) {
//        Result<User> userResult = produceRedFutureOf(User.class).byExecuting(() -> RedFuture.resolvedOf(null));
//        Result<String> timezoneResult = produceRedFutureOf(String.class).byExecuting(() -> RedFuture.resolvedOf(""));
//        Marker saveMarker = onceResults(userResult, timezoneResult).succeed().execute((result, f0, f1) -> {
//            result.resolve();
//        });
//        return onceResults(userResult, timezoneResult).succeed().andMarkers(saveMarker).succeed().produce(Boolean.class)
//                .byExecuting((f0, f1) -> f0.equals(new User()) && f1.equals("ioioio"));
//    }
//
//}
