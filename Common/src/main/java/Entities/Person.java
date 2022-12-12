package Entities;

import Exceptions.ValidationException;
import Input.Validation.CustomValidators.*;

import javax.sound.midi.Receiver;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author se.ifmo.ru
 * @version 1.0
 */
public class Person implements Comparable<Person>, Serializable {

    @Serial
    private static final long serialVersionUID = 1000000001L;

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    public ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int height; //Значение поля должно быть больше 0
    private float weight; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null


    private static final NameValidator nameValidator = new NameValidator();
    private static final CoordinatesValidator coordinatesValidator = new CoordinatesValidator();
    private static final HeightValidator heightValidator = new HeightValidator();
    private static final WeightValidator weightValidator = new WeightValidator();
    private static final EyeColorValidator eyeColorValidator = new EyeColorValidator();
    private static final NationalityValidator nationalityValidator = new NationalityValidator();
    private static final LocationValidator locationValidator = new LocationValidator();

    private Person(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, int height, float weight, Color eyeColor, Country nationality, Location location) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
        this.location = location;
    }

    public static Person personCreator(String name, Coordinates coordinates, int height, float weight, Color eyeColor, Country nationality, Location location) {
        return new Person(null, name, coordinates, null, height, weight, eyeColor, nationality, location);
    }

    public static Person personCreator(Person person) throws ValidationException {
        ZonedDateTime created_at = ZonedDateTime.now();
        nameValidator.validate(person.getName());
        coordinatesValidator.validate(person.getCoordinates());
        heightValidator.validate(person.getHeight());
        weightValidator.validate(person.getWeight());
        eyeColorValidator.validate(person.getEyeColor());
        nationalityValidator.validate(person.getNationality());
        locationValidator.validate(person.getLocation());
        return new Person(null, person.getName(), person.getCoordinates(), created_at, person.getHeight(), person.getWeight(), person.getEyeColor(), person.getNationality(), person.getLocation());
    }

    public static Person personUpdater(Person person, long generatedId, ZonedDateTime creationDate) throws ValidationException {
        nameValidator.validate(person.getName());
        coordinatesValidator.validate(person.getCoordinates());
        heightValidator.validate(person.getHeight());
        weightValidator.validate(person.getWeight());
        eyeColorValidator.validate(person.getEyeColor());
        nationalityValidator.validate(person.getNationality());
        locationValidator.validate(person.getLocation());
        return new Person(generatedId, person.getName(), person.getCoordinates(), creationDate, person.getHeight(), person.getWeight(), person.getEyeColor(), person.getNationality(), person.getLocation());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int compareTo(Person person) {
        return Integer.compare(this.height, person.getHeight());
    }

    @Override
    public String toString() {

        String id = String.format("ID: %s\n", this.id);
        String name = String.format("Name: %s\n", this.name);
        String coordinates = String.format("Coordinates: %s\n", this.coordinates);
        String createdAt = String.format("Created at: %s\n", this.creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")));
        String height = String.format("Height: %s\n", this.height);
        String weight = String.format("Weight: %s\n", this.weight);

        String eyeColor;
        if (this.eyeColor != null) {
            eyeColor = String.format("Eye color: %s\n", this.eyeColor);
        } else {
            eyeColor = "Eye color is not specified\n";
        }

        String nationality;
        if (this.nationality != null) {
            nationality = String.format("Nationality: %s\n", this.nationality);
        } else {
            nationality = "Nationality is not specified\n";
        }

        String location;
        if (this.location != null) {
            location = String.format("Location: %s", this.location);
        } else {
            location = "Location is not specified";
        }

        return  id +
                name +
                coordinates +
                createdAt +
                height +
                weight +
                eyeColor +
                nationality +
                location;

    }

}