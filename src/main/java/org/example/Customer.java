package org.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty province = new SimpleStringProperty();

    public Customer(String name, String province) {
        this.name.set(name);
        this.province.set(province);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getProvince() {
        return province.get();
    }

    public void setProvince(String province) {
        this.province.set(province);
    }

    public StringProperty provinceProperty() {
        return province;
    }

    @Override
    public String toString() {
        return name.get() + " (" + province.get() + ")";
    }
}