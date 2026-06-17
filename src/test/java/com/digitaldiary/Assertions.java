package com.digitaldiary;

public final class Assertions {
    private Assertions() {
    }

    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertThrows(Class<? extends Throwable> expectedType, Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            if (expectedType.isInstance(throwable)) {
                return;
            }
            throw new AssertionError("Expected " + expectedType.getSimpleName() + " but got " + throwable.getClass().getSimpleName());
        }
        throw new AssertionError("Expected " + expectedType.getSimpleName() + " but nothing was thrown");
    }
}
