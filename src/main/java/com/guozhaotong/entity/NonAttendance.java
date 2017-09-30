package com.guozhaotong.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
@Entity
public class NonAttendance {
    @Id
    @GeneratedValue
    private long id;
    private long recordID;
    private String personName;
    private String reason;

    public NonAttendance(long recordID, String personName, String reason) {
        this.recordID = recordID;
        this.personName = personName;
        this.reason = reason;
    }

    public NonAttendance() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecordID() {
        return recordID;
    }

    public void setRecordID(long recordID) {
        this.recordID = recordID;
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

    @Override
    public String toString() {
        return "NonAttendance{" +
                "id=" + id +
                ", recordID=" + recordID +
                ", personName='" + personName + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
