package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.LoginService;
import com.example.securityhibernate.service.ManagerService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private LoginService loginService;

    @GetMapping("")
    public ResponseEntity<?> manager() {
        return new ResponseEntity<>("Hello manager", HttpStatus.OK);
    }

    @GetMapping("/getInforPageManager/{tokenByUser}")
    public ResponseEntity<?> getInforPageManager(@PathVariable String tokenByUser) {
        List<ResponseData> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        ResponseData responseData1 = new ResponseData();
        ResponseData responseData2 = new ResponseData();
        if (tokenByUser != null) {

            // Set Infor Page Manager
            responseData.setData(managerService.getInforDashboardManager(jwtUtilsHelpers.
                    getIdUserByToken(tokenByUser)));
            list.add(responseData);

            // Set Infor User
            responseData1.setData(loginService.getFullNameByToken(tokenByUser));
            list.add(responseData1);

            // Set All Orders
            responseData2.setData(managerService.getAllOrder(tokenByUser));
            list.add(responseData2);
        } else {
            responseData.setDesc("Lấy thất bại thông tin Page manager");
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
