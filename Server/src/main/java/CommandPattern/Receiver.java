package CommandPattern;

import Entities.Country;
import Entities.Person;
import Exceptions.ValidationException;
import Input.FileManager.FileManager;
import Input.Generators.IDGenerator;
import Input.Validation.CustomValidators.IDValidator;
import Input.Validation.CustomValidators.IndexValidator;
import Input.Validation.CustomValidators.NationalityValidator;
import Output.OutputManager;
import Services.Request;
import Services.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

/**
 * Класс, представляющий собой сущность исполнителя команд
 * @author uvuv-643
 * @version 1.0
 */
public class Receiver {

    /** Дата инициализации коллекции */
    private final ZonedDateTime creationDate;

    /** Объект, управляющий выводом информации со стандартного потока ввода */
    private final OutputManager outputManager = new OutputManager();

    /** Коллекция, над которой выполняются команды */
    private final ArrayList<Person> collection;

    /**
     * Создание экземпляра исполнителя команд
     * @param collection - коллекция, над которой выполняются команды
     */
    public Receiver(ArrayList<Person> collection) {
        creationDate = ZonedDateTime.now();
        this.collection = collection;
    }

    /**
     * Getter для поля collection
     * @return ArrayList - коллекция
     */
    public ArrayList<Person> getCollection() {
        return this.collection;
    }

