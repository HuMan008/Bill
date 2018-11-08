package cn.gotoil.bill.web.services.impl;

import cn.gotoil.bill.config.property.SecureProperties;
import cn.gotoil.bill.model.BaseAdminUser;
import cn.gotoil.bill.web.services.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台用户实现类
 *
 * @author SuYajiang SYJ247@qq.com
 * @Date 2018-10-30 14:45
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    SecureProperties secureProperties;


    /**
     * 业务中 登录后实现这个方法 将权限放入到session中
     *
     * @param request
     * @param baseAdminUser
     */
    @Override
    public void afterLogin(HttpServletRequest request, BaseAdminUser baseAdminUser) {
        request.getSession().setAttribute(secureProperties.getSessionKey(), baseAdminUser);
    }
}
