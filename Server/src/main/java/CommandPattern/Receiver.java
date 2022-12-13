package CommandPattern;

import DAOPattern.PersonDAO;
import DAOPattern.UserDAO;
import Entities.Country;
import Entities.Person;
import Exceptions.DatabaseException;
import Exceptions.ValidationException;
import Input.FileManager.FileManager;
import Input.Validation.CustomValidators.IDValidator;
import Input.Validation.CustomValidators.IndexValidator;
import Input.Validation.CustomValidators.NationalityValidator;
import Output.OutputManager;
import Services.*;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

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
    private final UserDAO userService = new UserService();
    private final PersonDAO personService = new PersonService();
    private final IDValidator idValidator = new IDValidator(personService);

    /** Коллекция, над которой выполняются команды */
    private final ArrayList<Person> collection;

    /**
     * Создание экземпляра исполнителя команд
     */
    public Receiver() {
        creationDate = ZonedDateTime.now();
        this.collection = new ArrayList<>(personService.readAll());
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
    public Response add(Person person, long userId) {
        StringBuilder responseText = new StringBuilder();
        try {
            Person personValidated = Person.personCreator(person);
            Optional<Long> personId = personService.create(personValidated, userId);
            if (personId.isPresent()) {
                personValidated.setId(personId.get());
                collection.add(personValidated);
                responseText.append("Successfully added element to collection.");
            } else {
                throw new DatabaseException("Error when creating person");
            }
            return (new Response(true, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append("Provided data is incorrect. ").append(exception.getMessage());
            return (new Response(false, responseText.toString()));
        } catch (DatabaseException exception) {
            responseText.append(exception.getMessage());
            return (new Response(false, responseText.toString()));
        }
    }

    /**
     * Команда "update"
     * @param person - объект, представленный к добавлению в коллекцию
     * @param idRaw - id элемента в коллекции, который необходимо обновить
     * @return Response - ответ на запрос выполнения команды
     */
    public Response update(Person person, String idRaw, long userId) {
        StringBuilder responseText = new StringBuilder();
        long id;
        try {
            id = idValidator.validate(idRaw).getValidatedData();
            idValidator.validateNotUnique(id, userId);
            Person personValidated = Person.personCreator(person);
            boolean isUpdatedPerson = personService.updateById(id, personValidated, userId);
            if (isUpdatedPerson) {
                for (Person collectionPerson : collection) {
                    if (collectionPerson.getId().equals(id)) {
                        personValidated.setCreationDate(collectionPerson.getCreationDate());
                        collection.set(collection.indexOf(collectionPerson), personValidated);
                    }
                }
                return (new Response(true, responseText.toString()));
            } else {
                return (new Response(false, "You cannot update this element"));
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
    public Response removeById(String idRaw, long userId) {
        StringBuilder responseText = new StringBuilder();
        long id;
        try {
            id = idValidator.validate(idRaw).getValidatedData();
            idValidator.validateNotUnique(id, userId);
            try {
                boolean isRemovedPerson = personService.removeById(id);
                if (isRemovedPerson) {
                    Optional<Person> elementInCollection = collection.stream().filter((element) -> element.getId().equals(id)).findFirst();
                    elementInCollection.ifPresent(collection::remove);
                    return (new Response(true, responseText.toString()));
                } else {
                    throw new ValidationException("Not found element with this id");
                }
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
    public Response clear(long userId) {
        boolean isCleared = personService.clear(userId);
        if (isCleared) {
            collection.clear();
            return (new Response(true, ""));
        } else {
            return (new Response(false, "Unable to clear collection"));
        }
    }

    /**
     * Команда "save"
     * @return Response - ответ на запрос выполнения команды
     */

    @Deprecated
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
    public Response removeAt(String indexRaw, long userId) {
        StringBuilder responseText = new StringBuilder();
        int index;
        IndexValidator indexValidator = new IndexValidator();
        try {
            index = indexValidator.validate(indexRaw).getValidatedData();
            Person personOnRemoving = collection.get(index);
            idValidator.validateNotUnique(personOnRemoving.getId(), userId);
            boolean isRemoved = personService.removeById(personOnRemoving.getId());
            if (isRemoved) {
                collection.remove(index);
                return (new Response(true, responseText.toString()));
            } else {
                responseText.append("Unable to remove this person. Perhaps you are not owner of this object.");
            }
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
    public Response addIfMin(Person person, long userId) {
        StringBuilder responseText = new StringBuilder();
        try {
            Person personValidated = Person.personCreator(person);
            Optional<Person> min = collection.stream().min(Comparator.comparingInt(Person::getHeight));
            if (min.isPresent() && personValidated.compareTo(min.get()) < 0 || min.isEmpty()) {
                Optional<Long> personId = personService.create(personValidated, userId);
                if (personId.isPresent()) {
                    personValidated.setId(personId.get());
                    collection.add(personValidated);
                    responseText.append("Successfully added element to collection.");
                    return (new Response(true, responseText.toString()));
                } else {
                    throw new DatabaseException("Error when creating person");
                }
            }
        } catch (ValidationException exception) {
            responseText.append("Provided data is incorrect. ").append(exception.getMessage());
            return (new Response(false, responseText.toString()));
        } catch (DatabaseException exception) {
            responseText.append(exception.getMessage());
            return (new Response(false, responseText.toString()));
        }
        return (new Response(false, "This person is not minimal in collection"));
    }

    /**
     * Команда "reorder"
     * @return Response - ответ на запрос выполнения команды
     */
    public Response reorder(long userId) {
//        List<Integer> order = new ArrayList<>(IntStream.range(0, this.collection.size())
//                .mapToObj(i -> new PersonOrderPair(this.collection.get(i), i))
//                .filter(personOrderPair -> personOrderPair.getPerson().getUserId().equals(userId))
//                .map(PersonOrderPair::getOrder).toList());
//        Collections.reverse(order);
        boolean isReordered = personService.reorder(userId);
        if (isReordered) {
            return new Response(true, "Successfully cleared");
        } else {
            return new Response(false, "You cannot reorder your collection");
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

    public Response register(String login, String password) {
        Optional<Long> userId = userService.create(new LoginCredentials(login, password));
        return userId.map(aLong -> new Response(true, "User registered successfully.", aLong)).orElseGet(() -> new Response(false, "Cannot register user"));
    }

    public Response auth(String login, String password) {
        Optional<Long> userId = userService.check(new LoginCredentials(login, password));
        return userId.map(aLong -> new Response(true, "Login", aLong)).orElseGet(() -> new Response(false, "Error"));
    }

    public Response checkId(String idRaw, long userId) {
        long id;
        try {
            id = idValidator.validate(idRaw).getValidatedData();
            idValidator.validateNotUnique(id, userId);
            return new Response(true, "Ok");
        } catch (ValidationException exception) {
            return (new Response(false, "Not ok"));
        }
    }

}
