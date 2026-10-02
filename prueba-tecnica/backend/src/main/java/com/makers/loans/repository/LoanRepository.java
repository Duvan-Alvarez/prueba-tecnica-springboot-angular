package com.makers.loans.repository;
import com.makers.loans.entity.*; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface LoanRepository extends JpaRepository<Loan,Long>{ List<Loan> findByUserIdOrderByCreatedAtDesc(Long userId); }
