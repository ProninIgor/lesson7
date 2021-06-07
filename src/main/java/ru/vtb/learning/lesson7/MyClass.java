package ru.vtb.learning.lesson7;

@AppTable(name = "MyClass")
public class MyClass {

    @AppColumn(name = "Id")
    public Integer id;

    @AppColumn(name = "name")
    public String name;

    public void createTable(){

    }

    public void save(MyClass myClass){

    }
}
