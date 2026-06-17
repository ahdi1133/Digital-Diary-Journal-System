package org.slf4j;

public final class LoggerFactory {
    private static final Logger NO_OP_LOGGER = new Logger() {
    };

    private LoggerFactory() {
    }

    public static Logger getLogger(Class<?> ignored) {
        return NO_OP_LOGGER;
    }

    public static Logger getLogger(String ignored) {
        return NO_OP_LOGGER;
    }
}
