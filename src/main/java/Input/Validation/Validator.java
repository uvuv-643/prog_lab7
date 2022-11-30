package Input.Validation;

import Exceptions.ValidationException;

public interface Validator {

    public ValidatedData validate(String data) throws ValidationException;

}
