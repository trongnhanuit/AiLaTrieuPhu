package util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA{
static PublicKey publicKey;
static PrivateKey privateKey;

public static void main(String[] args) throws Exception 
{
//    getKeys();
//    String ciphertext= encryption("NguyenThanhHoa");
//    System.out.print(" - Ciphertext: "+ciphertext+"\n");
//    System.out.print("-------------------------------\n");
//    String plaintext= decryption(ciphertext);
//    System.out.print(" - Plaintext: "+plaintext+"\n");
}

public static void generateKeys() throws Exception 
{
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(2048);
    KeyPair kp = kpg.genKeyPair();
    publicKey = kp.getPublic();
    privateKey = kp.getPrivate();
    
    System.out.println("--------------------------");
    System.out.println("");
    System.out.print(Base64.encodeBytes(publicKey.getEncoded()));
    System.out.println("");
    System.out.println("--------------------------");

    System.out.println("");

    System.out.println("--------------------------");
    System.out.println("");
    System.out.print(Base64.encodeBytes(privateKey.getEncoded()));
    System.out.println("");
    System.out.println("--------------------------");
}

public static void getKeys() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
{
    //  get public key
    X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj2wJnRQTim94UjL6TNcJBSplWtw03+0pQ1j8oacPb0oaneW9xCWtufHto5eEo8TZ7EZyTr0zuAp27gI+X1NJUCKR/ZcEK0+JEsbelpPkokzqyK1gFS3DgnbvXrjNXo6jRLBHD6m8jQdb0H0CDbaL0ekSYgYWvgAzdU+hw+T6bU1TCXdTzhkbGp8UEgxDmu+Uras89iGhMXOoPQtCCfRKwvisRk3RZ/68O2sT+tLr23ygpn4LtYbxpVW4eIm5luJ0w+wY5fZuM4XvMPwbBlXtfGdAl24JSg5EpK3fFOUZhWRlxjRBJGiYNwzSAwD30vX2OgJQNbIXz6mxMkJ2BkdJUwIDAQAB"));
    KeyFactory kf = KeyFactory.getInstance("RSA");
    publicKey = kf.generatePublic(spec);

    //    get the private key  
    PKCS8EncodedKeySpec specPriv = new PKCS8EncodedKeySpec(Base64.decode("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCPbAmdFBOKb3hSMvpM1wkFKmVa3DTf7SlDWPyhpw9vShqd5b3EJa258e2jl4SjxNnsRnJOvTO4CnbuAj5fU0lQIpH9lwQrT4kSxt6Wk+SiTOrIrWAVLcOCdu9euM1ejqNEsEcPqbyNB1vQfQINtovR6RJiBha+ADN1T6HD5PptTVMJd1POGRsanxQSDEOa75Stqzz2IaExc6g9C0IJ9ErC+KxGTdFn/rw7axP60uvbfKCmfgu1hvGlVbh4ibmW4nTD7Bjl9m4zhe8w/BsGVe18Z0CXbglKDkSkrd8U5RmFZGXGNEEkaJg3DNIDAPfS9fY6AlA1shfPqbEyQnYGR0lTAgMBAAECggEAT1sx/o3rOf/vjVvTjsy0mv0YU6vgPb6VxEPqlO9A5Q0VqSRDWdKXS3mHSas+ovb6tXdNLDAX+88eexcWt9v3hrV6Hdx/XA+IArGy25I9QWhZyGvV7CIEOZ3hWIHccGNwB0OaLhp2z3mw3a0tg7TrrFvP5hkEYGDwGArVsPRzdCIoLvXw2Tlfcx5fpmXQWM2kd7XjPYk+/w2lFnEleeWrPmJbnzGssfgcUS6JQe+z18Ma2zCnNaQgc6wy/R9wd7EPETEHaTz9QfgNFddvghQa/9hgE/yXLW8E4mwYSFs+nX8cmhQ2vL+K4Y1ExL1uK2tqma0Dym9PewVN4bAFi2Sw8QKBgQD1WklWLkQ2d/YeqRtVUCv1cBxbI4HbNmO8lY7P9g0vh10bWQuvj6V0EF/4Hg70V5tjOhAZy6FSn5lbveNR5BLys4V11ZggqPabw61yKV4uhtUqFTXNAyS5TbBBg/KWn1rxn80niNPI182IVqOpqZNM8ucwwdftnfsyxi9P6q6L/wKBgQCVpV2hkkLhBHBDeJM+MJKXHLpVyjvVAG/q8pmaVs6LaV8mHb/hhPQZjPhQDwYldrMfFCh11Gls/ARaSo11eJlwdJBQOSDhNb/GxQce8pn8Ln2Z40IN7DF+rbg6O4rBMDyUYFffnMBQklD2jUzdJLqY3PydTZ9/wXRfKzXuNoVSrQKBgGyOjyY/gFTtTQuR3RMpTtngydsVYc5hSU3WioK/JmRiKzL/eikqkJVC7YN1oT5BSPJlbVxMect57DvoT50bIbsa7TEO2amRuNOAh8ycOxyw8b4V9mEIRAZtNlA2lm5Igc+o2fflFXem7DynwPA/pHoGe35aNjTfkpaLk8FzX9l3AoGAGPWrB07w7r0hyG8tB/tsB95XygVgM2fOQv7R1edSrfgWUbI2lfPqDvTvWdMXsFn7CF0BXp3eM7pZM4Ip1AUYeqRGicrtjAK+CPZF+Mh4HJLYijlYhB/4JWvuRXA29YAJKqpjHb30abvt2yG4R05f/S0myUnwJ4vp0BHR/fiEBkUCgYBw8HYJVAQyY07+U5SQo352GDXPztl3xmgan9OM2rF1wXA4IEas0BoZ1yyv1iwsgH9KnaoTFJJdMfGWAIeTCyjcsP/oBPc1DTJbfYbwgzKpvJr4TWElMr9Lv0sXdPY6DK1ScFIryvTmVCUcMb0kxuT0Apv0OwEGgIYtKkWD+0UHTA=="));
    privateKey = kf.generatePrivate(specPriv);
}

public static String encryption(String plaintext) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
{
    Cipher cipher = Cipher.getInstance("RSA");  
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] cipherText = cipher.doFinal(plaintext.getBytes());
    return Base64.encodeBytes(cipherText);
}

public static String decryption(String ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
{
    Cipher cipher = Cipher.getInstance("RSA"); 
    cipher.init(Cipher.DECRYPT_MODE, privateKey); //privKey stored earlier
    byte[] descryptedData = cipher.doFinal(Base64.decode(ciphertext));
    return new String(descryptedData);
}
}