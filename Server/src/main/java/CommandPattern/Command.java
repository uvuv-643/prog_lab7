package CommandPattern;

import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Интерфейс, представляющий собой единичную серверную команду
 * @author uvuv-643
 * @version 1.0
 */
public interface Command {

    /**
     * Обработать команду и подготовить ответ для клиента
     * @param request - объект запроса
     * @param invoker - объект, вызывающий исполнение команды на сервере
     * @return Response - сформированный ответ на запрос
     * @see Request
     * @see Response
     */
    Response execute(Request request, Invoker invoker);

    /**
     * Получить справку по текущей команде
     * @return String - справка по текущей команде
     */
    String getHelp();

}
