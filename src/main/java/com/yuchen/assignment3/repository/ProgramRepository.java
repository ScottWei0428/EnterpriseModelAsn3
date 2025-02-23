package com.yuchen.assignment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yuchen.assignment3.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, String> {
}