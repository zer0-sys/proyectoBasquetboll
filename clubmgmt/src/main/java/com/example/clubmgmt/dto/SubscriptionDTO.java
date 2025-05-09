package com.example.clubmgmt.dto;

import com.example.clubmgmt.entity.PaymentPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private Long id;
    private MemberSimpleDTO member;
    private ClubSimpleDTO club;
    private Date startDate;
    private Date endDate;
    private PaymentPeriod paymentPeriod;
}