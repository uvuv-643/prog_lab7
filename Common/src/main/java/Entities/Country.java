package Entities;

/**
 * @author se.ifmo.ru
 * @version 1.0
 */
public enum Country {
    VATICAN(825),
    THAILAND(70000000),
    RUSSIA(144000000),
    CHINA(1500000000);

    private long population;

    Country(long population) {
        this.population = population;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}