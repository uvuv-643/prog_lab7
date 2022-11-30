package Input;

import Entities.*;
import Exceptions.ValidationException;
import Input.Validation.CustomValidators.*;

import java.util.Optional;
import java.util.Scanner;

public class InputManager {

    private final Scanner scanner = new Scanner(System.in);
    private final NameValidator nameValidator = new NameValidator();
    private final CoordinatesValidator coordinatesValidator = new CoordinatesValidator();
    private final HeightValidator heightValidator = new HeightValidator();
    private final WeightValidator weightValidator = new WeightValidator();
    private final EyeColorValidator eyeColorValidator = new EyeColorValidator();
    private final NationalityValidator nationalityValidator = new NationalityValidator();
    private final LocationValidator locationValidator = new LocationValidator();

    public String inputPersonName() {
        System.out.println("Input person name in the next line: ");
        String name = scanner.nextLine();
        try {
            return this.nameValidator.validate(name).getValidatedData();
        } catch (ValidationException validationException) {
            System.out.println(validationException.getMessage());
            return this.inputPersonName();
        }
    }

    public Coordinates inputPersonCoordinates() {
        System.out.println("Input coordinates (x, y) separated by space. Example: ");
        System.out.println("0.5 0.12");
        String coordinates = scanner.nextLine();
        try {
            return this.coordinatesValidator.validate(coordinates).getValidatedData();
        } catch (ValidationException validationException) {
            System.out.println(validationException.getMessage());
            return this.inputPersonCoordinates();
        }
    }

    public int inputPersonHeight() {
        System.out.println("Input height in the next line (integer): ");
        String height = scanner.nextLine();
        try {
            return this.heightValidator.validate(height).getValidatedData();
        } catch (ValidationException validationException) {
            System.out.println(validationException.getMessage());
            return this.inputPersonHeight();
        }
    }

    public float inputPersonWeight() {
        System.out.println("Input weight in the next line (float): ");
        String weight = scanner.nextLine();
        try {
            return this.weightValidator.validate(weight).getValidatedData();
        } catch (ValidationException validationException) {
            System.out.println(validationException.getMessage());
            return this.inputPersonWeight();
        }
    }

    public Optional<Color> inputPersonEyeColor() {
        System.out.println("Input person eye color from the following list:");
        Color[] colors = Color.values();
        for (Color color : colors) {
            System.out.println(color);
        }
        String color = scanner.nextLine();
        try {
            return this.eyeColorValidator.validate(color).getValidatedData();
        } catch (ValidationException validationException) {
            System.out.println(validationException.getMessage());
            return this.inputPersonEyeColor();
        }
    }

    public Optional<Country> inputPersonNationality() {
        System.out.println("Input person nationality:");
        Country[] countries = Country.values();
        for (Country country : countries) {
            System.out.println(country);
        }
        String nationality = scanner.nextLine();
        try {
            return this.nationalityValidator.validate(nationality).getValidatedData();
        } catch (ValidationException validationException) {
            System.out.println(validationException.getMessage());
            return this.inputPersonNationality();
        }
    }

    public Optional<Location> inputPersonLocation() {
        System.out.println("Input person location in the following format:");
        System.out.println("X Y Location_Name");
        System.out.println("X - double, Y - integer, Location_Name - string");
        System.out.println("Example:");
        System.out.println("15.1 26 Default location");
        String location = scanner.nextLine();
        try {
            return this.locationValidator.validate(location).getValidatedData();
        } catch (ValidationException validationException) {
            System.out.println(validationException.getMessage());
            return this.inputPersonLocation();
        }
    }

    public Person inputPerson(Long reservedId) throws ValidationException {
        String name = this.inputPersonName();
        Coordinates coordinates = this.inputPersonCoordinates();
        int height = this.inputPersonHeight();
        float weight = this.inputPersonWeight();
        Color eyeColor = this.inputPersonEyeColor().get();
        Country nationality = this.inputPersonNationality().get();
        Location location = this.inputPersonLocation().get();
        return Person.personCreator(reservedId, name, coordinates, height, weight, eyeColor, nationality, location);
    }

}
