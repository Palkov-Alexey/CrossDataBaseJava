package com.example.crossdatabase.helpers;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

public class AnnotationHelper {
    public static <T, TR extends Annotation> Optional<TR> getAnnotation(T thisEnum, Class<TR> annotationClass) throws NoSuchFieldException {
        var anno = thisEnum.getClass().getDeclaredField(thisEnum.toString()).getAnnotationsByType(annotationClass);
        return Arrays.stream(anno).findFirst();
    }
}
