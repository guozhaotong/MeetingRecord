package com.guozhaotong.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guozhaotong.entity.*;
import com.guozhaotong.repository.AttendanceRepository;
import com.guozhaotong.repository.NonAttendanceRepository;
import com.guozhaotong.repository.RecordRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    protected static Logger logger = LoggerFactory.getLogger(RecordController.class);


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    NonAttendanceRepository nonAttendanceRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Record addRecord(Date date, String time, String place, String recorder, String content, boolean verification, String attachment, String attList, String nonAttList) {
        StringBuffer attRes = new StringBuffer();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(attachment);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    attRes.append(jsonObject.get("filePath") + ":");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Record record = new Record(date, time, place, recorder, content, verification, attRes.toString());
        record = recordRepository.save(record);
        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> attendanceList = jsonToJavaBean(attList, type);
        for (Person att : attendanceList) {
            Attendance attendance = new Attendance(record.getId(), att.getName());
            attendance = attendanceRepository.save(attendance);
        }
        type = new TypeToken<ArrayList<NonTemp>>() {
        }.getType();
        ArrayList<NonTemp> nonAttendanceArrayList = jsonToJavaBean(nonAttList, type);
        for (NonTemp nonTemp : nonAttendanceArrayList) {
            NonAttendance nonAttendance = new NonAttendance(record.getId(), nonTemp.getPersonName(), nonTemp.getReason());
            nonAttendance = nonAttendanceRepository.save(nonAttendance);
        }
        return record;
    }


    public static <T> ArrayList<T> jsonToJavaBean(String json, Type type) {
        Gson gson = new Gson();
        ArrayList<Person> person = gson.fromJson(json, type);//对于javabean直接给出class实例
        return (ArrayList<T>) person;
    }


    @Transactional()
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Record updateRecord(long id, Date date, String time, String place, String recorder, String content, boolean verification, String attechment, String attList, String nonAttList) {
        Record record = recordRepository.findOne(id);
        record.setId(id);
        record.setDate(date);
        record.setTime(time);
        record.setPlace(place);
        record.setRecorder(recorder);
        record.setContent(content);
        record.setVerification(verification);
        record.setAttachment(attechment);
        record = recordRepository.save(record);
        attendanceRepository.deleteByRecordId(id);

        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> attendanceList = jsonToJavaBean(attList, type);
        for (Person att : attendanceList) {
            Attendance attendance = new Attendance(record.getId(), att.getName());
            attendance = attendanceRepository.save(attendance);
        }
        nonAttendanceRepository.deleteByRecordID(id);
        type = new TypeToken<ArrayList<NonTemp>>() {
        }.getType();
        ArrayList<NonTemp> nonAttendanceArrayList = jsonToJavaBean(nonAttList, type);
        for (NonTemp nonTemp : nonAttendanceArrayList) {
            NonAttendance nonAttendance = new NonAttendance(record.getId(), nonTemp.getPersonName(), nonTemp.getReason());
            nonAttendance = nonAttendanceRepository.save(nonAttendance);
        }
        return record;
    }

    /***
     * 列出所有的会议记录信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Record> listRecords() {
        List<Record> recordList = recordRepository.findAll();
        return recordList;
    }

    /***
     * 列出一次会议记录信息
     * @return
     */
    @RequestMapping(value = "/showOne", method = RequestMethod.GET)
    public FullRecord listPersons(long id) {
        Record record = recordRepository.findOne(id);
        record.setReadNum(record.getReadNum() + 1);
        List<Person> attendanceList = attendanceRepository.getAttenPersons(id);
        List<NonAttendance> nonAttendanceList = nonAttendanceRepository.findByRecordID(id);
        String attRes = record.getAttachment();
        ArrayList<HashMap<String, String>> atts = new ArrayList<>();
        if (attRes != null) {

            String[] att = attRes.split(":");
            String usrHome = System.getProperty("user.home");
            String path_head = usrHome + "\\会议记录附件\\";
            for (int i = 0; i < att.length; i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put("pathHead", path_head);
                map.put("timeStamp", att[i].substring(0, 23));
                map.put("fileName", att[i].substring(23));
                atts.add(map);
            }
        }
        FullRecord fullRecord = new FullRecord(record.getDate().toString().split(" ")[0], record.getTime(), record.getPlace(), record.getRecorder(), record.getContent(), record.isVerification(), atts, record.getReadNum(), attendanceList, nonAttendanceList);
        record = recordRepository.save(record);
        return fullRecord;
    }


    /***
     * 列出一个人参与的所有会议
     * @param personName
     * @return
     */
    @RequestMapping(value = "/listPersonMeetings", method = RequestMethod.GET)
    public List<Record> listPersonMeetings(String personName) {
        List<Attendance> attendanceList = attendanceRepository.findByPersonName(personName);
        List<Record> recordList = new ArrayList<>();
        attendanceList.forEach(everyMeeting -> recordList.add(recordRepository.findOne(everyMeeting.getRecordId())));
        return recordList;
    }


    @RequestMapping(value = "/addAttach", method = RequestMethod.POST)
    public HashMap<String, String> addAttach(MultipartFile attachment) {
        String usrHome = System.getProperty("user.home");
        File user_path = new File(usrHome + "\\会议记录附件\\");
        if (!new File(usrHome + "\\会议记录附件\\").exists()) {
            user_path.mkdirs();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");
        String path_head = user_path + "\\";
        String path_body = format.format(new Date()) + attachment.getOriginalFilename();
        FileOutputStream f;
        try {
            f = new FileOutputStream(path_head + path_body);
            IOUtils.write(attachment.getBytes(), f);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> result = new HashMap<>();
        result.put("fileName", attachment.getOriginalFilename());
        result.put("filePath", path_body);

        return result;
    }

    @RequestMapping(value = "/deleteAttach", method = RequestMethod.POST)
    public HashMap<String, Object> deleteAttach(String fileName) {
        String usrHome = System.getProperty("user.home");
        String path_head = usrHome + "\\会议记录附件\\" + "\\";
        File file = new File(path_head + fileName);
        System.out.println(path_head + fileName);
        HashMap<String, Object> suc = new HashMap<>();
        if (file.delete()) {
            suc.put("code", 200);
            suc.put("message", "");
            return suc;
        }
        suc.put("code", 404);
        suc.put("message", "没删掉");
        return suc;
    }


    @RequestMapping(value = "/downloadAtt", method = {RequestMethod.GET})
    public ResponseEntity downloadAtt(String fileName) {
        ResponseEntity responseEntity = null;
        String usrHome = System.getProperty("user.home");
        String filePath = usrHome + "\\会议记录附件\\";
        File file = new File(filePath + "\\" + fileName);
        if (file.exists()) { //判断文件父目录是否存在
            Object att = null;
            try {
                att = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                responseEntity = ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName.substring(23), "UTF-8"))
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(att);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;

    }
}
