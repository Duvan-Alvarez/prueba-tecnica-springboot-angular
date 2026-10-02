package com.makers.loans.dto;
import com.makers.loans.entity.*; import java.math.BigDecimal; import java.time.LocalDateTime;
public record LoanResponse(Long id,String userEmail,BigDecimal amount,Integer termMonths,LoanStatus status,LocalDateTime createdAt,LocalDateTime updatedAt) { public static LoanResponse from(Loan l){return new LoanResponse(l.getId(),l.getUser().getEmail(),l.getAmount(),l.getTermMonths(),l.getStatus(),l.getCreatedAt(),l.getUpdatedAt());}}
