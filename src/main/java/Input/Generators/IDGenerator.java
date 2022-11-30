package Input.Generators;

import Entities.Person;
import Exceptions.ValidationException;
import Input.Validation.CustomValidators.IDValidator;

import java.util.ArrayList;

public class IDGenerator implements Generator<Long> {

    private final ArrayList<Person> collection;
    public IDGenerator(ArrayList<Person> collection) {
        this.collection = collection;
    }

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
