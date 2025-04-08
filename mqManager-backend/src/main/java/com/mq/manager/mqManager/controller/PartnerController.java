package com.mq.manager.mqManager.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.service.PartnerService;
import com.mq.manager.mqManager.service.dto.PartnerDTO;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public ResponseEntity<Page<PartnerDTO>> getAllPartners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PartnerDTO> partners = partnerService.getAllPartners(pageable);
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerService.getPartnerById(id);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) {
        Partner savedPartner = partnerService.savePartner(partner);
        return new ResponseEntity<>(savedPartner, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partnerDetails) {
        Partner updatedPartner = partnerService.updatePartner(id, partnerDetails);
        return new ResponseEntity<>(updatedPartner, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}