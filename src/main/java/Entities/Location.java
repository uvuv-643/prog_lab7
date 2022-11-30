package Entities;

public class Location {
    private double x;
    private int y;
    private String name; // Строка не может быть пустой, Поле не может быть null

    public Location(double x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String x = String.format("X: %.4f \n", this.x);
        String y = String.format("Y: %d \n", this.y);
        String locationName = String.format("Location name: %s \n", this.name);
        return x + y + locationName;
    }

}