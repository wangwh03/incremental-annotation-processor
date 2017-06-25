package com.gradle.incrap;

import java.lang.annotation.Annotation;
import javax.lang.model.element.Element;

import static com.gradle.incrap.QueryLanguage.LOCATOR_ANNOTATION_ON_FIELD;
import static com.gradle.incrap.QueryLanguage.SEPARATOR;

public class AnnotationPathEncoder {

  public String encodeAnnotationOnField(Element fieldElement, Class<? extends Annotation> annotationClass) {
    final String className = fieldElement.getEnclosingElement().toString();
    final String fieldName = fieldElement.getSimpleName().toString();
    final String annotationTypeName = annotationClass.getName();
    return new StringBuilder() //
        .append(LOCATOR_ANNOTATION_ON_FIELD)
        .append(SEPARATOR)
        .append(className)
        .append(SEPARATOR)
        .append(fieldName)
        .append(SEPARATOR)
        .append(annotationTypeName)
        .toString();
  }
}
