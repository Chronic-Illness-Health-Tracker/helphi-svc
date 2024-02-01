package com.helphi.svc;

import com.helphi.api.HealthCondition;
import com.helphi.repository.HealthConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HealthConditionService {

    private final HealthConditionRepository conditionRepository;

    @Autowired
    public HealthConditionService(HealthConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    public Optional<HealthCondition> getCondition(UUID conditionId) {
        return this.conditionRepository.findById(conditionId);
    }

    public HealthCondition createCondition(HealthCondition condition) {
        return this.conditionRepository.save(condition);
    }

    public HealthCondition updateCondition(HealthCondition condition) {
        return this.conditionRepository.save(condition);
    }
    public void deleteCondition(UUID conditionId) {
        this.conditionRepository.deleteById(conditionId);
    }
}
