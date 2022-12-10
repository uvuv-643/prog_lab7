package Input;

import Entities.*;
import Exceptions.ValidationException;
import Input.Validation.CustomValidators.*;

import java.util.Optional;
import java.util.Scanner;

/**
 * Класс, осуществляющий ввод данных из стандартного потока
 * @author uvuv-643
 * @version 1.0
 */
public class InputManager {

    /** Объект для ввода данных из стандартного потока */
    private final Scanner scanner = new Scanner(System.in);

    private final NameValidator nameValidator = new NameValidator();
    private final CoordinatesValidator coordinatesValidator = new CoordinatesValidator();
    private final HeightValidator heightValidator = new HeightValidator();
    private final WeightValidator weightValidator = new WeightValidator();
    private final EyeColorValidator eyeColorValidator = new EyeColorValidator();
    private final NationalityValidator nationalityValidator = new NationalityValidator();
    private final LocationValidator locationValidator = new LocationValidator();

    /**
     * Ввести имя пользователя из стандартного потока
     * @return String - валидное пользовательское имя
     */
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

    /**
     * Ввести координаты пользователя из стандартного потока
     * @return Coordinates - валидные координаты
     */
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

    /**
     * Ввести высоту пользователя из стандартного потока
     * @return int - валидная высота
     */
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

    /**
     * Ввести вес пользователя из стандартного потока
     * @return float - валидный вес
     */
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

    /**
     * Ввести цвет глаз пользователя из стандартного потока
     * @return Color - валидный цвет глаз (может быть null)
     */
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

    /**
     * Ввести страну пользователя из стандартного потока
     * @return Country - валидная страна (может быть null)
     */
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

    /**
     * Ввести месторасположение пользователя из стандартного потока
     * @return Location - валидное месторасположение (может быть null)
     */
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

    /**
     * Ввести из стандартного потока все данные о пользователе и создать объект класса Person
     * @return Person - объект без полей id, created_at, готовый для отправки на сервер
     */
    public Person inputPerson() {
        String name = this.inputPersonName();
        Coordinates coordinates = this.inputPersonCoordinates();
        int height = this.inputPersonHeight();
        float weight = this.inputPersonWeight();
        Color eyeColor = null; Country nationality = null; Location location = null;
        Optional<Color> eyeColorRaw = this.inputPersonEyeColor();
        Optional<Country> nationalityRaw = this.inputPersonNationality();
        Optional<Location> locationRaw = this.inputPersonLocation();
        if (eyeColorRaw.isPresent()) {
            eyeColor = eyeColorRaw.get();
        }
        if (nationalityRaw.isPresent()) {
            nationality = nationalityRaw.get();
        }
        if (locationRaw.isPresent()) {
            location = locationRaw.get();
        }
        return Person.personCreator(name, coordinates, height, weight, eyeColor, nationality, location);
    }

}
