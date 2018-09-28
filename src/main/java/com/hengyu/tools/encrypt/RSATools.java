package com.hengyu.tools.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Hashtable;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/6/8
 * Time：21:50
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
public class RSATools {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    //公钥名称
    private static final String PUBLIC_KEY = "publicKey";
    //密钥名称
    private static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    private static final int KEY_SIZE = 2048;

    //全局存放rsa公钥，密钥集合
    private final static Hashtable<String,byte[]> KEYS = new Hashtable<>();
    //构造函数私有化
    private RSATools(){}
    //初始化加密key
    static {
        //generateKeyBytes();
    }

    //存放publicKeyString以及privateKeyString
    private final static Hashtable<String,String> KEYS_STRING = new Hashtable<>();
    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     *
     * @return
     */
    private static void generateKeyBytes() {

        try {
            //创建keyPart生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            //初始化rsa密钥长度
            keyPairGenerator.initialize(KEY_SIZE);
            //获取KeyPair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //获取RSAPublicKey公钥key对象
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            //获取RSAPrivateKey密钥key对象
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            KEYS.put(PUBLIC_KEY, publicKey.getEncoded());
            KEYS.put(PRIVATE_KEY, privateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    private static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    private static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA加密
     *
     * @param plainText 需要加密的数组
     * @return 加密后的字符串
     */
    public static String RSAEncode(String plainText) throws Exception{

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, restorePublicKey(decryptBASE64(getPublicKey())));
        return Base64Tools.encrypt(cipher.doFinal(plainText.getBytes()));

    }

    /**
     * 解密，三步走。
     *
     * @param encodedText
     * @return
     */
    public static String RSADecode(String encodedText) throws Exception{
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, restorePrivateKey(decryptBASE64(getPrivateKey())));
        return new String(cipher.doFinal(Base64Tools.decrypt(encodedText)));
    }

    /**
     * 获取公钥字符串
     * 第一次从/resource/rsa/rsa.public文件中获取
     * 之后从内存中获取
     * @return
     * @throws Exception
     */
    private static String getPublicKey() throws Exception {
        //先从内存中获取
        String publicKey = KEYS_STRING.get(PUBLIC_KEY);
        if(publicKey == null || publicKey == "") {
            InputStream inputStream = RSATools.class.getClass().getResourceAsStream("/rsa/rsa.public");
            byte b[] = new byte[KEY_SIZE];
            int len = 0;
            int temp = 0;          //所有读取的内容都使用temp接收
            while ((temp = inputStream.read()) != -1) {    //当没有读取完时，继续读取
                b[len] = (byte) temp;
                len++;
            }
            inputStream.close();
            //返回put
            publicKey = new String(b, 0, len);
            //存放到内存中
            KEYS_STRING.put(PUBLIC_KEY , publicKey);
        }
        //编码返回字符串
        return publicKey;
    }

    /**
     * 获取私钥字符串
     * @return
     * @throws Exception
     */
    private static String getPrivateKey() throws Exception {
        String privateKey = KEYS_STRING.get(PRIVATE_KEY);
        if(privateKey == null || privateKey == "") {
            InputStream inputStream = RSATools.class.getClass().getResourceAsStream("/rsa/rsa.private");
            byte b[] = new byte[KEY_SIZE];
            int len = 0;
            int temp = 0;          //所有读取的内容都使用temp接收
            while ((temp = inputStream.read()) != -1) {    //当没有读取完时，继续读取
                b[len] = (byte) temp;
                len++;
            }
            inputStream.close();
            //返回put
            privateKey = new String(b, 0, len);
            //存放到内存中
            KEYS_STRING.put(PRIVATE_KEY , privateKey);
        }
        //编码返回字符串
        return privateKey;
    }

    /**
     * 将公钥、密钥字符串还原成byte数组
     * @param key 公钥或者密钥字符串
     * @return byte数组
     * @throws Exception
     */
    private static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 将公钥、密钥byte[]数组转换成字符串
     * @param key 公钥密钥byte数组对象
     * @return 字符串
     * @throws Exception
     */
    private static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static void main(String[] args) throws Exception {

       /* System.out.println("public key : "+getPublicKey());
        System.out.println("private key : "+getPrivateKey());

        JSONObject object = new JSONObject();
        object.put("name","恒与少年");

        String encodedText = RSAEncode(JSON.toJSONString(object));
        System.out.println("RSA encoded: " + encodedText);

        // 解密
        System.out.println("RSA decoded: "
                + RSADecode(encodedText));*/
        //String str = System.currentTimeMillis()+"."+"OUM2NEY3NDA3MUNGMjJFOTBGQ0JERUJDMkRCNUJFQ0Q=";
        String code = RSAEncode("c2R4bWtqX21vYmlsZTQ4QTkxMzAwMTkyQzJFMTgzODc0N0NFMzk4MTREM0ZG");
        System.out.println(code);
        System.out.println(RSADecode(code));
    }
}
