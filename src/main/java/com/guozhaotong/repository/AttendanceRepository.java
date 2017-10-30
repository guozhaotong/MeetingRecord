package com.guozhaotong.repository;

import com.guozhaotong.entity.Attendance;
import com.guozhaotong.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByRecordId(long recordID);

    List<Attendance> findByPersonName(String personName);

    @Query("select new Person(p.ID,p.name,p.grade) from Person p, Attendance a where  p.name=a.personName and a.recordId=?1")
    List<Person> getAttenPersons(long recordId);

    @Query(value = "delete from attendance where record_id=?1", nativeQuery = true)
    @Modifying
    void deleteByRecordId(long recordID);
}
