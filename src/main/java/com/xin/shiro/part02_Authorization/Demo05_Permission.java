package com.xin.shiro.part02_Authorization;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xuexin
 * @date 2018/2/8
 */
public class Demo05_Permission {

    public final String permission_1 = "permission:1";
    public final String permission_2 = "permission:2";
    public final String permission_1_2 = "permission:1,2";

    @Before
    public void prepare() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:part02_Authorization/permission.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

    @After
    public void clean() {
        //退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }

    public Subject login(String username, String password) {
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("登录失败:" + e.getMessage());
        }
        return subject;
    }

    @Test
    public void test01() {
        String username = "u1";
        String password = "123";
        //4、登录，即身份验证
        Subject subject = login(username, password);
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission_1 + ":" + subject.isPermitted(permission_1));
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission_2 + ":" + subject.isPermitted(permission_2));
        System.out.println(subject.getPrincipal() + "是否拥有权限\"permission:1:2\"" + ":" + subject.isPermitted("permission:1:2"));
        subject.logout();
    }

    @Test
    public void test02() {
        String username = "u2";
        String password = "123";
        //4、登录，即身份验证
        Subject subject = login(username, password);
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission_1 + ":" + subject.isPermitted(permission_1));
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission_2 + ":" + subject.isPermitted(permission_2));
        subject.logout();
    }

    /**
     * "permission:1,2"表示拥有"permission:1"与"permission:2"，isPermitted("permission:1,2")为true
     * permission:1,permission:2表示拥有"permission:1"与"permission:2"，但是isPermitted("permission:1,2")为false
     */
    @Test
    public void test03() {
        //4、登录，即身份验证
        Subject subject = login("u1", "123");
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1 + "\"与\"" + permission_2 + "\":" + subject.isPermittedAll(permission_1, permission_2));
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1_2 + "\":" + subject.isPermitted(permission_1_2));
        subject.logout();
        subject = login("u3", "123");
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1 + "\"与\"" + permission_2 + "\":" + subject.isPermittedAll(permission_1, permission_2));
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1_2 + "\":" + subject.isPermitted(permission_1_2));
        subject.logout();
    }

    /**
     * "*"表示通配符，但是只作用于权限，不能相当于字符的通配符
     * "permission:*"表示拥有permission权限下的所有权限
     * "permission*"表示拥有名为"permission*"的权限下，但是并不匹配"permission:1"等权限
     */
    @Test
    public void test04() {
        //4、登录，即身份验证
        Subject subject = login("u4", "123");
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1 + "\"与\"" + permission_2 + "\":" + subject.isPermittedAll(permission_1, permission_2));
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1_2 + "\":" + subject.isPermitted(permission_1_2));
        subject.logout();
        subject = login("u5", "123");
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1 + "\"与\"" + permission_2 + "\":" + subject.isPermittedAll(permission_1, permission_2));
        System.out.println(subject.getPrincipal() + "是否拥有权限\"" + permission_1_2 + "\":" + subject.isPermitted(permission_1_2));
        subject.logout();
    }

    @Test
    public void test05() {
        //4、登录，即身份验证
        Subject subject = login("u6", "123");
        System.out.println(subject.getPrincipal() + "是否拥有权限" + permission_1 + "\"与other:1\":" + subject.isPermittedAll(permission_1, "other:1"));
        subject.logout();
    }

}
