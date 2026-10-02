package com.makers.loans.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="loans")
public class Loan {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false, fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
 @Column(nullable=false, precision=15, scale=2) private BigDecimal amount;
 @Column(nullable=false) private Integer termMonths;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private LoanStatus status;
 @Column(nullable=false) private LocalDateTime createdAt;
 private LocalDateTime updatedAt;
 public Loan() {}
 public Loan(User user,BigDecimal amount,Integer termMonths){this.user=user;this.amount=amount;this.termMonths=termMonths;this.status=LoanStatus.PENDING;this.createdAt=LocalDateTime.now();}
 @PreUpdate void beforeUpdate(){updatedAt=LocalDateTime.now();}
 public Long getId(){return id;} public User getUser(){return user;} public BigDecimal getAmount(){return amount;} public Integer getTermMonths(){return termMonths;} public LoanStatus getStatus(){return status;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
 public void setId(Long id){this.id=id;} public void setUser(User user){this.user=user;} public void setAmount(BigDecimal amount){this.amount=amount;} public void setTermMonths(Integer termMonths){this.termMonths=termMonths;} public void setStatus(LoanStatus status){this.status=status;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;} public void setUpdatedAt(LocalDateTime updatedAt){this.updatedAt=updatedAt;}
}
