package com.gradle.incrap.util;

import com.gradle.incrap.AnnotationPathEncoder;
import java.lang.annotation.Annotation;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.SourceVersion.latestSupported;

/**
 * Allows testing {@link AnnotationPathEncoder} by running the annotationPathEncoder
 * during annotation processing. This mechanism is required as we can't mock
 * the elements of annotation processing.
 */
@SupportedAnnotationTypes("*")
public class AnnotationPathEncoderTestProcessor extends AbstractProcessor {

  private AnnotationPathEncoder annotationPathEncoder = new AnnotationPathEncoder();
  private Class<? extends Annotation> annotationClass;
  private String result;

  public AnnotationPathEncoderTestProcessor(Class<? extends Annotation> annotationClass) {
    this.annotationClass = annotationClass;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotationClass);

    if (islastRound(roundEnv)) {
      return false;
    }
    assertNotEmpty(annotatedElements);
    assertContainsOnlyOneElement(annotatedElements);

    final Element annotatedElement = getFirstElement(annotatedElements);
    result = annotationPathEncoder.encodeAnnotationOnField(annotatedElement, annotationClass);
    return false;
  }

  public String getResult() {
    return result;
  }

  private Element getFirstElement(Set<? extends Element> annotatedElements) {
    return annotatedElements.iterator().next();
  }

  private void assertContainsOnlyOneElement(Set<? extends Element> annotatedElements) {
    if (annotatedElements.size() > 1) {
      throw new RuntimeException("Too many annotated elements found");
    }
  }

  private void assertNotEmpty(Set<? extends Element> annotatedElements) {
    if (annotatedElements.isEmpty()) {
      throw new RuntimeException("No annotated elements found");
    }
  }

  private boolean islastRound(RoundEnvironment roundEnv) {
    return roundEnv.processingOver();
  }
}
