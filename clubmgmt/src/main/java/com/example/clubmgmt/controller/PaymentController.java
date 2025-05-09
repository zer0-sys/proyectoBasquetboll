package com.example.clubmgmt.controller;

import com.example.clubmgmt.dto.PaymentRecordDTO;
import com.example.clubmgmt.entity.PaymentRecord;
import com.example.clubmgmt.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/club/{clubId}")
    public List<PaymentRecordDTO> getPaymentsByClub(@PathVariable Long clubId) {
        return paymentService.getPaymentsByClub(clubId);
    }

    @GetMapping("/member/{memberId}")
    public List<PaymentRecordDTO> getPaymentsByMember(@PathVariable Long memberId) {
        return paymentService.getPaymentsByMember(memberId);
    }

    @GetMapping("/unpaid")
    public List<PaymentRecordDTO> getUnpaidRecords() {
        return paymentService.getUnpaidRecords();
    }

    @PostMapping("/mark-as-paid/{id}")
    public ResponseEntity<?> markAsPaid(@PathVariable Long id) {
        try {
            PaymentRecordDTO updatedRecord = paymentService.markAsPaid(id);
            return ResponseEntity.ok(Map.of(
                "payment", updatedRecord,
                "message", "Pago marcado como completado y se ha generado automáticamente el siguiente cobro."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping
    public PaymentRecordDTO createPaymentRecord(@RequestBody PaymentRecord paymentRecord) {
        return paymentService.createPaymentRecord(paymentRecord);
    }
}