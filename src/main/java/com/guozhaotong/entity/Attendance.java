package com.guozhaotong.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
@Entity
public class Attendance {
    @Id
    @GeneratedValue
    private long id;
    private long recordId;
    private String personName;

    public Attendance() {
    }

    public Attendance(long recordID, String personName) {
        this.recordId = recordID;
        this.personName = personName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", recordId=" + recordId +
                ", personName='" + personName + '\'' +
                '}';
    }
}
