package com.example.clubmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecordDTO {
    private Long id;
    private SubscriptionSimpleDTO subscription;
    private Date paymentMonth;
    private boolean paid;
}