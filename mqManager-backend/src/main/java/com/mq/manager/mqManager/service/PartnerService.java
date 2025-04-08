package com.mq.manager.mqManager.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.Utils.exceptions.ResourceNotFoundException;
import com.mq.manager.mqManager.Utils.helpers.Mapper;
import com.mq.manager.mqManager.repository.PartnerRepository;
import com.mq.manager.mqManager.service.dto.PartnerDTO;
import com.mq.manager.mqManager.service.interfaces.PartnerInterface;

@Service
public class PartnerService implements PartnerInterface {

    private PartnerRepository partnerRepository;
    private final Mapper mapper;

    public PartnerService(PartnerRepository partnerRepository, Mapper mapper) {
        this.partnerRepository = partnerRepository;
        this.mapper = mapper;
    }

    public Page<PartnerDTO> getAllPartners(Pageable pageable) {
        return partnerRepository.findAll(pageable).map(mapper::convertPartnerToDTO);
    }

    public void deletePartner(Long id) {
        // use CrudRepository's existsById method
        if (!partnerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Partner not found with id: " + id);
        }
        partnerRepository.deleteById(id);
    }

    public Partner savePartner(Partner partner) {
        validatePartner(partner);
        return partnerRepository.save(partner);
    }

    public Partner updatePartner(Long id, Partner partnerDetails) {
        Partner partner = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id: " + id));

        partner.setAlias(partnerDetails.getAlias());
        partner.setType(partnerDetails.getType());
        partner.setDirection(partnerDetails.getDirection());
        partner.setApplication(partnerDetails.getApplication());
        partner.setProcessedFlowType(partnerDetails.getProcessedFlowType());
        partner.setDescription(partnerDetails.getDescription());

        validatePartner(partner);
        return partnerRepository.save(partner);
    }

    public Optional<Partner> findById(Long id) {
        return partnerRepository.findById(id);
    }

    public Partner getPartnerById(Long id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id: " + id));
    }

    private void validatePartner(Partner partner) {
        if (partner.getAlias() == null || partner.getAlias().trim().isEmpty()) {
            throw new IllegalArgumentException("Partner's alias is required");
        }

        if (partner.getType() == null || partner.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Partner's type is required");
        }

        if (partner.getDirection() == null) {
            throw new IllegalArgumentException("Partner's direction is required");
        }

        if (partner.getProcessedFlowType() == null) {
            throw new IllegalArgumentException("Partner's processed flow type is required");
        }

        if (partner.getDescription() == null || partner.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Partner's description is required");
        }
    }
}