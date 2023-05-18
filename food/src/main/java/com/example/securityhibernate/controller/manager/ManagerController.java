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

    // Get infor page manager
    @GetMapping("/getInforPageManager/{tokenByUser}")
    public ResponseEntity<?> getInforPageManager(@PathVariable String tokenByUser) {
        List<ResponseData> list = new ArrayList<>();
        if (tokenByUser != null) {
            // Set Infor Page Manager
            list.add(new ResponseData(managerService.getInforDashboardManager(jwtUtilsHelpers.getIdUserByToken(tokenByUser)),
                    "Lấy thành công thông tin infor dashboard"));

            // Set Infor User
            list.add(new ResponseData(loginService.getFullNameByToken(tokenByUser),
                    "Lấy thành công infor user"));

            // Set All Orders
            list.add(new ResponseData(managerService.getAllOrder(tokenByUser),
                    "Lấy thành công all orders"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            list.add(new ResponseData("Lấy thất bại thông tin Page manager"));
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }

    }

}
