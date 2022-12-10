package Input.Validation;

import Exceptions.ValidationException;

/**
 * Интерфейс взаимодействия с валидаторами
 * @author uvuv-643
 * @version 1.0
 */
public interface Validator {

    /**
     * Осуществляет проверку передаваемых данных в метод
     * @param data - данные для валиции
     * @return ValidatedData - generic, хранящий данные, которые прошли валидацию
     * @throws ValidationException - исключение, выбрасываемое из метода при появлении ошибок валидации
     * @see ValidatedData
     * @see ValidationException
     */
    ValidatedData validate(String data) throws ValidationException;

}
