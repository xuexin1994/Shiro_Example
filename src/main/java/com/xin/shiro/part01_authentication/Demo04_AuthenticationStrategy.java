package com.xin.shiro.part01_authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

/**
 * @author xuexin
 * @date 2018/2/8
 * AuthenticationStrategy(认证策略)---在多个Realm的时候可能会有相同的凭证(账户秘密相同)对应不同Realm中的不同身份信息(AuthenticationInfo)
 *  例如com.xin.shiro.realm.MyRealm01与com.xin.shiro.realm.MyRealm01_2中皆有username="zs",password="123"返回不同的身份信息
 *      在所有Realm验证之前执行
 *          AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token) throws AuthenticationException;
 *      在每个Realm验证之前调用
 *          AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException;
 *      在每个Realm验证之后调用
 *          AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t)throws AuthenticationException;
 *      在所有Realm验证之后调用
 *          AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException;
 */
public class Demo04_AuthenticationStrategy {
    public String defaultUsername = "zs";
    public String defaultPassword = "123";

    /**
     * FirstSuccessfulStrategy：只要有一个Realm验证成功即可，只返回第一个Realm身份验证成功的认证信息，其他的忽略；
     */
    @Test
    public void test_FirstSuccessfulStrategy() {
        String username = defaultUsername;
        String password = defaultPassword;
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:part01_authentication/firstSuccessfulStrategy.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            subject.login(token);
            PrincipalCollection pc = subject.getPrincipals();
            for (Object o : pc) {
                System.out.println("Principal:" + o);
            }
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println("登录失败:" + e);
        }
        //6、退出
        subject.logout();
    }

    /**
     * AtLeastOneSuccessfulStrategy：只要有一个Realm验证成功即可，和FirstSuccessfulStrategy不同，返回所有Realm身份验证成功的认证信息；
     */
    @Test
    public void test_AtLeastOneSuccessfulStrategy() {
        String username = defaultUsername;
        String password = defaultPassword;
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:part01_authentication/atLeastOneSuccessfulStrategy.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            subject.login(token);
            PrincipalCollection pc = subject.getPrincipals();
            for (Object o : pc) {
                System.out.println("Principal:" + o);
            }
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println("登录失败:" + e);
        }
        //6、退出
        subject.logout();
    }

    /**
     * AllSuccessfulStrategy：所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息，如果有一个失败就失败了。
     * 配置文件中注释掉realm02则登录成功
     */
    @Test
    public void test_AllSuccessfulStrategy() {
        String username = defaultUsername;
        String password = defaultPassword;
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:part01_authentication/allSuccessfulStrategy.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils，这是全局设置，只需要设置一次；
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            subject.login(token);
            PrincipalCollection pc = subject.getPrincipals();
            for (Object o : pc) {
                System.out.println("Principal:" + o);
            }
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println("登录失败:" + e);
        }
        //6、退出
        subject.logout();
    }

    @After
    public void tearDown() throws Exception {
        //退出时解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }
}
