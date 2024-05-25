package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit,Long> {
}
