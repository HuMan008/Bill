package cn.gotoil.bill.web.controller;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@Authentication(authenticationType = AuthenticationType.None)
public class HomeController {

    @RequestMapping(value = "/home")
    public Object homeAction() {
        return new Date();
    }
}
