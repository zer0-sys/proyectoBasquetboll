package com.example.clubmgmt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subscription subscription;

    @Temporal(TemporalType.DATE)
    private Date paymentMonth; //Fecha del mes que corresponde al pago

    @Column(nullable = false)
    private boolean paid = false; //Estado de pago
}