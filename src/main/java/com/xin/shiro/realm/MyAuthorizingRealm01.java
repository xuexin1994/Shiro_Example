package com.xin.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.List;

/**
 * @author xuexin
 * @date 2018/2/9
 */
public class MyAuthorizingRealm01 extends AuthorizingRealm {
    /**
     * 授权方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //如果该账户是"zs"
        if ("zs".equals(principals.toString())) {
            info.addRole("boss");
        }
        info.addStringPermission("my.permission1");
        info.addStringPermission("my.permission2.read");
        info.addStringPermission("permission3:write");
        info.addStringPermission("permission4:read,write");
        return info;
    }

    /**
     * 认证方法
     * getAuthenticationInfo先从缓存中获取(getCachedAuthenticationInfo)，如果缓存中为null则调用该方法
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //得到用户名
        String username = (String) token.getPrincipal();
        //得到密码
        String password = new String((char[]) token.getCredentials());
        String[] userArr = {"zs", "ls"};
        List<String> userList = Arrays.asList(userArr);
        if (userList.indexOf(username) == -1) {
            //如果用户名错误
            throw new UnknownAccountException();
        }
        if (!"123".equals(password)) {
            //如果密码错误
            throw new IncorrectCredentialsException();
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
