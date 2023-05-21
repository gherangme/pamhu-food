package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.InvoiceDTO;
import com.example.securityhibernate.dto.OrderDetailDTO;

import java.util.List;

public interface OrderService {

    List<OrderDetailDTO> getAllOrderByIdUser(int idUser);
    boolean saveOrder(String username, int idFood, int amount, double price);
    InvoiceDTO getInforInvoiceById(int id);

}
