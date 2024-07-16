package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double getTotalExpenseAmount();
}
