package com.conquestreforged.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private static final Logger logger = LogManager.getLogger("ReforgedCore");

    public static final int ERROR = 0;
    public static final int WARN = 1;
    public static final int INFO = 2;
    public static final int DEBUG = 3;

    private static int level = Log.DEBUG;

    private Log() {
    }

    public static void level(int level) {
        if (level >= ERROR && level <= DEBUG) {
            Log.level = level;
        }
    }

    public static void info(String message, Object... args) {
        if (Log.level < Log.INFO) {
            return;
        }
        Log.logger.info(message, args);
    }

    public static void error(String message, Object... args) {
        if (Log.level < Log.ERROR) {
            return;
        }
        Log.logger.error(message, args);
    }

    public static void warn(String message, Object... args) {
        if (Log.level < Log.WARN) {
            return;
        }
        Log.logger.warn(message, args);
    }

    public static void debug(String message, Object... args) {
        if (Log.level < Log.DEBUG) {
            return;
        }
        Log.logger.debug(message, args);
    }
}
