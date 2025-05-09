package com.example.clubmgmt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Club club;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private PaymentPeriod paymentPeriod = PaymentPeriod.MONTHLY; // Valor predeterminado

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<PaymentRecord> paymentRecords;
}