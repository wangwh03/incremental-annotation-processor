package com.gradle.incrap;

import static java.lang.String.format;

public class LocatorNotSupportedException extends RuntimeException {

  public LocatorNotSupportedException(String locator, String query) {
    super(format("Locator no supported: %s in query %s", locator, query));
  }
}
