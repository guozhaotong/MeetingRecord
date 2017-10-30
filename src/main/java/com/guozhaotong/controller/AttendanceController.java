package com.guozhaotong.controller;

import com.guozhaotong.entity.Attendance;
import com.guozhaotong.entity.Person;
import com.guozhaotong.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    AttendanceRepository attendanceRepository;

    protected static Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public List<Attendance> addPersons(List<Attendance> attendanceList) {
        for (Attendance att : attendanceList) {
            Attendance attendance = new Attendance(att.getRecordId(), att.getPersonName());
            attendance = attendanceRepository.save(attendance);
        }
        return attendanceList;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean deleteAttendance(long id) {
        Attendance attendance = attendanceRepository.findOne(id);
        if (attendance == null) {
            logger.info("删除未存在的出席人员数据");
            return false;
        } else {
            attendanceRepository.delete(id);
            return true;
        }
    }

    @Transactional()
    @RequestMapping(value = "/deleteByRecordId", method = RequestMethod.POST)
    public boolean deleteByRecordId(long id) {
        attendanceRepository.deleteByRecordId(id);
        return true;
    }
    /***
     * 列出参会表中所有的信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Attendance> listAttendances() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        return attendanceList;
    }


    /***
     * 列出参加某次会议的所有人
     * @return
     */
    @RequestMapping(value = "/listPerForOneMeeting", method = RequestMethod.GET)
    public List<String> listAttPersons(long recordID) {
        List<Attendance> attendanceList = attendanceRepository.findByRecordId(recordID);
        List<String> persons = new ArrayList<>();
        for (Attendance att : attendanceList) {
            persons.add(att.getPersonName());
        }
        return persons;
    }


    @RequestMapping(value = "/listPerForOneMeetingssssss", method = RequestMethod.GET)
    public List<Person> listAttPersonssssss(long id) {
        List<Person> attendanceList = attendanceRepository.getAttenPersons(id);
        return attendanceList;
    }
}
