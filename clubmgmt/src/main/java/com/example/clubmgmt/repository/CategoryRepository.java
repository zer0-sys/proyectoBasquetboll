package com.example.clubmgmt.repository;

import com.example.clubmgmt.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}