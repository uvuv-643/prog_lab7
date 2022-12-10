package Services;

import java.io.Serial;
import java.io.Serializable;

/**
 * Общий класс для всех ответов, возвращаемых сервером для клиентов. Поддерживает сериализацию.
 * @author uvuv-643
 * @version 1.0
 */
public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 211103L;

    /** Статус успеха выполнения команды */
    private boolean success;

    /** Сообщение пользователю (успешный результат выполнения, ошибка, уведомление) */
    private String message;

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "success: " + Boolean.toString(success) + "; content: " + (message.length() > 30 ? message.substring(0, 30).replaceAll("\n", " ") + "..." : message.replaceAll("\n", " "));
    }

}