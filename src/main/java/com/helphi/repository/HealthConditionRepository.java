package com.helphi.repository;

import com.helphi.api.HealthCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthConditionRepository extends JpaRepository<HealthCondition, UUID> {
}
