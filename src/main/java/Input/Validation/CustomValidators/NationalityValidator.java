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
        }
        Optional<Country> nationality = switch (data) {
            case null, "" -> Optional.empty();
            case "RUSSIA" -> Optional.of(Country.RUSSIA);
            case "CHINA" -> Optional.of(Country.CHINA);
            case "THAILAND" -> Optional.of(Country.THAILAND);
            case "VATICAN" -> Optional.of(Country.VATICAN);
            default -> null;
        };
        if (nationality == null) {
            throw new ValidationException("Incorrect nationality");
        }
        return new ValidatedData<>(nationality);
    }

    public ValidatedData<Country> validate(Country data) throws ValidationException {
        return new ValidatedData<>(data);
    }

}
