package com.xin.shiro.realm;

import com.xin.shiro.util.Content;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @author xuexin
 * @date 2018/2/14
 */
public class MyHashRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof UsernamePasswordToken)) {
            throw new UnsupportedTokenException();
        }
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        String salt = Content.KEY_MD5;
        Hash hash = new Md5Hash(password, salt, 2);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, hash.toBase64(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(salt));
        return info;
    }
}
