package com.makers.loans.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record LoanRequest(@NotNull @DecimalMin("100000.00") @DecimalMax("100000000.00") BigDecimal amount,@NotNull @Min(1) @Max(84) Integer termMonths){}
