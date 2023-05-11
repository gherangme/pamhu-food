package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.ManagerDTO;
import com.example.securityhibernate.service.ManagerService;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImp implements ManagerService {



    @Override
    public ManagerDTO getInforDashboardManager(String username) {
        ManagerDTO managerDTO = new ManagerDTO();
        return null;
    }
}
