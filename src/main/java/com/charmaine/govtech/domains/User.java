package com.charmaine.govtech.domains;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.opencsv.bean.CsvBindByName;

@Entity
public class User {

    @Id
    @CsvBindByName
    private String name;
    @CsvBindByName
    private double salary;

    public User() {
    }

    public User(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
