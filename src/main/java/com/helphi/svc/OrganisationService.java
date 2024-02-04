package com.helphi.svc;

import com.helphi.api.Address;
import com.helphi.api.organisation.Organisation;
import com.helphi.repository.OrganisationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;
    private final AddressService addressService;

    public OrganisationService(OrganisationRepository organisationRepository, AddressService addressService) {
        this.organisationRepository = organisationRepository;
        this.addressService = addressService;
    }

    public Optional<Organisation> getOrganisation(UUID organisationId) {
        return this.organisationRepository.findById(organisationId);
    }

    public Organisation createOrganisation(Organisation organisation) throws IllegalArgumentException {
        organisation.setId(null);
        return validateAndSave(organisation);
    }


    public Organisation updateOrganisation(Organisation organisation) throws IllegalArgumentException, EntityNotFoundException {
        return validateAndSave(organisation);
    }

    public void deleteOrganisation(UUID organisationId) throws IllegalArgumentException {
        this.organisationRepository.deleteById(organisationId);
    }

    private Organisation validateAndSave(Organisation organisation) {
        String postcode = organisation.getContactAddress().getPostcode();
        organisation.getContactAddress().setPostcode(postcode.replaceAll("\\s",""));

        Optional<Address> address = this.addressService.getAddressByValues(organisation.getContactAddress());
        address.ifPresent(organisation::setContactAddress);

        return this.organisationRepository.save(organisation);
    }
}
