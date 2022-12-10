package Input.Validation.CustomValidators;

import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

/**
 * Валидатор для поля Person - Weight
 * @author uvuv-643
 * @version 1.0
 */
public class WeightValidator implements Validator {

    private final int MAXIMUM_FLOAT_LENGTH = 18;

    @Override
    public ValidatedData<Float> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Weight cannot be null");
        }
        data = data.trim();
        float weight;
        if (data.length() > MAXIMUM_FLOAT_LENGTH) {
            throw new ValidationException("Maximum size of weight exceeded");
        }
        try {
            weight = Float.parseFloat(data);
            if (weight <= 0) {
                throw new ValidationException("Weight must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong data provided");
        }
        return new ValidatedData<>(weight);
    }

    public ValidatedData<Float> validate(float data) throws ValidationException {
        if (data <= 0) {
            throw new ValidationException("Weight must be greater than zero");
        }
        return new ValidatedData<>(data);
    }

}
