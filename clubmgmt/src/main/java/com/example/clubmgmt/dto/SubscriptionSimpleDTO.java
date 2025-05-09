package com.example.clubmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionSimpleDTO {
    private Long id;
    private MemberSimpleDTO member;
    private ClubSimpleDTO club;
}