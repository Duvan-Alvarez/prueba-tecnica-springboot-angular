package com.makers.loans.dto;
import com.makers.loans.entity.LoanStatus; import jakarta.validation.constraints.NotNull; public record StatusRequest(@NotNull LoanStatus status){}
