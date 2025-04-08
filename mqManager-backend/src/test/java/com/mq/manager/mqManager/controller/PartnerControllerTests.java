package com.mq.manager.mqManager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mq.manager.mqManager.Utils.enums.Direction;
import com.mq.manager.mqManager.Utils.enums.ProcessedFlowType;
import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.service.PartnerService;
import com.mq.manager.mqManager.service.dto.PartnerDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PartnerController.class)
public class PartnerControllerTests {

    @MockBean
    private PartnerService partnerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void savePartner_shouldAddPartner() throws Exception {
        Partner partner = new Partner();
        partner.setAlias("Partner1");
        partner.setType("Type1");
        partner.setDirection(Direction.valueOf("INBOUND"));

        when(partnerService.savePartner(any(Partner.class))).thenReturn(partner);

        mockMvc.perform(post("/api/partners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partner)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.alias").value("Partner1"))
                .andExpect(jsonPath("$.type").value("Type1"))
                .andExpect(jsonPath("$.direction").value("INBOUND"))
                .andDo(print());
    }

    @Test
    void getAllPartners_shouldGetAllPartners() throws Exception {
        PartnerDTO partner = new PartnerDTO();
        partner.setAlias("Partner1");
        Page<PartnerDTO> page = new PageImpl<>(Collections.singletonList(partner), PageRequest.of(0, 10), 1);

        when(partnerService.getAllPartners(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/partners")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].alias").value("Partner1"))
                .andDo(print());
    }

    @Test
    void updatedPartner_shouldUpdatePartner() throws Exception {
        Partner updatedPartner = new Partner();
        updatedPartner.setAlias("Partner1Updated");
        updatedPartner.setType("TypeUpdated");
        updatedPartner.setDirection(Direction.valueOf("OUTBOUND"));
        updatedPartner.setApplication("APP_1Updated");
        updatedPartner.setProcessedFlowType(ProcessedFlowType.valueOf("ALERTING"));
        updatedPartner.setDescription("Description1Updated");

        when(partnerService.updatePartner(eq(1L), any(Partner.class))).thenReturn(updatedPartner);

        mockMvc.perform(put("/api/partners/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPartner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("Partner1Updated"))
                .andExpect(jsonPath("$.direction").value("OUTBOUND"))
                .andDo(print());
    }

    @Test
    void deletePartner_shouldDeletePartner() throws Exception {
        doNothing().when(partnerService).deletePartner(1L);

        mockMvc.perform(delete("/api/partners/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}