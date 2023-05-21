package com.example.securityhibernate.mapper;

import com.example.securityhibernate.dto.StatusDTO;
import com.example.securityhibernate.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public StatusDTO convertEntityToDTO(Status status) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());
        return statusDTO;
    }

    public Status convertDTOToEntity(StatusDTO statusDTO) {
        Status status = new Status();
        status.setId(statusDTO.getId());
        status.setName(statusDTO.getName());
        return status;
    }

}
