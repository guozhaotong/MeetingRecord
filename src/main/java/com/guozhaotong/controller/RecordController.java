package com.guozhaotong.controller;

import com.guozhaotong.entity.Attendance;
import com.guozhaotong.entity.Record;
import com.guozhaotong.repository.AttendanceRepository;
import com.guozhaotong.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    protected static Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    RecordRepository recordRepository;
    AttendanceRepository attendanceRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Record addRecord(Date time, String place, String recorder, String content, boolean verification) {
        Record record = new Record(time, place, recorder, content, verification);
        record = recordRepository.save(record);
        return record;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Record updateRecord(long id, Date time, String place, String recorder, String content, boolean verification) {
        Record record = recordRepository.findOne(id);
        if (record == null) {
            logger.info("更新未存在的人员数据");
            return null;
        } else if (record.isVerification()) {
            logger.info("已经确认的记录无法更改");
            return null;
        } else {
            record.setId(id);
            record.setTime(time);
            record.setPlace(place);
            record.setRecorder(recorder);
            record.setContent(content);
            record.setVerification(verification);
            record = recordRepository.save(record);
            return record;
        }
    }

    /***
     * 列出所有的会议记录信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Record> listPersons() {
        List<Record> recordList = recordRepository.findAll();
        return recordList;
    }

    /***
     * 列出一次会议记录信息
     * @return
     */
    @RequestMapping(value = "/showOne", method = RequestMethod.GET)
    public Record listPersons(long id) {
        Record record = recordRepository.findOne(id);
        return record;
    }
//
//    /***
//     * 列出一次会议记录信息
//     * @return
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Record listPerso111111ns(@PathVariable("id") long id) {
//        Record record = recordRepository.findOne(id);
//        return record;
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    public Record listPerso1111222211ns(@PathVariable("id") long id) {
//        Record record = recordRepository.findOne(id);
//        return record;
//    }

    /***
     * 列出一个人参与的所有会议
     * @param personName
     * @return
     */
    @RequestMapping(value = "/listPersonMeetings", method = RequestMethod.GET)
    public List<Record> listPersonMeetings(String personName) {
        List<Attendance> attendanceList = attendanceRepository.findByPersonName(personName);
        List<Record> recordList = new ArrayList<>();
        attendanceList.forEach(everyMeeting -> recordList.add(recordRepository.findOne(everyMeeting.getRecordID())));
        return recordList;
    }
}
