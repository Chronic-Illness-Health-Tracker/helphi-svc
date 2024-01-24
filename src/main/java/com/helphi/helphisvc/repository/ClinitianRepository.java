package com.helphi.helphisvc.repository;

import com.helphi.helphisvc.dto.ClinitianDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinitianRepository extends JpaRepository<ClinitianDTO, String> {
}
