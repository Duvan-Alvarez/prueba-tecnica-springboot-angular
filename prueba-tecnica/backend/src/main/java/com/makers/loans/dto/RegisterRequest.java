package com.makers.loans.dto;
import jakarta.validation.constraints.*; public record RegisterRequest(@NotBlank @Email String email,@NotBlank @Size(min=6,max=100) String password){}
