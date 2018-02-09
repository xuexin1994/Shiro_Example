package com.xin.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;

/**
 * @author xuexin
 * @date 2018/2/6
 */
public class MyRealm01 implements Realm {
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof UsernamePasswordToken)) {
            throw new UnsupportedTokenException();
        }
        String username = (String) token.getPrincipal();
        char[] passwordArr = (char[]) token.getCredentials();
        String password = new String(passwordArr);
        if (!"zs".equals(username)) {
            throw new UnknownAccountException();
        }
        if (!"123".equals(password)) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
