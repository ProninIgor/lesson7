package ru.vtb.learning.lesson7;

public class MainApp {
    public static void main(String[] args) {

        String url = "jdbc:sqlite:javadb.db";
        Dal dal = new Dal(url);

        Employee employee = new Employee();
        employee.name = "Bob";
        employee.id = 1;

        dal.createTable(employee.getClass());
        dal.save(employee);

    }
}
