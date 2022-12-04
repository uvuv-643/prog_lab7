package Input.Validation.CustomValidators;

import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

public class WeightValidator implements Validator {

    @Override
    public ValidatedData<Float> validate(String data) throws ValidationException {
        if (data == null) {
            throw new ValidationException("Weight cannot be null");
        }
        data = data.trim();
        float weight;
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
