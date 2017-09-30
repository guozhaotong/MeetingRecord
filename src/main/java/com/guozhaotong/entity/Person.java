package com.guozhaotong.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author 郭朝彤
 * @date 2017/9/28.
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private long ID;
    private String name;
    private String grade;

    public Person(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public Person() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
