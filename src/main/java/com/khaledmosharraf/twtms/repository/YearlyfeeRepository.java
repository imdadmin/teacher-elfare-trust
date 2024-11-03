package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Yearlyfee;
import com.khaledmosharraf.twtms.utils.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface YearlyfeeRepository extends JpaRepository<Yearlyfee,Long> {
//SELECT * FROM user_sequences WHERE sequence_name = 'YEARLYFEE_SEQUENCE';
    Yearlyfee findTopByYear(@Param("year") Integer year);

}
