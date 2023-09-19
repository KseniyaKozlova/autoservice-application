package by.autodiagnostic.validation;

public interface FieldValidator {

    boolean workField(Object object) throws FieldValidatorException;
}
