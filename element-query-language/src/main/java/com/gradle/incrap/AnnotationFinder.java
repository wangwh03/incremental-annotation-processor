package com.gradle.incrap;

import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import static com.gradle.incrap.QueryLanguage.LOCATOR_ANNOTATION_ON_FIELD;
import static javax.lang.model.util.ElementFilter.fieldsIn;

/**
 * Retrieves annotation using the
 * element query language.
 */
public class AnnotationFinder {
  private static final String TAG = AnnotationFinder.class.getName();

  private Elements elementUtils;

  public AnnotationFinder(Elements elementUtils) {
    this.elementUtils = elementUtils;
  }

  /**
   * Retrieves an element given a query string.
   * Query string
   *
   * @param query a query using the element query language.
   * @return the element specified by the query or null if it can't be found.
   * @See #AnnotationFinder
   */
  public TypeElement lookupElement(String query) {
    log("Looking up %s", query);
    String[] split = query.split(":");
    String locator = split[0];
    switch (locator) {
      case LOCATOR_ANNOTATION_ON_FIELD:
        return lookupAnnotationOnField(query.substring(LOCATOR_ANNOTATION_ON_FIELD.length() + 1));
      default:
        throw new LocatorNotSupportedException(locator, query);
    }
  }

  /**
   * @return the annotation that matches this query or null if not found.
   */
  private TypeElement lookupAnnotationOnField(String subQuery) {
    log("Looking up annotation on field %s", subQuery);
    String[] split = subQuery.split(":");
    String className = split[0];
    String fieldName = split[1];
    String annotationTypeName = split[2];

    //find type
    TypeElement typeElement = elementByName(className);
    if (typeElement == null) {
      return null;
    }

    //find member in type
    List<? extends Element> allMembers = elementUtils.getAllMembers(typeElement);
    List<VariableElement> fields = fieldsIn(allMembers);
    Element fieldElement = fieldElementByName(fields, fieldName);
    if (fieldElement == null) {
      return null;
    }

    //find annotation on member
    final List<? extends AnnotationMirror> annotationMirrors = fieldElement.getAnnotationMirrors();
    return annotationByTypeName(annotationMirrors, annotationTypeName);
  }

  private TypeElement elementByName(String className) {
    TypeElement typeElement = elementUtils.getTypeElement(className);
    if (typeElement != null) {
      log("Found type element: %s", typeElement.toString());
    } else {
      log("Type not found");
    }
    return typeElement;
  }

  private Element fieldElementByName(List<VariableElement> fields, String fieldName) {
    for (Element fieldElement : fields) {
      final String currentFieldName = fieldElement.toString();
      log("Scanned member element: %s", currentFieldName);
      if (currentFieldName.equals(fieldName)) {
        log("Found member element: %s", currentFieldName);
        return fieldElement;
      }
    }
    log("Member element: %s not found");
    return null;
  }

  private TypeElement annotationByTypeName(List<? extends AnnotationMirror> annotationMirrors, String annotationTypeName) {
    for (AnnotationMirror annotationMirror : annotationMirrors) {
      final String currentAnnotationTypeName = annotationMirror.getAnnotationType().toString();
      log("Scanned annotation element: %s", currentAnnotationTypeName);
      if (currentAnnotationTypeName.equals(annotationTypeName)) {
        log("Found annotation element: %s", currentAnnotationTypeName);
        return (TypeElement) annotationMirror.getAnnotationType().asElement();
      }
    }
    log("Annotation element not found");
    return null;
  }

  private void log(String format, Object... args) {
    LogWrapper.log(TAG + ": " + format, args);
  }
}
