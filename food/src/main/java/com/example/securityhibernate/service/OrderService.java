package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.response.InvoiceDTO;
import com.example.securityhibernate.dto.response.OrderDetailDTO;

import java.util.List;

public interface OrderService {

    List<OrderDetailDTO> getAllOrdersByIdUser(int idUser);
    boolean saveOrder(String username, int idFood, int amount, double price);
    InvoiceDTO getInvoiceById(int id);

}
