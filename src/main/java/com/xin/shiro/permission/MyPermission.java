package com.xin.shiro.permission;

import org.apache.shiro.authz.Permission;

/**
 * @author xuexin
 * @date 2018/2/9
 */
public class MyPermission implements Permission {

    private final String DEFAULT_SPLIT_SIGN = "\\.";

    private final String[] permissionArr;

    public MyPermission(String permissionString) {
        this.permissionArr = permissionString.split(DEFAULT_SPLIT_SIGN);
    }

    @Override
    public boolean implies(Permission p) {
        if (!(p instanceof MyPermission)) {
            return false;
        }
        MyPermission permission = (MyPermission) p;
        int thisPermissionArrLength = this.permissionArr.length;
        int permissionArrLength = permission.permissionArr.length;
        int cycleNum = Math.min(thisPermissionArrLength, permissionArrLength);
        for (int i = 0; i < cycleNum; i++) {
            if (!(this.permissionArr[i].equals(permission.permissionArr[i]))) {
                return false;
            }
        }
        return cycleNum == thisPermissionArrLength ? true : false;
    }
}
