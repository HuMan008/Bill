package cn.gotoil.bill.web.services;

import cn.gotoil.bill.model.BaseAdminUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理用户功能抽象类
 *
 * @author SuYajiang SYJ247@qq.com
 * @Date 2018-10-30 14:22
 */

public interface AdminUserService<T extends BaseAdminUser> {

    /**
     * 业务中 登录后实现这个方法 将权限放入到session中
     *
     * @param request
     */
    void afterLogin(HttpServletRequest request, T baseAdminUser);


}
