package com.gradle.incrap.util;

import com.gradle.incrap.AnnotationFinder;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.SourceVersion.latestSupported;

/**
 * Allows testing {@link AnnotationFinder} by running the annotationFinder
 * during annotation processing. This mechanism is required as we can't mock
 * the elements of annotation processing.
 */
@SupportedAnnotationTypes("*")
public class AnnotationFinderTestProcessor extends AbstractProcessor {

  private AnnotationFinder annotationFinder;
  private String query;
  private TypeElement result;

  public AnnotationFinderTestProcessor(String query) {
    this.query = query;
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    annotationFinder = new AnnotationFinder(processingEnv.getElementUtils());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (isLastRound(roundEnv)) {
      return false;
    }

    result = annotationFinder.lookupElement(query);
    return false;
  }

  private boolean isLastRound(RoundEnvironment roundEnv) {
    return roundEnv.processingOver();
  }

  public TypeElement getResult() {
    return result;
  }
}
