package Input.Generators;

import Entities.Person;
import Exceptions.ValidationException;
import Input.Validation.CustomValidators.IDValidator;

import java.util.ArrayList;

/**
 * Генератор ID для записей в коллекции
 * @author uvuv-643
 * @version 1.0
 */
public class IDGenerator implements Generator<Long> {

    /** Коллекция, над которой выполняется генерация */
    private final ArrayList<Person> collection;

    /**
     * Конструктор для создания объекта генерации
     * @param collection - коллекция, над которой будет выполняться генерация
     */
    public IDGenerator(ArrayList<Person> collection) {
        this.collection = collection;
    }

    /**
     * Выполнить генерацию ID для заданной коллекции в поле генератора
     * @return Long - сгенерированный уникальный ID
     */
    @Override
    public Long generate() {
        long generatedId = -1L;
        IDValidator idValidator = new IDValidator();
        boolean generatedIdIsNotUnique = true;
        while (generatedIdIsNotUnique) {
            try {
                generatedId = Math.round(Math.random() * Long.MAX_VALUE + 1);
                idValidator.validateUnique(generatedId, this.collection);
                generatedIdIsNotUnique = false;
            } catch (ValidationException ignored) { }
        }
        return generatedId;
    }

}
