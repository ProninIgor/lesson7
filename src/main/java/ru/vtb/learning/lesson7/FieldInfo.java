package ru.vtb.learning.lesson7;

import java.lang.reflect.Field;

public class FieldInfo {
    private AppColumn appColumn;
    private Field field;

    public FieldInfo(AppColumn appColumn, Field field) {
        this.appColumn = appColumn;
        this.field = field;
    }

    public AppColumn getAppColumn() {
        return appColumn;
    }

    public Field getField() {
        return field;
    }
}
