package cn.gotoil.bill.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 业务系统里的用户都继承这个类
 *
 * @author SuYajiang SYJ247@qq.com
 * @Date 2018-10-30 14:48
 */
public class BaseAdminUser {


    String roleStr;

    Set<String> roles = new HashSet<>();

    String permissionStr;

    Set<String> permissions = new HashSet<>();


    public String getRoleStr() {
        return roleStr;
    }

    public void setRoleStr(String roleStr) {
        this.roleStr = roleStr;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPermissionStr() {
        return permissionStr;
    }

    public void setPermissionStr(String permissionStr) {
        this.permissionStr = permissionStr;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
