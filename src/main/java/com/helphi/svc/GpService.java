package com.helphi.svc;

import com.helphi.api.Gp;
import com.helphi.exception.NotFoundException;
import com.helphi.repository.GpRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GpService {

    private final GpRepository gpRepository;

    @Autowired
    public GpService(GpRepository gpRepository) {
        this.gpRepository = gpRepository;
    }

    public Optional<Gp> getGp(UUID gpId) {
        return this.gpRepository.findById(gpId);
    }

    public Gp createGp(Gp gp) throws IllegalArgumentException {
        return this.gpRepository.save(gp);
    }

    public Gp updateGp(Gp gp) throws IllegalArgumentException {
        return this.gpRepository.findById(gp.getId())
            .map(existingGp -> this.gpRepository.save(gp))
            .orElseThrow(() -> new NotFoundException(String.format("Gp with id %s cannot be found", gp.getId().toString())));
    }

    public void deleteGp(UUID gpId) {
        try {
            this.gpRepository.deleteById(gpId);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(String.format("Gp with id %s cannot be found", gpId.toString()));
        }
    }

    public List<Gp> getAllGpInSurgery(UUID surgeryId) {
        return this.gpRepository.findBySurgeryId(surgeryId);
    }
}
