package com.gradle.incrap.util;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaSourceSubjectFactory;
import java.lang.annotation.Annotation;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

public class TestUtil {
  public static TypeElement querySuccessfully(String path, JavaFileObject source) {
    AnnotationFinderTestProcessor processor = new AnnotationFinderTestProcessor(path);

    Truth.assertAbout(JavaSourceSubjectFactory.javaSource()).that(source) //
        .withCompilerOptions("-Xlint:-processing") //
        .processedWith(processor) //
        .compilesWithoutError();

    return processor.getResult();
  }

  public static String encodeSuccessfully(JavaFileObject source, Class<? extends Annotation> annotationClass) {
    AnnotationPathEncoderTestProcessor processor = new AnnotationPathEncoderTestProcessor(annotationClass);

    Truth.assertAbout(JavaSourceSubjectFactory.javaSource()).that(source) //
        .withCompilerOptions("-Xlint:-processing") //
        .processedWith(processor) //
        .compilesWithoutError();

    return processor.getResult();
  }
}