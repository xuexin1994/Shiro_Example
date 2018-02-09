package com.xin.shiro.demo06;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.Scanner;

/**
 * @author xuexin
 * @date 2018/2/9
 */
public class Demo06_AuthorizingRealm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号");
        String username = sc.nextLine();
        System.out.println("请输入密码");
        String password = sc.nextLine();
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:demo06/authorizer.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            String role1 = "role1";
            String permission1 = "my.permission1.read";
            String permission2 = "my.permission2";
            String permission3 = "permission3:write,read";
            String permission4 = "permission4:write,read";
            //登录前不具备角色
            subject.login(token);
            System.out.println(subject.getPrincipal() + "是否拥有角色" + role1 + ":" + subject.hasRole(role1));
            System.out.println(subject.getPrincipal() + "是否拥有权限" + permission1 + ":" + subject.isPermitted(permission1));
            System.out.println(subject.getPrincipal() + "是否拥有权限" + permission2 + ":" + subject.isPermitted(permission2));
            System.out.println(subject.getPrincipal() + "是否拥有权限" + permission3 + ":" + subject.isPermitted(permission3));
            System.out.println(subject.getPrincipal() + "是否拥有权限" + permission4 + ":" + subject.isPermitted(permission4));
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println("登录失败:" + e);
        }
        //6、退出
        subject.logout();
    }

}
