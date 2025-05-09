package com.example.clubmgmt.util;

import com.example.clubmgmt.dto.*;
import com.example.clubmgmt.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {

    public UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    public CategoryDTO convertToDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public ClubDTO convertToDto(Club club) {
        if (club == null) {
            return null;
        }
        
        ClubDTO dto = new ClubDTO();
        dto.setId(club.getId());
        dto.setName(club.getName());
        dto.setDescription(club.getDescription());
        
        if (club.getCategories() != null) {
            Set<CategoryDTO> categoryDTOs = new HashSet<>();
            for (Category category : club.getCategories()) {
                if (category != null) {
                    // Evitar recursión infinita - no convertir los clubs de la categoría
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(category.getId());
                    categoryDTO.setName(category.getName());
                    categoryDTOs.add(categoryDTO);
                }
            }
            dto.setCategories(categoryDTOs);
        } else {
            dto.setCategories(new HashSet<>()); // Evitar NPE
        }
        
        if (club.getUser() != null) {
            dto.setUser(convertToDto(club.getUser()));
        }
        
        return dto;
    }

    public ClubSimpleDTO convertToSimpleDto(Club club) {
        ClubSimpleDTO dto = new ClubSimpleDTO();
        dto.setId(club.getId());
        dto.setName(club.getName());
        return dto;
    }

    public MemberDTO convertToDto(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setEmail(member.getEmail());
        dto.setJoinDate(member.getJoinDate());
        
        if (member.getClub() != null) {
            dto.setClub(convertToSimpleDto(member.getClub()));
        }
        
        if (member.getCategory() != null) {
            dto.setCategory(convertToDto(member.getCategory()));
        }
        
        return dto;
    }

    public MemberSimpleDTO convertToSimpleDto(Member member) {
        MemberSimpleDTO dto = new MemberSimpleDTO();
        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setEmail(member.getEmail());
        return dto;
    }

    public SubscriptionDTO convertToDto(Subscription subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(subscription.getId());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        
        if (subscription.getMember() != null) {
            dto.setMember(convertToSimpleDto(subscription.getMember()));
        }
        
        if (subscription.getClub() != null) {
            dto.setClub(convertToSimpleDto(subscription.getClub()));
        }
        
        return dto;
    }

    public SubscriptionSimpleDTO convertToSimpleDto(Subscription subscription) {
        SubscriptionSimpleDTO dto = new SubscriptionSimpleDTO();
        dto.setId(subscription.getId());
        
        if (subscription.getMember() != null) {
            dto.setMember(convertToSimpleDto(subscription.getMember()));
        }
        
        if (subscription.getClub() != null) {
            dto.setClub(convertToSimpleDto(subscription.getClub()));
        }
        
        return dto;
    }

    public PaymentRecordDTO convertToDto(PaymentRecord paymentRecord) {
        PaymentRecordDTO dto = new PaymentRecordDTO();
        dto.setId(paymentRecord.getId());
        dto.setPaymentMonth(paymentRecord.getPaymentMonth());
        dto.setPaid(paymentRecord.isPaid());
        
        if (paymentRecord.getSubscription() != null) {
            dto.setSubscription(convertToSimpleDto(paymentRecord.getSubscription()));
        }
        
        return dto;
    }

    public List<CategoryDTO> convertToCategoryDtoList(List<Category> categories) {
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<ClubDTO> convertToClubDtoList(List<Club> clubs) {
        if (clubs == null) {
            return new ArrayList<>();
        }
        
        return clubs.stream()
            .filter(Objects::nonNull)
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public List<MemberDTO> convertToMemberDtoList(List<Member> members) {
        return members.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<SubscriptionDTO> convertToSubscriptionDtoList(List<Subscription> subscriptions) {
        return subscriptions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<PaymentRecordDTO> convertToPaymentRecordDtoList(List<PaymentRecord> paymentRecords) {
        return paymentRecords.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}