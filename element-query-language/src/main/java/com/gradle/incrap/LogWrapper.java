package com.gradle.incrap;

//TODO: it's been a long time I didn't use a log framework, which one to take ?
public class LogWrapper {
  public static boolean DEBUG = false;

  public static void log(String format, Object... args) {
    if (DEBUG) {
      System.out.printf(format, args);
    }
  }

  public static void log(String tag, Exception ex) {
    if (DEBUG) {
      if (tag != null) {
        System.out.printf(tag);
      }
      ex.printStackTrace();
    }
  }
}