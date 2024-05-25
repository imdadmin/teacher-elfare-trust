package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
}
