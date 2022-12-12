package Input.Validation.CustomValidators;

import Entities.Person;
import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

import java.util.ArrayList;

/**
 * Валидатор для поля Person - ID
 * @author uvuv-643
 * @version 1.0
 */
public class IDValidator implements Validator {

    @Override
    public ValidatedData<Long> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("ID cannot be null");
        }
        data = data.trim();
        long id;
        try {
            id = Long.parseLong(data);
            if (id <= 0) {
                throw new ValidationException("ID must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong ID provided");
        }
        return new ValidatedData<>(id);
    }

}
