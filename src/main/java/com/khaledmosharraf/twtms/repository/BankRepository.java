package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,Long> {
}
