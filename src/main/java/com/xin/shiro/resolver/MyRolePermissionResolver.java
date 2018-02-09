package com.xin.shiro.resolver;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xuexin
 * @date 2018/2/9
 */
public class MyRolePermissionResolver implements RolePermissionResolver {

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("boss".equals(roleString)){
            //如果角色为"boss"则拥有WildcardPermission的所有权限
            List<Permission> permissionList = new ArrayList<Permission>();
            permissionList.add(new WildcardPermission("*"));
            return permissionList;
        }
        return null;
    }
}
