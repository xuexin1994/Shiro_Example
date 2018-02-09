package com.xin.shiro.demo02;

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
 * @date 2018/2/7
 */
public class Demo02_Realm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号");
        String username = sc.nextLine();
        System.out.println("请输入密码");
        String password = sc.nextLine();
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:demo02/realm.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            System.out.println("未调用login方法--->通过认证:" + subject.isAuthenticated());
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println("登录失败:" + e);
        }
        System.out.println("调用login方法--->通过认证:" + subject.isAuthenticated());
        //6、退出
        subject.logout();
        System.out.println("调用logout方法--->通过认证:" + subject.isAuthenticated());
    }
}
