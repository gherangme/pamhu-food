package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.LoginService;
import com.example.securityhibernate.service.ManagerService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/managers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerController {

    JwtUtilsHelpers jwtUtilsHelpers;
    ManagerService managerService;
    LoginService loginService;

    @GetMapping("/{tokenByUser}")
    public ResponseEntity<?> getInformationManager(@PathVariable String tokenByUser) {
        List<ResponseData> list = new ArrayList<>();
        if (tokenByUser != null) {
            list.add(new ResponseData(managerService.getDashboardManager(jwtUtilsHelpers.getIdUserByToken(tokenByUser)),
                    "Lấy thành công thông tin infor dashboard"));

            list.add(new ResponseData(loginService.getFullNameByToken(tokenByUser),
                    "Lấy thành công infor user"));

            list.add(new ResponseData(managerService.getAllOrders(tokenByUser),
                    "Lấy thành công all orders"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            list.add(new ResponseData("Lấy thất bại thông tin Page manager"));
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }

    }

}
