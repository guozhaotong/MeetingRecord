package com.guozhaotong.repository;

import com.guozhaotong.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByRecordID(long recordID);

    List<Attendance> findByPersonName(String personName);
}
