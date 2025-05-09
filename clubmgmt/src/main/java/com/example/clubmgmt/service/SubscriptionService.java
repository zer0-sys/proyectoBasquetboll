package com.example.clubmgmt.service;

import com.example.clubmgmt.dto.SubscriptionDTO;
import com.example.clubmgmt.entity.PaymentPeriod;
import com.example.clubmgmt.entity.PaymentRecord;
import com.example.clubmgmt.entity.Subscription;
import com.example.clubmgmt.repository.SubscriptionRepository;
import com.example.clubmgmt.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private EntityDtoConverter converter;

    public List<SubscriptionDTO> getAllSubscriptions() {
        return converter.convertToSubscriptionDtoList(subscriptionRepository.findAll());
    }

    public Optional<SubscriptionDTO> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).map(converter::convertToDto);
    }

    @Transactional
    public SubscriptionDTO createSubscription(Subscription subscription) {
        if (subscription.getPaymentPeriod() == null) {
            subscription.setPaymentPeriod(PaymentPeriod.MONTHLY);
        }
        
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setSubscription(savedSubscription);
        paymentRecord.setPaymentMonth(new Date());
        paymentRecord.setPaid(false);
        
        paymentService.createPaymentRecord(paymentRecord);
        
        return converter.convertToDto(savedSubscription);
    }

    public SubscriptionDTO updateSubscription(Long id, Subscription updatedSubscription) {
        return subscriptionRepository.findById(id).map(subscription -> {
            subscription.setMember(updatedSubscription.getMember());
            subscription.setClub(updatedSubscription.getClub());
            subscription.setStartDate(updatedSubscription.getStartDate());
            subscription.setEndDate(updatedSubscription.getEndDate());
            if (updatedSubscription.getPaymentPeriod() != null) {
                subscription.setPaymentPeriod(updatedSubscription.getPaymentPeriod());
            }
            return converter.convertToDto(subscriptionRepository.save(subscription));
        }).orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
    
    public Optional<Subscription> getSubscriptionEntityById(Long id) {
        return subscriptionRepository.findById(id);
    }
}