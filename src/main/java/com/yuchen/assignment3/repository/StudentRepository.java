package com.yuchen.assignment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yuchen.assignment3.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	Student findByUserName(String userName);
}
