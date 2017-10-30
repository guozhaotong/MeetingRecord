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
    private Date date;
    private String time;
    private String place;
    private String recorder;
    @Lob
    private String content;
    private boolean verification;
    private String attachment;
    private long readNum;

    public Record(Date date, String time, String place, String recorder, String content, boolean verification, String attachment) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.recorder = recorder;
        this.content = content;
        this.verification = verification;
        this.attachment = attachment;
        this.readNum = 0;
    }

    public Record(Date date, String time, String place, String recorder, String content, boolean verification) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.recorder = recorder;
        this.content = content;
        this.verification = verification;
        this.readNum = 0;
    }

    public Record() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public long getReadNum() {
        return readNum;
    }

    public void setReadNum(long readNum) {
        this.readNum = readNum;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", recorder='" + recorder + '\'' +
                ", content='" + content + '\'' +
                ", verification=" + verification +
                ", attachment='" + attachment + '\'' +
                '}';
    }
}
