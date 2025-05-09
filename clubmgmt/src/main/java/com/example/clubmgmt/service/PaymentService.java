package com.example.clubmgmt.service;

import com.example.clubmgmt.dto.PaymentRecordDTO;
import com.example.clubmgmt.entity.PaymentRecord;
import com.example.clubmgmt.entity.Subscription;
import com.example.clubmgmt.repository.PaymentRecordRepository;
import com.example.clubmgmt.repository.SubscriptionRepository;
import com.example.clubmgmt.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;
    
    @Autowired
    private EntityDtoConverter converter;

    public List<PaymentRecordDTO> getPaymentsByClub(Long clubId) {
        return converter.convertToPaymentRecordDtoList(
            paymentRecordRepository.findBySubscriptionClubId(clubId)
        );
    }

    public List<PaymentRecordDTO> getPaymentsByMember(Long memberId) {
        return converter.convertToPaymentRecordDtoList(
            paymentRecordRepository.findBySubscriptionMemberId(memberId)
        );
    }

    public List<PaymentRecordDTO> getUnpaidRecords() {
        return converter.convertToPaymentRecordDtoList(
            paymentRecordRepository.findByPaidFalse()
        );
    }

    @Transactional
    public PaymentRecordDTO markAsPaid(Long paymentRecordId) {
        PaymentRecord record = paymentRecordRepository.findById(paymentRecordId)
                .orElseThrow(() -> new RuntimeException("Registro de pago no encontrado"));
        record.setPaid(true);
        PaymentRecord savedRecord = paymentRecordRepository.save(record);
        
        generateNextPaymentRecord(savedRecord);
        
        return converter.convertToDto(savedRecord);
    }
    
    @Transactional
    public PaymentRecordDTO createPaymentRecord(PaymentRecord paymentRecord) {
        return converter.convertToDto(paymentRecordRepository.save(paymentRecord));
    }
    
    private void generateNextPaymentRecord(PaymentRecord currentPayment) {
        Subscription subscription = currentPayment.getSubscription();
        
        if (subscription == null) {
            return;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentPayment.getPaymentMonth());
        
        switch (subscription.getPaymentPeriod()) {
            case WEEKLY:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case BIWEEKLY:
                calendar.add(Calendar.WEEK_OF_YEAR, 2);
                break;
            case MONTHLY:
            default:
                calendar.add(Calendar.MONTH, 1);
                break;
        }
        
        PaymentRecord nextPayment = new PaymentRecord();
        nextPayment.setSubscription(subscription);
        nextPayment.setPaymentMonth(calendar.getTime());
        nextPayment.setPaid(false);
        
        paymentRecordRepository.save(nextPayment);
    }
}