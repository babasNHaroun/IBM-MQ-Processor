package com.mq.manager.mqManager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.service.PartnerService;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public ResponseEntity<List<Partner>> getAllPartners() {
        List<Partner> partners = partnerService.findAll();
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerService.getById(id);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) {
        Partner savedPartner = partnerService.save(partner);
        return new ResponseEntity<>(savedPartner, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partnerDetails) {
        Partner updatedPartner = partnerService.update(id, partnerDetails);
        return new ResponseEntity<>(updatedPartner, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}