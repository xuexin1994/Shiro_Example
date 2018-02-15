package com.xin.shiro.part03_config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xin.shiro.resolver.MyPermissionResolver;
import com.xin.shiro.resolver.MyRolePermissionResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.AuthenticatingSecurityManager;
import org.apache.shiro.mgt.AuthorizingSecurityManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * @author xuexin
 * @date 2018/2/10
 */
public class Demo07_ConfigByJava {
    public static void main(String[] args) {
        /**
         * --------------创建安全管理器--------------
         *  1.同时会默认创建默认的认证器
         *  @see AuthenticatingSecurityManager#AuthenticatingSecurityManager()
         *  2.在创建认证器ModularRealmAuthenticator时默认指定安全策略为AtLeastOneSuccessfulStrategy
         *  @see ModularRealmAuthenticator#ModularRealmAuthenticator()
         *  3.默认创建授权器
         *  @see AuthorizingSecurityManager#AuthorizingSecurityManager()
         */
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置权限转换器及角色权限转换器
        Authorizer authorizer = securityManager.getAuthorizer();
        if (authorizer instanceof ModularRealmAuthorizer) {
            ((ModularRealmAuthorizer) authorizer).setPermissionResolver(new MyPermissionResolver());
            ((ModularRealmAuthorizer) authorizer).setRolePermissionResolver(new MyRolePermissionResolver());
        }

        //--------------设置数据库连接池--------------
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://192.168.217.128:3306/shiro_example");
        ds.setUsername("root");
        ds.setPassword("root");


        //--------------设置Realm--------------
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);
        /**
         * 设置查询权限，默认为false(用户只关联角色，不关联权限)；如果要赋予权限则将其设置为true。
         * @see JdbcRealm#doGetAuthorizationInfo(PrincipalCollection)
         * 如果如果需要设置权限的查询语句可以用setPermissionsQuery方法设置
         * @see JdbcRealm#setPermissionsQuery(String)
         */
        jdbcRealm.setPermissionsLookupEnabled(true);
        /**
         * --------------设置安全管理器中的Realm--------------
         *  1.创建realm时会默认将realm中的权限转换器设置为WildcardPermissionResolver
         *  @see AuthorizingRealm#AuthorizingRealm(org.apache.shiro.cache.CacheManager, org.apache.shiro.authc.credential.CredentialsMatcher) 空参构造调用有参构造
         *  2.将realm添加到安全管理器时会将安全管理器中的permissionResolver(权限转换器)设置到realm中
         *  @see AuthorizingSecurityManager#afterRealmsSet()
         *  @see ModularRealmAuthorizer#setRealms(java.util.Collection)
         *  @see ModularRealmAuthorizer#applyPermissionResolverToRealms() 在此处将安全管理器的permissionResolver覆盖至每个realm(如果permissionResolver不为null)
         */
        securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken("zs", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("登录失败,失败原因:" + e);
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
