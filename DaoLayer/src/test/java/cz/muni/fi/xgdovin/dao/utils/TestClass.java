package cz.muni.fi.xgdovin.dao.utils;

import cz.muni.fi.xgdovin.dao.domain.DomainObject;

import java.util.UUID;

class TestClass implements DomainObject {

    private UUID uuid;

    private boolean someBool;

    private String someText;

    private int someInt;

    private double someDouble;

    public TestClass(UUID uuid, boolean someBool, String someText, int someInt, double someDouble) {
        this.uuid = uuid;
        this.someBool = someBool;
        this.someText = someText;
        this.someInt = someInt;
        this.someDouble = someDouble;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public boolean isSomeBool() {
        return someBool;
    }

    public String getSomeText() {
        return someText;
    }

    public int getSomeInt() {
        return someInt;
    }

    public double getSomeDouble() {
        return someDouble;
    }
}
