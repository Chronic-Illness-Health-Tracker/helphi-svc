package com.helphi.helphisvc.repository;

import com.helphi.helphisvc.dto.HealthConditionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthConditionRepository extends JpaRepository<HealthConditionDTO, String> {
}
