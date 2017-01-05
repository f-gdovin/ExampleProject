package cz.muni.fi.xgdovin.dao.utils;

import cz.muni.fi.xgdovin.dao.domain.DomainObject;

class TestClass implements DomainObject {

    private long id;

    private boolean someBool;

    private String someText;

    private int someInt;

    private double someDouble;

    public TestClass(long id, boolean someBool, String someText, int someInt, double someDouble) {
        this.id = id;
        this.someBool = someBool;
        this.someText = someText;
        this.someInt = someInt;
        this.someDouble = someDouble;
    }

    @Override
    public long getId() {
        return id;
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
