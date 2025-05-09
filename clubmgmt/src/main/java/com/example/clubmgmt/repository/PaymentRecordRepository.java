package com.example.clubmgmt.repository;

import com.example.clubmgmt.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    List<PaymentRecord> findByPaidFalse(); //Buscar registros de no pagados

    @Query("SELECT p FROM PaymentRecord p WHERE p.subscription.club.id = :clubId")
    List<PaymentRecord> findBySubscriptionClubId(@Param("clubId") Long clubId);

    @Query("SELECT p FROM PaymentRecord p WHERE p.subscription.member.id = :memberId")
    List<PaymentRecord> findBySubscriptionMemberId(@Param("memberId") Long memberId);
}