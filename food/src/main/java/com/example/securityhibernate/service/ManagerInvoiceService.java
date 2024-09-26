package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.response.InvoiceDTO;

public interface ManagerInvoiceService {

    InvoiceDTO getInvoiceById(int id);

}
