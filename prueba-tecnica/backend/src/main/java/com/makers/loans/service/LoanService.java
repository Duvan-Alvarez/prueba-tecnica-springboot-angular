package com.makers.loans.service;

import com.makers.loans.dto.*; import com.makers.loans.entity.*; import com.makers.loans.repository.*; import org.springframework.cache.annotation.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.List;
@Service public class LoanService { private final LoanRepository loans; private final UserRepository users; public LoanService(LoanRepository loans,UserRepository users){this.loans=loans;this.users=users;}
 @Transactional public LoanResponse create(String email,LoanRequest r){User u=users.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado")); return LoanResponse.from(loans.save(new Loan(u,r.amount(),r.termMonths())));}
 @Cacheable(value="loansByUser",key="#email") @Transactional(readOnly=true) public List<LoanResponse> findMine(String email){User u=users.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado"));return loans.findByUserIdOrderByCreatedAtDesc(u.getId()).stream().map(LoanResponse::from).toList();}
 @Cacheable(value="allLoans") @Transactional(readOnly=true) public List<LoanResponse> findAll(){return loans.findAll().stream().map(LoanResponse::from).toList();}
 @CacheEvict(value={"allLoans","loansByUser"},allEntries=true) @Transactional public LoanResponse changeStatus(Long id,LoanStatus status){Loan l=loans.findById(id).orElseThrow(()->new IllegalArgumentException("Préstamo no encontrado")); if(l.getStatus()!=LoanStatus.PENDING) throw new IllegalStateException("Solo se puede cambiar un préstamo pendiente"); l.setStatus(status); return LoanResponse.from(loans.save(l));}
}
