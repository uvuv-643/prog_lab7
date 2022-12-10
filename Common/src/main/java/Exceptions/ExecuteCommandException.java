package Exceptions;

/**
 * Исключение, необходимое для контроля выполнения исполнения единичной команды. Наличие данного исключение значит, что команда не может быть выполнена
 * @author uvuv-643
 * @version 1.0
 */
public class ExecuteCommandException extends Exception {

    public ExecuteCommandException(String message) {
        super(message);
    }

}
