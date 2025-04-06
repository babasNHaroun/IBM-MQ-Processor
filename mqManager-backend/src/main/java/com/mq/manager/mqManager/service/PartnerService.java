package com.mq.manager.mqManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.repository.PartnerRepository;

@Service
public class PartnerService {

    private PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }

    public void delete(Long id) {
        partnerRepository.deleteById(id);
    }

    public Partner save(Partner partner) {
        return partnerRepository.save(partner);
    }

    public Optional<Partner> findById(Long id) {
        return partnerRepository.findById(id);
    }

}
