package com.guozhaotong.controller;

import com.guozhaotong.entity.NonAttCount;
import com.guozhaotong.entity.NonAttendance;
import com.guozhaotong.entity.Person;
import com.guozhaotong.repository.NonAttendanceRepository;
import com.guozhaotong.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
@RestController
@RequestMapping("/nonAttendance")
public class NonAttendanceController {
    @Autowired
    NonAttendanceRepository nonAttendanceRepository;
    @Autowired
    PersonRepository personRepository;

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
        List<NonAttendance> nonAttendanceList = nonAttendanceRepository.findAll();
        return nonAttendanceList;
    }

    /***
     * 降序列出请过假的每个人及请假次数
     * @return
     */
    @RequestMapping(value = "/listNon", method = RequestMethod.GET)
    public Map<String, Object> listNonAttPersons(long id) {
        List<Person> personList = personRepository.findAll();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        Date dateSet = null;
        if (dateNow.getMonth() < 9 && dateNow.getMonth() >= 2) {
            try {
                dateSet = format.parse((dateNow.getYear() + 1900) + "-02-10 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (dateNow.getMonth() != 1) {
            try {
                dateSet = format.parse((dateNow.getYear() + 1900) + "-09-01 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                dateSet = format.parse((dateNow.getYear() + 1900 - 1) + "-09-01 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<NonAttendance> nonAttendanceList = nonAttendanceRepository.findBeforeRecordID(id, dateSet);
        List<NonAttCount> nonAttCounts = new ArrayList<>();
        for (Person person : personList) {
            int num = 0;
            for (NonAttendance nonAttendance : nonAttendanceList) {
                if (nonAttendance.getPersonName().equals(person.getName())) {
                    num++;
                }
            }
            if (num != 0) {
                nonAttCounts.add(new NonAttCount(person.getName(), num));
            }
        }
        Collections.sort(nonAttCounts, (no1, no2) -> {
            return no2.getNumber() - no1.getNumber();
        });
        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        nonAttCounts.forEach(no -> {
            names.add(no.getName());
            nums.add(no.getNumber());
        });
        HashMap<String, Object> result = new HashMap<>();
        result.put("names", names);
        result.put("nums", nums);
        result.put("all", nonAttCounts);
        return result;
    }
}
