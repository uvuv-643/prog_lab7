package CommandPattern;

import Services.Request;

import java.util.Optional;

/**
 * Интерфейс, представляющий собой единичную клиентскую команду
 * @author uvuv-643
 * @version 1.0
 */
public interface Command {

    /**
     * Обработать команду локально, на клиенте и подготовить её к отправке на сервер
     * @param invoker - объект, вызывающий исполнение команды
     * @param args - аргументы команды
     * @return Request - сформированный запрос на сервер
     */
    Optional<Request> execute(Invoker invoker, String[] args);

}
