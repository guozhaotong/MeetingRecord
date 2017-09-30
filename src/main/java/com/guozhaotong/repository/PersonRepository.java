package com.guozhaotong.repository;

import com.guozhaotong.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/29.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByGrade(String grade);

    Person findByName(String name);
}
