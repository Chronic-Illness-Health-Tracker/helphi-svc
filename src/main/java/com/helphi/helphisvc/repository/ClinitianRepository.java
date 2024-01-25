package com.helphi.helphisvc.repository;

import com.helphi.api.user.Clinitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinitianRepository extends JpaRepository<Clinitian, String> {
}
