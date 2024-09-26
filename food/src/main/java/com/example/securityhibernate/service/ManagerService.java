package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.response.ManagerDTO;
import com.example.securityhibernate.dto.response.OrderDetailDTO;

import java.util.List;

public interface ManagerService {

    ManagerDTO getDashboardManager(int idUser);
    List<OrderDetailDTO> getAllOrders(String token);

}
