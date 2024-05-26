package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
