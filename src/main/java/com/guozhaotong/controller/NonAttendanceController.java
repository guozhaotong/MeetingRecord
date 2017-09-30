package com.guozhaotong.controller;

import com.guozhaotong.entity.NonAttendance;
import com.guozhaotong.repository.NonAttendanceRepository;
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
@RequestMapping("/nonAttendance")
public class NonAttendanceController {
    @Autowired
    NonAttendanceRepository nonAttendanceRepository;

    protected static Logger logger = LoggerFactory.getLogger(NonAttendanceController.class);

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public NonAttendance addPerson(long recordID, String personName, String reason) {
        NonAttendance nonAttendance = new NonAttendance(recordID, personName, reason);
        nonAttendance = nonAttendanceRepository.save(nonAttendance);
        return nonAttendance;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean deleteNonAttendance(long id) {
        NonAttendance nonAttendance = nonAttendanceRepository.findOne(id);
        if (nonAttendance == null) {
            logger.info("删除未存在的不出席人员数据");
            return false;
        } else {
            nonAttendanceRepository.delete(id);
            return true;
        }
    }

    /***
     * 列出不参会表中所有的信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<NonAttendance> listNonAttendances() {
        List<NonAttendance> attendanceList = nonAttendanceRepository.findAll();
        return attendanceList;
    }
}
