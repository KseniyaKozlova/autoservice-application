package by.autodiagnostic.validation.impl;

import by.autodiagnostic.annotation.PatternValidator;
import by.autodiagnostic.validation.FieldValidator;
import by.autodiagnostic.validation.FieldValidatorException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class TransportFieldValidator implements FieldValidator {

    @Override
    public boolean workField(final Object object) throws FieldValidatorException {
        boolean isMatches = false;

        for (final Field field : object.getClass().getDeclaredFields()) {
            for (final Annotation annotation : field.getDeclaredAnnotations()) {
                if (!(annotation instanceof final PatternValidator patternValidator)) {
                    continue;
                }

                if (!field.canAccess(object) && !field.trySetAccessible()) {
                    continue;
                }

                try {
                    final Object fieldValue = field.get(object);
                    if (!(fieldValue instanceof final String value)) {
                        continue;
                    }

                    final Predicate<String> predicate = Pattern.compile(patternValidator.pattern()).asMatchPredicate();
                    isMatches = predicate.test(value);
                } catch (final IllegalAccessException ex) {
                    throw new FieldValidatorException("Failed to process Parameter annotation", ex);
                }
            }
        }
        return isMatches;
    }
}
