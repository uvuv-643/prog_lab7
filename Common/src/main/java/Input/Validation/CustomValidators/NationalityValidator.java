package Input.Validation.CustomValidators;

import Entities.Country;
import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

import java.util.Optional;

public class NationalityValidator implements Validator {

    @Override
    public ValidatedData<Optional<Country>> validate(String data) throws ValidationException {
        if (data != null) {
            data = data.trim();
            Optional<Country> nationality = switch (data.toLowerCase()) {
                case "" -> Optional.empty();
                case "russia" -> Optional.of(Country.RUSSIA);
                case "china" -> Optional.of(Country.CHINA);
                case "thailand" -> Optional.of(Country.THAILAND);
                case "vatican" -> Optional.of(Country.VATICAN);
                default -> null;
            };
            if (nationality == null) {
                throw new ValidationException("Incorrect nationality");
            }
            return new ValidatedData<>(nationality);
        } else {
            return new ValidatedData<>(Optional.empty());
        }
    }

    public ValidatedData<Country> validate(Country data) throws ValidationException {
        return new ValidatedData<>(data);
    }

}
