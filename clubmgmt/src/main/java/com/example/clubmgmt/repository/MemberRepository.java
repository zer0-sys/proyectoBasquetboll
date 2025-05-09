package com.example.clubmgmt.repository;

import com.example.clubmgmt.entity.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.club.id = :clubId")
    List<Member> findByClubId(@Param("clubId") Long clubId);

    @Query("SELECT m FROM Member m WHERE m.category.id = :categoryId")
    List<Member> findByCategoryId(@Param("categoryId") Long categoryId);
}