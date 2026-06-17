package org.slf4j;

public interface Logger {
    default boolean isTraceEnabled() { return false; }
    default boolean isDebugEnabled() { return false; }
    default boolean isInfoEnabled() { return false; }
    default boolean isWarnEnabled() { return false; }
    default boolean isErrorEnabled() { return false; }
    default void trace(String message) { }
    default void trace(String message, Object argument) { }
    default void trace(String message, Object first, Object second) { }
    default void trace(String message, Object... arguments) { }
    default void debug(String message) { }
    default void debug(String message, Object argument) { }
    default void debug(String message, Object first, Object second) { }
    default void debug(String message, Object... arguments) { }
    default void info(String message) { }
    default void info(String message, Object argument) { }
    default void info(String message, Object first, Object second) { }
    default void info(String message, Object... arguments) { }
    default void warn(String message) { }
    default void warn(String message, Object argument) { }
    default void warn(String message, Object first, Object second) { }
    default void warn(String message, Object... arguments) { }
    default void error(String message) { }
    default void error(String message, Object argument) { }
    default void error(String message, Object first, Object second) { }
    default void error(String message, Object... arguments) { }
}
