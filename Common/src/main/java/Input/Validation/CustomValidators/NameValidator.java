package Input.Validation.CustomValidators;

import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

/**
 * Валидатор для поля Person - Name
 * @author uvuv-643
 * @version 1.0
 */
public class NameValidator implements Validator {

    @Override
    public ValidatedData<String> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Name cannot be null");
        } else if (data.isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        return new ValidatedData<>(data);
    }

}
