package com.mq.manager.mqManager.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mq.manager.mqManager.entity.Partner;
import com.mq.manager.mqManager.service.dto.PartnerDTO;

public interface PartnerInterface {

    Partner savePartner(Partner partner);

    Page<PartnerDTO> getAllPartners(Pageable pageable);

    Partner updatePartner(Long id, Partner updatedPartner);

    void deletePartner(Long id);

}
