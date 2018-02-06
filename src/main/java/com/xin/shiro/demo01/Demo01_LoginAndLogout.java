package com.xin.shiro.demo01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * @author xuexin
 * @date 2018/2/6
 */
public class Demo01_LoginAndLogout {
    @Test
    public void loginAndLogout() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:Users.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            //4、登录，即身份验证
            System.out.println("通过认证:" + subject.isAuthenticated());
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            if (e instanceof UnknownAccountException) {
                System.out.println("账号有误");
            } else if (e instanceof IncorrectCredentialsException) {
                System.out.println("密码有误");
            } else {
                System.out.println("登录失败:" + e.getMessage());
            }
        }
        System.out.println("通过认证:" + subject.isAuthenticated());
        //6、退出
        subject.logout();
        System.out.println("通过认证:" + subject.isAuthenticated());

    }
}
