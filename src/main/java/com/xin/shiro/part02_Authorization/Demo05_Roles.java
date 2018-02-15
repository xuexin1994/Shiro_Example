package com.xin.shiro.part02_Authorization;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author xuexin
 * @date 2018/2/8
 */
public class Demo05_Roles {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号");
        String username = sc.nextLine();
        System.out.println("请输入密码");
        String password = sc.nextLine();
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:part02_Authorization/role.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            String role1 = "role1";
            String role2 = "role2";
            //登录前不具备角色
            System.out.println(subject.getPrincipal() + "是否拥有角色" + role1 + ":" + subject.hasRole(role1));
            System.out.println("------登录前------");
            subject.login(token);
            System.out.println("------登录后------");
            System.out.println(subject.getPrincipal() + "是否拥有角色" + role1 + ":" + subject.hasRole(role1));
            System.out.println(subject.getPrincipal() + "是否拥有角色" + role2 + ":" + subject.hasRole(role2));
            System.out.println(subject.getPrincipal() + "是否同时拥有角色role1，role2" + ":" + subject.hasAllRoles(Arrays.asList(new String[]{role1,role2})));
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println("登录失败:" + e.getMessage());
        }
        //6、退出
        subject.logout();
    }
}
