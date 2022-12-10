package Exceptions;

/**
 * Исключение, необходимое для валидации. Наличие данного исключение значит, что валидация поля не была пройдена
 * @author uvuv-643
 * @version 1.0
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
