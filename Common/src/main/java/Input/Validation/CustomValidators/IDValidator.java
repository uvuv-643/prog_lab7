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

    /**
     * Проверяет, является ли указанный ID уникальным на коллекции элементов
     * @param Id - проверяемый ID
     * @param collection - коллекция, над которой осуществляется проверка
     * @return Long - ID
     * @throws ValidationException - выбрасывается, если ID не является уникальным
     */
    public ValidatedData<Long> validateUnique(Long Id, ArrayList<Person> collection) throws ValidationException {
        boolean isUnique = collection.stream().noneMatch((element) -> element.getId().equals(Id));
        if (isUnique) {
            return new ValidatedData<>(Id);
        }
        throw new ValidationException("ID is not unique");
    }

    /**
     * Проверяет, является ли указанный ID неуникальным на коллекции элементов
     * @param Id - проверяемый ID
     * @param collection - коллекция, над которой осуществляется проверка
     * @return Long - ID
     * @throws ValidationException - выбрасывается, если ID является уникальным
     */
    public ValidatedData<Long> validateNotUnique(Long Id, ArrayList<Person> collection) throws ValidationException {
        boolean isUnique = collection.stream().noneMatch((element) -> element.getId().equals(Id));
        if (isUnique) {
            throw new ValidationException("ID is unique");
        }
        return new ValidatedData<>(Id);

    }

}
