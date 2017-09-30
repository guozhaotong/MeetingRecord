package com.guozhaotong.controller;

import com.guozhaotong.entity.Attendance;
import com.guozhaotong.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Attendance addPerson(long recordID, String personName) {
        Attendance attendance = new Attendance(recordID, personName);
        attendance = attendanceRepository.save(attendance);
        return attendance;
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

    /***
     * 列出参会表中所有的信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Attendance> listAttendances() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        return attendanceList;
    }


}
