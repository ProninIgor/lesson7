package ru.vtb.learning.lesson7;

public class MainApp {
    public static void main(String[] args) {

    }

    public static String createScript(Class entityType){
        if(entityType.isAnnotationPresent(AppTable.class)){
            throw new IllegalArgumentException("Invalid class");
        }

        return "";
    }
}
