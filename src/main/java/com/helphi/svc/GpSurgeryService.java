package com.helphi.svc;

import com.helphi.api.Gp;
import com.helphi.api.GpSurgery;
import com.helphi.exception.NotFoundException;
import com.helphi.repository.GpSurgeryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GpSurgeryService {

    private final GpSurgeryRepository gpSurgeryRepository;

    @Autowired
    public GpSurgeryService(GpSurgeryRepository gpSurgeryRepository) {
        this.gpSurgeryRepository = gpSurgeryRepository;
    }

    public Optional<GpSurgery> getGpSurgery(UUID gpSurgeryId) {
        return this.gpSurgeryRepository.findById(gpSurgeryId);
    }

    public GpSurgery createGpSurgery(GpSurgery gpSurgery) throws IllegalArgumentException {
        return this.gpSurgeryRepository.save(gpSurgery);
    }

    public GpSurgery updateGpSurgery(GpSurgery gpSurgery) throws IllegalArgumentException {
        return this.gpSurgeryRepository.findById(gpSurgery.getId())
                .map(existingGp -> this.gpSurgeryRepository.save(gpSurgery))
                .orElseThrow(() -> new NotFoundException(String.format("Gp with id %s cannot be found", gpSurgery.getId().toString())));
    }

    public void deleteGpSurgery(UUID gpSurgeryId) {
        try {
            this.gpSurgeryRepository.deleteById(gpSurgeryId);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(String.format("Gp with id %s cannot be found", gpSurgeryId.toString()));
        }
    }

    public List<GpSurgery> getSurgeriesByName(String surgeryName) {
        if(surgeryName == null) {
            return this.gpSurgeryRepository.findAll();
        }
        return this.gpSurgeryRepository.findByName(surgeryName.toLowerCase());
    }
}
