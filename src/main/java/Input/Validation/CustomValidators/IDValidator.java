package Input.Validation.CustomValidators;

import Entities.Person;
import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

import java.util.ArrayList;

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

    public ValidatedData<Long> validateUnique(Long Id, ArrayList<Person> collection) throws ValidationException {
        boolean isUnique = collection.stream().noneMatch((element) -> element.getId().equals(Id));
        if (isUnique) {
            return new ValidatedData<>(Id);
        }
        throw new ValidationException("ID is not unique");
    }

    public ValidatedData<Long> validateNotUnique(Long Id, ArrayList<Person> collection) throws ValidationException {
        boolean isUnique = collection.stream().noneMatch((element) -> element.getId().equals(Id));
        if (isUnique) {
            throw new ValidationException("ID is unique");
        }
        return new ValidatedData<>(Id);

    }

}
