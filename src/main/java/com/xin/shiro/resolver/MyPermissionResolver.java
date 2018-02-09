package com.xin.shiro.resolver;

import com.xin.shiro.permission.MyPermission;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @author xuexin
 * @date 2018/2/9
 */
public class MyPermissionResolver implements PermissionResolver {
    private final String MY_SIGN = "my.";
    @Override
    public Permission resolvePermission(String permissionString) {
        //如果权限字符串以"my."开头则使用自定义的权限类
        if (permissionString.startsWith(MY_SIGN)){
            return new MyPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
