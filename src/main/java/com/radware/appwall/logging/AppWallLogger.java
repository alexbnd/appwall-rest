//Copyright ©Radware Ltd. 2017 All Rights Reserved
package com.radware.appwall.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class AppWallLogger {
    private static AppWallLoggerImpl appWallLoggerImpl;
    public static final String DEFAULT_BUNDLE_NAME = "shared";
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME);

    static {
        appWallLoggerImpl = new AppWallLogger().new AppWallLoggerImpl();
    }


    public static void info(Class classObj, String msg, Object... args) {
        appWallLoggerImpl.log(Level.INFO, classObj, msg, args);
    }

    public static void trace(Class classObj, String msg, Object... args) {
        appWallLoggerImpl.log(Level.TRACE, classObj, msg, args);
    }

    public static void debug(Class classObj, String msg, Object... args) {
        appWallLoggerImpl.log(Level.DEBUG, classObj, msg, args);
    }

    public static void debug(Class classObj, Exception exception, String msg, Object... args) {
        appWallLoggerImpl.log(Level.DEBUG, classObj, exception, msg, args);
    }


    public static void error(Class classObj, Exception exception, String msg, Object... args) {
        appWallLoggerImpl.log(Level.ERROR, classObj, exception, msg, args);
    }

    public static void error(Class classObj, String msg, Object... args) {
        appWallLoggerImpl.log(Level.ERROR, classObj, msg, args);
    }

    public static Level getLogLevel(Class classObj) {
        return appWallLoggerImpl.getLogLevel(classObj);
    }


    class AppWallLoggerImpl {
        //Logger logger;
        private void logImpl(Level level, Class classObj, Throwable exception, String msg, Object... args) {
            Logger logger = LogManager.getLogger(classObj);
            String msgText = RESOURCE_BUNDLE.getString(msg);
            logger.log(level, logger.getMessageFactory().newMessage(msgText, args), exception);
        }

        //Log with a string and arguments instead of a Message and without an exception
        private void logImpl(Level level, Class classObj, String msg, Object... args) {
            Logger logger = LogManager.getLogger(classObj);
            String msgText = RESOURCE_BUNDLE.getString(msg);
            //Log a message
            logger.log(level, msgText, args);
        }

        // Log with a string and arguments instead of a Message and with an exception
        public void log(Level level, Class classObj, Exception exception, String msg, Object... args) {
            logImpl(level, classObj, exception, msg, args);
        }

        // Log messages without an exception
        public void log(Level level, Class classObj, String msg, Object... args) {
            logImpl(level, classObj, msg, args);
        }

        public Level getLogLevel(Class classObj) {
            Logger logger = LogManager.getLogger(classObj);
            return logger.getLevel();
        }

    }

}
