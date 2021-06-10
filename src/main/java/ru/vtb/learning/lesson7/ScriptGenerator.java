package ru.vtb.learning.lesson7;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScriptGenerator {
    public static String getCreateTableScript(Class entityType){
        EntityMetadata entityMetadata = getEntityMetadata(entityType);

        checkEntityMetadata(entityMetadata);

        FieldInfo[] declaredFields = entityMetadata.getDeclaredFields();

        List<String> collect = Arrays.stream(declaredFields)
                .map(x -> x.getAppColumn().name() + " " + getDbType(x.getField().getType()))
                .collect(Collectors.toList());

        String name = entityMetadata.getAppTableName();

        String result = "create table " + name + "(" + String.join(",", collect) + ");";
        return result;
    }

    public static String getCreateObjectPrepareStatementScript(Object obj) throws IllegalAccessException {
        Class objectType = obj.getClass();
        EntityMetadata entityMetadata = getEntityMetadata(objectType);
        checkEntityMetadata(entityMetadata);

        StringBuilder sb = new StringBuilder();
        sb.append("Insert into");
        sb.append(" ");
        sb.append(entityMetadata.getAppTableName());
        sb.append(" ");
        sb.append("(");

        FieldInfo[] declaredFields = entityMetadata.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            FieldInfo f = declaredFields[i];
            sb.append(f.getAppColumn().name());

            if (i != declaredFields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(") ");
        sb.append("values(");

        for (int i = 0; i < declaredFields.length; i++) {
            FieldInfo f = declaredFields[i];
            sb.append("?");

            if (i != declaredFields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
    }

    private static EntityMetadata getEntityMetadata(Class entityType){
        Annotation[] annotations = entityType.getAnnotations();
        if(!entityType.isAnnotationPresent(AppTable.class)){
            new EntityMetadata(false, "", new FieldInfo[0]);
        }

        Annotation annotation = entityType.getAnnotation(AppTable.class);
        AppTable appTableAnnotation = (AppTable) annotation;
        String name = appTableAnnotation.name();

        Field[] declaredFields = entityType.getDeclaredFields();
        if(declaredFields.length == 0){
            return new EntityMetadata(true, name, new FieldInfo[0]);
        }

        FieldInfo[] collect = Arrays.stream(declaredFields)
                .map(x -> new FieldInfo(x.getAnnotation(AppColumn.class), x))
                .filter(x->x.getAppColumn() != null)
                .toArray(FieldInfo[]::new );

        return new EntityMetadata(true, name, collect);
    }



    private static void checkEntityMetadata(EntityMetadata entityMetadata) {
        if(!entityMetadata.isAppTableAnnotation()){
            throw new IllegalArgumentException("Invalid class");
        }

        if(entityMetadata.getDeclaredFields().length == 0){
            throw new IllegalArgumentException("Invalid class. Not found declared fields.");
        }
    }

    private static String getDbType(Class cl){
        String res = null;
        if (int.class.equals(cl)) {
            return "integer";
        } else if (String.class.equals(cl)) {
            return  "text";
        }

        throw new IllegalArgumentException("illegal argument");
    }
}
