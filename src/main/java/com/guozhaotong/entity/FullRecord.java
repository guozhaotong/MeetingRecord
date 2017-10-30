package com.guozhaotong.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/10/17.
 */
public class FullRecord {
    private String date;
    private String time;
    private String place;
    private String recorder;
    private String content;
    private boolean verification;
    private ArrayList<HashMap<String, String>> attachment;
    private long readNum;
    private List<Person> attPersons;
    private List<NonAttendance> nonAttPersons;

    public FullRecord() {
    }

    public FullRecord(String date, String time, String place, String recorder, String content, boolean verification, ArrayList<HashMap<String, String>> attachment, long readNum, List<Person> attPersons, List<NonAttendance> nonAttPersons) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.recorder = recorder;
        this.content = content;
        this.verification = verification;
        this.attachment = attachment;
        this.attPersons = attPersons;
        this.nonAttPersons = nonAttPersons;
        this.readNum = readNum;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public ArrayList<HashMap<String, String>> getAttachment() {
        return attachment;
    }

    public void setAttachment(ArrayList<HashMap<String, String>> attachment) {
        this.attachment = attachment;
    }

    public List<Person> getAttPersons() {
        return attPersons;
    }

    public void setAttPersons(List<Person> attPersons) {
        this.attPersons = attPersons;
    }

    public List<NonAttendance> getNonAttPersons() {
        return nonAttPersons;
    }

    public void setNonAttPersons(List<NonAttendance> nonAttPersons) {
        this.nonAttPersons = nonAttPersons;
    }

    public long getReadNum() {
        return readNum;
    }

    public void setReadNum(long readNum) {
        this.readNum = readNum;
    }

    @Override
    public String toString() {
        return "FullRecord{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", recorder='" + recorder + '\'' +
                ", content='" + content + '\'' +
                ", verification=" + verification +
                ", attachment='" + attachment + '\'' +
                ", attPersons=" + attPersons +
                ", nonAttPersons=" + nonAttPersons +
                '}';
    }
}
