package com.guozhaotong.repository;

import com.guozhaotong.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
public interface RecordRepository extends JpaRepository<Record, Long> {

}
