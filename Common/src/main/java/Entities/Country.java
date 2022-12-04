package Entities;

public enum Country {
    RUSSIA(14400000),
    CHINA(1500000000),
    VATICAN(825),
    THAILAND(70000000);

    private long population;

    Country(long population) {
        this.population = population;
    }

    public long getPopulation() {
        return this.population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

}