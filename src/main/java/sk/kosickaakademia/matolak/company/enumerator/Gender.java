package sk.kosickaakademia.matolak.company.enumerator;

import sk.kosickaakademia.matolak.company.entity.User;

import java.util.List;

public enum Gender {
    MALE(0), FEMALE(1), OTHER(2);

    private int value;

    Gender(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
