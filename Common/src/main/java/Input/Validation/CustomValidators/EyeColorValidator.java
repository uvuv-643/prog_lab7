package Input.Validation.CustomValidators;

import Entities.Color;
import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

import java.util.Optional;

/**
 * Валидатор для поля Person - EyeColor
 * @author uvuv-643
 * @version 1.0
 */
public class EyeColorValidator implements Validator {

    @Override
    public ValidatedData<Optional<Color>> validate(String data) throws ValidationException {
        if (data != null) {
            data = data.trim();
            Optional<Color> color = switch (data.toLowerCase()) {
                case "" -> Optional.empty();
                case "black" -> Optional.of(Color.BLACK);
                case "blue" -> Optional.of(Color.BLUE);
                case "yellow" -> Optional.of(Color.YELLOW);
                default -> null;
            };
            if (color == null) {
                throw new ValidationException("Incorrect eye color");
            }
            return new ValidatedData<>(color);
        } else {
            return new ValidatedData<>(Optional.empty());
        }

    }

    public ValidatedData<Color> validate(Color data) throws ValidationException {
        return new ValidatedData<>(data);
    }

}
