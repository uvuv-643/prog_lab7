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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class Receiver {

    private final ZonedDateTime creationDate;
    private final OutputManager outputManager = new OutputManager();

    private final ArrayList<Person> collection;


    public Receiver(ArrayList<Person> collection) {
        creationDate = ZonedDateTime.now();
        this.collection = collection;
    }

    public ArrayList<Person> getCollection() {
        return this.collection;
    }

    public Optional<Response> info() {
        String typeOfCollection = String.format("Type of collection: %s", collection.getClass());
        String dateOfInitialization = String.format("Initialization date: %s", creationDate);
        String countOfElements = String.format("Elements in collection: %d", collection.size());
        return Optional.of(new Response(true, typeOfCollection + "\n" + dateOfInitialization + "\n" + countOfElements + "\n"));
    }

    public Optional<Response> show() {
        return Optional.of(new Response(true, outputManager.showCollection(collection)));
    }

    public Optional<Response> add(Person person) {
        StringBuilder responseText = new StringBuilder();
        try {
            IDGenerator idGenerator = new IDGenerator(collection);
            long generatedId = idGenerator.generate();
            Person personValidated = Person.personCreator(person, generatedId);
            collection.add(personValidated);
            responseText.append("Successfully added element to collection.").append("\n");
            return Optional.of(new Response(true, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append("Provided data is incorrect. ").append(exception.getMessage()).append("\n");
            return Optional.of(new Response(false, responseText.toString()));
        }
    }

    public Optional<Response> update(Person person, String idRaw) {
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
                return Optional.of(new Response(true, responseText.toString()));
            } catch (ValidationException exception) {
                responseText.append("Internal server error. ").append(exception.getMessage()).append("\n");
                return Optional.of(new Response(false, responseText.toString()));
            }
        } catch (ValidationException exception) {
            responseText.append("Passed incorrect ID").append("\n");
            return Optional.of(new Response(false, responseText.toString()));
        }
    }

    public Optional<Response> removeById(String idRaw) {
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
                return Optional.of(new Response(true, responseText.toString()));
            } catch (ValidationException exception) {
                responseText.append("Internal server error. ").append(exception.getMessage()).append("\n");
                return Optional.of(new Response(false, responseText.toString()));
            }
        } catch (ValidationException exception) {
            responseText.append("Passed incorrect ID").append("\n");
            return Optional.of(new Response(false, responseText.toString()));
        }
    }

    public Optional<Response> clear() {
        collection.clear();
        return Optional.of(new Response(true, ""));
    }

    public Optional<Response> save() {
        StringBuilder responseText = new StringBuilder();
        FileManager worker = new FileManager();
        try {
            worker.writeInFile(collection);
        } catch (IOException exception) {
            responseText.append("Passed incorrect argument or you have no access to file").append("\n");
            return Optional.of(new Response(true, responseText.toString()));
        }
        return Optional.of(new Response(true, "Data was saved into the file"));
    }

    public Optional<Response> executeScript() {
        return Optional.of(new Response(false, "Cannot load execute_script directly"));
    }

    public void exit() {
        System.exit(0);
    }

    public Optional<Response> removeAt(String indexRaw) {
        StringBuilder responseText = new StringBuilder();
        int index;
        IndexValidator indexValidator = new IndexValidator();
        try {
            index = indexValidator.validate(indexRaw).getValidatedData();
            collection.remove(index);
            return Optional.of(new Response(true, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append(exception.getMessage());
        } catch (IndexOutOfBoundsException exception) {
            responseText.append("Index out of bounds");
        }
        return Optional.of(new Response(false, responseText.toString()));
    }

    public Optional<Response> addIfMin(Person person) {
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
            return Optional.of(new Response(min.isEmpty() || personValidated.compareTo(min.get()) < 0, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append("Internal server error. ").append(exception.getMessage());
            return Optional.of(new Response(false, responseText.toString()));
        }
    }

    public Optional<Response> reorder() {
        Collections.reverse(collection);
        return Optional.of(new Response(true, ""));
    }

    public Optional<Response> filterGreaterThanNationality(String nationalityRaw) {
        StringBuilder responseText = new StringBuilder();
        NationalityValidator nationalityValidator = new NationalityValidator();
        try {
            Country nationality = nationalityValidator.validate(nationalityRaw).getValidatedData().get();
            long targetPopulation = nationality.getPopulation();
            ArrayList<Person> filteredCollection = new ArrayList<>(collection.stream().filter((element) -> element.getNationality().getPopulation() > targetPopulation).toList());
            responseText.append(outputManager.showCollection(filteredCollection));
            return Optional.of(new Response(true, responseText.toString()));
        } catch (ValidationException exception) {
            responseText.append(exception.getMessage());
            return Optional.of(new Response(false, responseText.toString()));
        }
    }

    public Optional<Response> printDescending() {
        ArrayList<Person> reversedCollection = new ArrayList<>(collection.stream().sorted().toList());
        Collections.reverse(reversedCollection);
        return Optional.of(new Response(true, outputManager.showCollection(reversedCollection)));
    }

    public Optional<Response> printFieldDescendingOrder() {
        StringBuilder responseText = new StringBuilder();
        Country[] countries = collection.stream().sorted(Comparator.reverseOrder()).map(Person::getNationality).toArray(Country[]::new);
        if (countries.length == 0) {
            responseText.append("Collection in empty");
        } else {
            responseText.append("Field <Location> in collection: ");
            for (Country country : countries) {
                responseText.append(country);
            }
        }
        return Optional.of(new Response(true, responseText.toString()));
    }

}
