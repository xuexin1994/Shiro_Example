package com.xin.shiro.demo07;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author xuexin
 * @date 2018/2/11
 */
public class Demo07_ConfigByIni {
    public static void main(String[] args) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:demo07/config.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken("zs", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("登录失败");
        }
        String role1 = "role1";
        String permission1 = "my.permission1.read";
        String permission2 = "my.permission2";
        String permission3 = "permission3:write,read";
        String permission4 = "permission4:write,read";
        System.out.println(subject.getPrincipal() + "是否拥有角色" + role1 + ":" + subject.hasRole(role1));
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission1 + ":" + subject.isPermitted(permission1));
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission2 + ":" + subject.isPermitted(permission2));
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission3 + ":" + subject.isPermitted(permission3));
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission4 + ":" + subject.isPermitted(permission4));
        subject.logout();
    }
}
