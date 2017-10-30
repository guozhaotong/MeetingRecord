package com.guozhaotong.controller;

import com.guozhaotong.entity.Attendance;
import com.guozhaotong.entity.Person;
import com.guozhaotong.repository.AttendanceRepository;
import com.guozhaotong.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/person")
public class PersonController {

    protected static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonRepository personRepository;
    AttendanceRepository attendanceRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Person addPerson(String name, String grade) {
        Person person = new Person(name, grade);
        person = personRepository.save(person);
        return person;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean deletePerson(long id) {
        Person person = personRepository.findOne(id);
        if (person == null) {
            logger.info("删除未存在的人员数据");
            return false;
        } else {
            personRepository.delete(id);
            return true;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Person updatePerson(long id, String name, String grade) {
        Person person = personRepository.findOne(id);
        if (person == null) {
            logger.info("更新未存在的人员数据");
            return null;
        } else {
            person = new Person();
            person.setID(id);
            person.setName(name);
            person.setGrade(grade);
            person = personRepository.save(person);
            return person;
        }
    }

    /***
     * 列出所有的人员信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Person> listPersons() {
        List<Person> personList = personRepository.findAll();
        return personList;
    }


    /***
     * 展示一个人的信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/showOne", method = RequestMethod.GET)
    public Person showPerson(long id) {
        Person person = personRepository.findOne(id);
        return person;

    }

    /***
     * 展示某一个年级的所有人的信息
     * @param grade
     * @return
     */
    @RequestMapping(value = "/listOneGradePersons", method = RequestMethod.GET)
    public List<Person> listOneGradePersons(String grade) {
        List<Person> personList = personRepository.findByGrade(grade);
        return personList;
    }

    /***
     * 列出某一次会议的所有参与人
     * @return
     */
    @RequestMapping(value = "/listMeetingPersons", method = RequestMethod.GET)
    public List<Person> listMeetingPersons(Long recordID) {
        List<Attendance> attendanceList = attendanceRepository.findByRecordId(recordID);
        List<Person> personList = new ArrayList<>();
        attendanceList.forEach(everyAttendance -> personList.add(personRepository.findByName(everyAttendance.getPersonName())));
        return personList;
    }
}
