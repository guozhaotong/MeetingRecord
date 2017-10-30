package com.guozhaotong.entity;

/**
 * @author 郭朝彤
 * @date 2017/10/20.
 */
public class NonAttCount {
    private String name;
    private int number;

    public NonAttCount(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public NonAttCount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "NonAttCount{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';


    }
}
