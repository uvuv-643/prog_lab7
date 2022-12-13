package Services;

import Entities.Person;

public class PersonOrderPair {
    private Person person;
    private int order;

    public PersonOrderPair(Person person, int order) {
        this.person = person;
        this.order = order;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