    /**
     * Команда "info"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response info() {
        String typeOfCollection = String.format("Type of collection: %s", collection.getClass());
        String dateOfInitialization = String.format("Initialization date: %s", creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")));
        String countOfElements = String.format("Elements in collection: %d", collection.size());
        return (new Response(true, typeOfCollection + "\n" + dateOfInitialization + "\n" + countOfElements));
    }

    /**
     * Команда "show"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response show() {
        return (new Response(true, outputManager.showCollection(collection)));
    }

    /**
     * Команда "add"
     * @param person - объект, представленный к добавлению в коллекцию
     * @return Response - ответ на запрос выполнения команды
     */
    public Response add(Person person) {
        StringBuilder responseText = new StringBuilder();
        try {
            IDGenerator idGenerator = new IDGenerator(collection);
            long generatedId = idGenerator.generate();
            Person personValidated = Person.personCreator(person, generatedId);
            collection.add(personValidated);
            responseText.append("Successfully added element to collection.");
            return (new Response(true, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append("Provided data is incorrect. ").append(exception.getMessage());
            return (new Response(false, responseText.toString()));
        }
    }

    /**
     * Команда "update"
     * @param person - объект, представленный к добавлению в коллекцию
     * @param idRaw - id элемента в коллекции, который необходимо обновить
     * @return Response - ответ на запрос выполнения команды
     */
    public Response update(Person person, String idRaw) {
        StringBuilder responseText = new StringBuilder();
        long id;
        IDValidator idValidator = new IDValidator();
        try {
            id = idValidator.validate(idRaw).getValidatedData();
            idValidator.validateNotUnique(id, collection);
            try {
                Person personValidated = Person.personCreator(person, id);
                int updatedCount = 0;
                for (Person collectionPerson : collection) {
                    if (collectionPerson.getId().equals(id)) {
                        personValidated.setCreationDate(collectionPerson.getCreationDate());
                        collection.set(collection.indexOf(collectionPerson), personValidated);
                        updatedCount++;
                    }
                }
                if (updatedCount == 0) {
                    throw new ValidationException("Not found element with this id");
                }
                return (new Response(true, responseText.toString()));
            } catch (ValidationException exception) {
                responseText.append("Internal server error. ").append(exception.getMessage());
                return (new Response(false, responseText.toString()));
            }
        } catch (ValidationException exception) {
            responseText.append("Passed incorrect ID");
            return (new Response(false, responseText.toString()));
        }
    }

    /**
     * Команда "remove_by_id"
     * @param idRaw - id элемента в коллекции, который необходимо удалить
     * @return Response - ответ на запрос выполнения команды
     */
    public Response removeById(String idRaw) {
        StringBuilder responseText = new StringBuilder();
        long id;
        IDValidator idValidator = new IDValidator();
        try {
            id = idValidator.validate(idRaw).getValidatedData();
            idValidator.validateNotUnique(id, collection);
            try {
                Optional<Person> elementInCollection = collection.stream().filter((element) -> element.getId().equals(id)).findFirst();
                elementInCollection.ifPresent(collection::remove);
                if (elementInCollection.isEmpty()) {
                    throw new ValidationException("Not found element with this id");
                }
                return (new Response(true, responseText.toString()));
            } catch (ValidationException exception) {
                responseText.append("Internal server error. ").append(exception.getMessage());
                return (new Response(false, responseText.toString()));
            }
        } catch (ValidationException exception) {
            responseText.append("Passed incorrect ID");
            return (new Response(false, responseText.toString()));
        }
    }

    /**
     * Команда "clear"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response clear() {
        collection.clear();
        return (new Response(true, ""));
    }

    /**
     * Команда "save"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response save() {
        StringBuilder responseText = new StringBuilder();
        FileManager worker = new FileManager();
        try {
            worker.writeInFile(collection);
        } catch (IOException exception) {
            responseText.append("Passed incorrect argument or you have no access to file");
            return (new Response(true, responseText.toString()));
        }
        return (new Response(true, "Data was saved into the file"));
    }

    /**
     * @deprecated
     * Команда "execute_script"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response executeScript() {
        return (new Response(false, "Cannot load execute_script directly"));
    }

    /**
     * Команда "exit"
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Команда "remove_at"
     * @param indexRaw - индекс элемента в коллекции, который необходимо удалить
     * @return Response - ответ на запрос выполнения команды
     */
    public Response removeAt(String indexRaw) {
        StringBuilder responseText = new StringBuilder();
        int index;
        IndexValidator indexValidator = new IndexValidator();
        try {
            index = indexValidator.validate(indexRaw).getValidatedData();
            collection.remove(index);
            return (new Response(true, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append(exception.getMessage());
        } catch (IndexOutOfBoundsException exception) {
            responseText.append("Index out of bounds");
        }
        return (new Response(false, responseText.toString()));
    }

    /**
     * Команда "add_if_min"
     * @param person - объект, который необходимо добавить в коллекцию
     * @return Response - ответ на запрос выполнения команды
     */
    public Response addIfMin(Person person) {
        StringBuilder responseText = new StringBuilder();
        try {
            IDGenerator idGenerator = new IDGenerator(collection);
            long generatedId = idGenerator.generate();
            Person personValidated = Person.personCreator(person, generatedId);
            Optional<Person> min = collection.stream().min(Comparator.comparingInt(Person::getHeight));
            min.ifPresentOrElse((element) -> {
                if (personValidated.compareTo(element) < 0) {
                    collection.add(personValidated);
                }
            }, () -> collection.add(personValidated));
            return (new Response(min.isEmpty() || personValidated.compareTo(min.get()) < 0, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append("Internal server error. ").append(exception.getMessage());
            return (new Response(false, responseText.toString()));
        }
    }

    /**
     * Команда "reorder"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response reorder() {
        if (collection.size() > 0) {
            Collections.reverse(collection);
            return (new Response(true, ""));
        } else {
            return (new Response(false, "Collection is empty"));
        }
    }

    /**
     * Команда "filter_greater_than_nationality"
     * @param nationalityRaw - национальность, параметр команды
     * @return Response - ответ на запрос выполнения команды
     */
    public Response filterGreaterThanNationality(String nationalityRaw) {
        StringBuilder responseText = new StringBuilder();
        NationalityValidator nationalityValidator = new NationalityValidator();
        try {
            Optional<Country> nationalityRawValue = nationalityValidator.validate(nationalityRaw).getValidatedData();
            if (nationalityRawValue.isPresent()) {
                Country nationality = nationalityRawValue.get();
                ArrayList<Person> filteredCollection = new ArrayList<>(collection.stream().filter((element) -> {
                    if (element.getNationality() != null) {
                        return element.getNationality().compareTo(nationality) > 0;
                    } else {
                        return false;
                    }
                }).toList());
                responseText.append(outputManager.showCollection(filteredCollection));
            } else {
                responseText.append(outputManager.showCollection(collection));
            }
            return (new Response(true, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append(exception.getMessage());
            return (new Response(false, responseText.toString()));
        }
    }

    /**
     * Команда "print_descending"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response printDescending() {
        ArrayList<Person> sortedCollection = new ArrayList<>(collection.stream().sorted(Comparator.reverseOrder()).toList());
        return (new Response(true, outputManager.showCollection(sortedCollection)));
    }

    /**
     * Команда "print_field_descending_order"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response printFieldDescendingOrder() {
        StringBuilder responseText = new StringBuilder();
        Country[] countries = collection.stream().sorted(Comparator.reverseOrder()).map(Person::getNationality).toArray(Country[]::new);
        if (countries.length == 0) {
            responseText.append("Collection in empty");
        } else {
            responseText.append("Field <Location> in collection: ").append("\n");
            for (Country country : countries) {
                responseText.append(country).append("\n");
            }
        }
        return (new Response(true, responseText.toString()));
    }

}
