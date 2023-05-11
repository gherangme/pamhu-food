package com.example.securityhibernate.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

    // Format date
    public String formatDate(Date oldDate) {
        String newDate = "";
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(oldDate.toString());
            newDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e) {
            System.out.println("An error occurred when change date format | " + e.getMessage());
            e.printStackTrace();
        }

        return newDate;
    }

}
