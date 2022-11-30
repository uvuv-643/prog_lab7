package Entities;


public class Coordinates {
    private Float x; //Значение поля должно быть больше -600, Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        String x = String.format("X: %.4f \n", this.x);
        String y = String.format("Y: %.4f \n", this.y);
        return x + y;
    }

}