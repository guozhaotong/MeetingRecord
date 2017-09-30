package com.guozhaotong.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

/**
 * @author 郭朝彤
 * @date 2017/9/28.
 */
@Entity
public class Record {
    @Id
    @GeneratedValue
    private long id;
    private Date time;
    private String place;
    private String recorder;
    @Lob
    private String content;
    private boolean verification;

    public Record(Date time, String place, String recorder, String content, boolean verification) {
        this.time = time;
        this.place = place;
        this.recorder = recorder;
        this.content = content;
        this.verification = verification;
    }

    public Record() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVerification() {
        return verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", recorder='" + recorder + '\'' +
                ", content='" + content + '\'' +
                ", verification=" + verification +
                '}';
    }
}
