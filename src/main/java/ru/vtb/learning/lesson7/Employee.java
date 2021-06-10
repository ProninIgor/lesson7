package ru.vtb.learning.lesson7;

@AppTable(name = "Employees")
public class Employee {

    @AppColumn(name = "Id")
    public int id;

    @AppColumn(name = "name")
    public String name;
}
