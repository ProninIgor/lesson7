package ru.vtb.learning.lesson7;

public class EntityMetadata {
    public EntityMetadata(boolean isAppTableAnnotation, String appTableName, FieldInfo[] declaredFields) {
        this.isAppTableAnnotation = isAppTableAnnotation;
        this.appTableName = appTableName;
        this.declaredFields = declaredFields;
    }

    public boolean isAppTableAnnotation() {
        return isAppTableAnnotation;
    }

    public String getAppTableName() {
        return appTableName;
    }

    public FieldInfo[] getDeclaredFields() {
        return declaredFields;
    }

    private boolean isAppTableAnnotation;

    private String appTableName;

    private FieldInfo[] declaredFields;
}
