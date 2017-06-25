package com.gradle.incrap;

/**
 * <dl>
 * <dt> annotations on classes</dt>
 * <dd> {@code ac:className:annotationType}</dd>
 * <dt> annotations on fields</dt>
 * <dd> {@code af:className:fieldName:annotationType}</dd>
 * <dt> annotations on methods</dt>
 * <dd> {@code am:className:memberName:paramLength:paramType0:...paramTypeN:annotationType}</dd>
 * <dt> annotations on constructors</dt>
 * <dd> {@code am:className:init:paramLength:paramType0:...paramTypeN:annotationType}</dd>
 * <dt> annotations on params</dt>
 * <dd> {@code am:className:methodName:paramLength:paramType0:...paramTypeN:paramIndex:annotationType}</dd>
 * </dl>
 */
public interface QueryLanguage {
  String LOCATOR_ANNOTATION_ON_FIELD = "af";
  String SEPARATOR = ":";
}
