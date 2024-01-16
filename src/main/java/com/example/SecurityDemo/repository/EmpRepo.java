package com.example.SecurityDemo.repository;

import com.example.SecurityDemo.entity.EmpModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepo extends JpaRepository<EmpModel, Integer> {
}
