package Exceptions;

/**
 * Исключение, необходимое для контроля выполнения исполнения файла в команде execute_script. Наличие данного исключение значит, что файл не может быть обработан, так как он уже был рекурсивно обработан командой execute_script
 * @author uvuv-643
 * @version 1.0
 */
public class ExecuteScriptException extends Exception {

    public ExecuteScriptException(String message) {
        super(message);
    }

}
