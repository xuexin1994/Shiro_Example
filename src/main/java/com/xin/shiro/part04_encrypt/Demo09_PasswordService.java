package com.xin.shiro.part04_encrypt;

import com.xin.shiro.MainTest;
import org.apache.shiro.subject.Subject;

import java.util.Scanner;

/**
 * @author xuexin
 * @date 2018/2/13
 */
public class Demo09_PasswordService {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号");
        String username = sc.nextLine();
        System.out.println("请输入密码");
        String password = sc.nextLine();
        String configFile = "classpath:part04_encrypt/passwordService.ini";
        Subject subject = MainTest.login(configFile, username, password);
        subject.logout();
    }
}
