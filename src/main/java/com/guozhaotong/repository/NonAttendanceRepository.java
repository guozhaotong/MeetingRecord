package com.guozhaotong.repository;

import com.guozhaotong.entity.NonAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
public interface NonAttendanceRepository extends JpaRepository<NonAttendance, Long> {
    List<NonAttendance> findByRecordID(long recordID);

    @Query("select n " +
            "from NonAttendance n , Record r" +
            " where " +
            "r.id = n.recordID " +
            "and r.date <= (select re.date from Record re where re.id = ?1) " +
            "and r.date >= ?2")
    List<NonAttendance> findBeforeRecordID(long recordID, Date d);
//
//    @Query("delete from NonAttendance n where n.recordId=?1")
//    void deleteNonAttendanceByRecordId(long recordId);
//    void deleteByRecordID(long recordId);


    @Query(value = "delete from non_attendance where recordid=?1", nativeQuery = true)
    @Modifying
    void deleteByRecordID(long recordID);
}
