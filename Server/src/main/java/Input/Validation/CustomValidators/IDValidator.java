package Input.Validation.CustomValidators;

import DAOPattern.PersonDAO;
import Entities.Person;
import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;
import Services.PersonService;

import java.util.ArrayList;

/**
 * Валидатор для поля Person - ID
 * @author uvuv-643
 * @version 1.0
 */
public class IDValidator implements Validator {

    private PersonDAO personService;

    public IDValidator(PersonDAO personService) {
        this.personService = personService;
    }

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
     * Проверяет, является ли указанный ID уникальным на коллекции элементов и принадлежит пользователю
     * @param id - проверяемый ID
     * @param userId - пользователь, на принадлежность которому проверяется элемент
     * @return Long - ID
     * @throws ValidationException - выбрасывается, если ID не является уникальным
     */
    @Deprecated
    public ValidatedData<Long> validateUnique(Long id, Long userId) throws ValidationException {
        boolean isUnique = personService.checkById(id, userId);
        if (isUnique) {
            return new ValidatedData<>(id);
        }
        throw new ValidationException("ID is not unique");
    }

    /**
     * Проверяет, является ли указанный ID неуникальным на коллекции элементов и принадлежит пользователю
     * @param id - проверяемый ID
     * @param userId - пользователь, на принадлежность которому проверяется элемент
     * @return Long - ID
     * @throws ValidationException - выбрасывается, если ID является уникальным
     */
    public ValidatedData<Long> validateNotUnique(Long id, Long userId) throws ValidationException {
        boolean isUnique = !personService.checkById(id, userId);
        if (isUnique) {
            throw new ValidationException("ID is unique");
        }
        return new ValidatedData<>(id);

    }

}
