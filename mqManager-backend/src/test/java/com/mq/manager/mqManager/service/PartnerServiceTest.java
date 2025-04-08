package com.mq.manager.mqManager.service;

import com.mq.manager.mqManager.Utils.enums.ProcessedFlowType;
import com.mq.manager.mqManager.Utils.enums.Direction;
import com.mq.manager.mqManager.entity.Partner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach; 

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PartnerServiceTest {

    private Partner partner;

    @Autowired
    private PartnerService partnerService;

    @BeforeEach
    void setUp() {
        partner = new Partner();
        partner.setAlias("Partner1");
        partner.setType("Type1");
        partner.setDirection(Direction.INBOUND);
        partner.setProcessedFlowType(ProcessedFlowType.MESSAGE);
        partner.setDescription("Description1");
    }

    @Test
    public void testSavePartner() {

        Partner savedPartner = partnerService.savePartner(partner);

        assertNotNull(savedPartner);
        assertNotNull(savedPartner.getId());
        assertEquals("Partner1", savedPartner.getAlias());
        assertEquals(Direction.INBOUND, savedPartner.getDirection());
        assertEquals(ProcessedFlowType.MESSAGE, savedPartner.getProcessedFlowType());
        assertEquals("Description1", savedPartner.getDescription());
    }

    @Test
    public void tesUpdatedPartner() {

        Partner savedPartner = partnerService.savePartner(partner);
        savedPartner.setAlias("Parnter1_1");
        partner.setType("Type1_1");
        partner.setDirection(Direction.OUTBOUND);
        partner.setProcessedFlowType(ProcessedFlowType.ALERTING);
        partner.setDescription("Description1_1");

        Partner updatedPartner = partnerService.updatePartner(savedPartner.getId(), savedPartner);

        assertEquals("Parnter1_1", updatedPartner.getAlias());
        assertEquals("Type1_1", updatedPartner.getType());
        assertEquals(Direction.OUTBOUND, updatedPartner.getDirection());
        assertEquals(ProcessedFlowType.ALERTING, updatedPartner.getProcessedFlowType());
        assertEquals("Description1_1", updatedPartner.getDescription());

    }
}