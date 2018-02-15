package com.xin.shiro.part04_encrypt;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.security.Key;

/**
 * @author xuexin
 * @date 2018/2/12
 * CodecSupport，提供了toBytes(str, "utf-8") / toString(bytes,“utf-8")用于在byte 数组/String 之间转换。
 */
public class Demo08_EncodeAndDecode {
    public static final String SRC_STRING = "这是原始字符串";
    public static final String SALT = "这是盐";
    public static final int ITERATIONS = 2;


    @Test
    public void Base64() {
        String base64String = Base64.encodeToString(SRC_STRING.getBytes());
        System.out.println("Base64加密后的数据为:" + base64String);
        System.out.println("Base64解密后的数据为:" + Base64.decodeToString(base64String));
    }

    @Test
    public void Hex() {
        String hexString = Hex.encodeToString(SRC_STRING.getBytes());
        System.out.println("十六进制加密后的数据为:" + hexString);
        System.out.println("十六进制解密后的数据为:" + CodecSupport.toString(Hex.decode(hexString)));
    }

    @Test
    public void Hash() {
        System.out.println("MD5加密后的数据为:" + new Md5Hash(SRC_STRING));
        System.out.println("MD5加盐后加密后的数据为:" + new Md5Hash(SRC_STRING, SALT));
        System.out.println("MD5加盐并且多次散列后加密后的数据为:" + new Md5Hash(SRC_STRING, SALT, ITERATIONS));
        System.out.println("SHA-1加密后的数据为:" + new SimpleHash("SHA-1", SRC_STRING));
        System.out.println("SHA-256加密后的数据为:" + new SimpleHash("SHA-256", SRC_STRING));
    }

    @Test
    public void Aes() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(192);//AES加密密钥要求128,192,256;由于jdk只提供128位支持，如果要支持192或256需要修改jre的相关jar包
        Key key = aesCipherService.generateNewKey();
        ByteSource encrypt = aesCipherService.encrypt(SRC_STRING.getBytes(), key.getEncoded());
        System.out.println(encrypt.toHex());
        ByteSource decrypt = aesCipherService.decrypt(encrypt.getBytes(), key.getEncoded());
        System.out.println(new String(decrypt.getBytes()));
    }
}
