package com.helphi.repository;

import com.helphi.api.HealthCondition;
import com.helphi.api.organisation.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HealthConditionRepository extends JpaRepository<HealthCondition, UUID> {

    @Query("SELECT p FROM HealthCondition p WHERE LOWER(p.name) LIKE %:healthConditionName%")
    List<HealthCondition> findByName(@Param("healthConditionName") String healthConditionName);
}
