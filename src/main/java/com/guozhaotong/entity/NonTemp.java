package com.guozhaotong.entity;

/**
 * @author 郭朝彤
 * @date 2017/10/20.
 */
public class NonTemp {
    private String name;
    private String grade;
    private long id;
    private String personName;
    private String reason;

    public NonTemp(String name, String grade, long id, String personName, String reason) {
        this.name = name;
        this.grade = grade;
        this.id = id;
        this.personName = personName;
        this.reason = reason;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
