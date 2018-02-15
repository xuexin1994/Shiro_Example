package com.xin.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * @author xuexin
 * @date 2018/2/13
 */
public class MyPasswordServiceRealm extends AuthenticatingRealm {

    private PasswordService passwordService;

    public PasswordService getPasswordService() {
        return passwordService;
    }

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof UsernamePasswordToken)) {
            throw new UnsupportedTokenException();
        }
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        return new SimpleAuthenticationInfo(usernamePasswordToken.getUsername(),
                passwordService.encryptPassword(usernamePasswordToken.getPassword()),
                getName());

    }
}
