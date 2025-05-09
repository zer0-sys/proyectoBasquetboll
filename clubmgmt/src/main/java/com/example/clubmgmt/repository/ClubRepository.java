package com.example.clubmgmt.repository;

import com.example.clubmgmt.entity.Club;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT c FROM Club c LEFT JOIN FETCH c.categories LEFT JOIN FETCH c.user")
    List<Club> findAllWithCategoriesAndUser();

    @Query("SELECT c FROM Club c WHERE c.user.id = :userId")
    List<Club> findByUserId(@Param("userId") Long userId);
}