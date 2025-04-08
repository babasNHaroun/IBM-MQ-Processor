package com.mq.manager.mqManager.service;

import com.mq.manager.mqManager.Utils.enums.ProcessedFlowType;
import com.mq.manager.mqManager.Utils.enums.Direction;
import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.repository.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
public class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private PartnerService partnerService;

    private Partner partner;

    @BeforeEach
    void setUp() {
        partner = new Partner();
        partner.setId(1L);
        partner.setAlias("Partner1");
        partner.setType("Type1");
        partner.setDirection(Direction.INBOUND);
        partner.setProcessedFlowType(ProcessedFlowType.MESSAGE);
        partner.setDescription("Description1");
    }

    @Test
    public void testSavePartner() {
        when(partnerRepository.save(any(Partner.class))).thenReturn(partner);

        Partner savedPartner = partnerService.savePartner(partner);

        assertNotNull(savedPartner);
        assertEquals("Partner1", savedPartner.getAlias());
        assertEquals(Direction.INBOUND, savedPartner.getDirection());
        assertEquals(ProcessedFlowType.MESSAGE, savedPartner.getProcessedFlowType());
        assertEquals("Description1", savedPartner.getDescription());
    }

    @Test
    public void testUpdatePartner() {
        Partner updatedPartner = new Partner();
        updatedPartner.setId(1L);
        updatedPartner.setAlias("Partner1_1");
        updatedPartner.setType("Type1_1");
        updatedPartner.setDirection(Direction.OUTBOUND);
        updatedPartner.setProcessedFlowType(ProcessedFlowType.ALERTING);
        updatedPartner.setDescription("Description1_1");

        when(partnerRepository.findById(1L)).thenReturn(java.util.Optional.of(partner));
        when(partnerRepository.save(any(Partner.class))).thenReturn(updatedPartner);

        Partner result = partnerService.updatePartner(1L, updatedPartner);

        assertEquals("Partner1_1", result.getAlias());
        assertEquals("Type1_1", result.getType());
        assertEquals(Direction.OUTBOUND, result.getDirection());
        assertEquals(ProcessedFlowType.ALERTING, result.getProcessedFlowType());
        assertEquals("Description1_1", result.getDescription());
    }
}