package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.ManagerDTO;
import com.example.securityhibernate.dto.OrderDetailDTO;

import java.util.List;

public interface ManagerService {

    ManagerDTO getInforDashboardManager(int idUser);
    List<OrderDetailDTO> getAllOrder(String token);

}
