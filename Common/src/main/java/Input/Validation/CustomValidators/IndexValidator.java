package Input.Validation.CustomValidators;

import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

/**
 * Валидатор для индексов
 * @author uvuv-643
 * @version 1.0
 */
public class IndexValidator implements Validator {

    @Override
    public ValidatedData<Integer> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Index cannot be null");
        }
        data = data.trim();
        int index;
        try {
            index = Integer.parseInt(data);
            if (index < 0) {
                throw new ValidationException("Index must be non-negative");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong index provided");
        }
        return new ValidatedData<>(index);
    }

}
