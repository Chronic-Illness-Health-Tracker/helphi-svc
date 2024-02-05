package com.helphi.svc;

import com.helphi.api.HealthCondition;
import com.helphi.exception.ForeignKeyConstraintException;
import com.helphi.repository.HealthConditionRepository;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        HealthCondition addedCondition = null;

        try {
            addedCondition = this.conditionRepository.save(condition);
        } catch (DataIntegrityViolationException ex) {
            if(ex.getMessage().contains("\"health_condition\" violates foreign key constraint \"fk_organisation\"")){
                throw new ForeignKeyConstraintException("Invalid Organisation");
            } else {
                throw ex;
            }
        }
        return addedCondition;
    }

    public HealthCondition updateCondition(HealthCondition condition) {
        return this.conditionRepository.save(condition);
    }
    public void deleteCondition(UUID conditionId) {
        this.conditionRepository.deleteById(conditionId);
    }
}
