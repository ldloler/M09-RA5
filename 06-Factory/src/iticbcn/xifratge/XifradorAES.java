package iticbcn.xifratge;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import iticbcn.xifratge.errors.ClauNoSuportada;

public class XifradorAES implements Xifrador{

    public final String ALGORISME_XIFRAT = "AES";
    public final String ALGORISME_HASH = "SHA-256";
    public final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private final int MIDA_IV = 16;
    private byte[] iv = new byte[MIDA_IV];
    //private final String CLAU = "Marc123";

    private byte[] xifraAES(String msg, String clau) throws Exception {
        // Obtenir els bytes de l'String
        byte[] msgBytes = msg.getBytes("UTF-8");
        // Genera IvParameterSpec
        IvParameterSpec ivParams = insertRandomIV();
        // Genera hash
        SecretKeySpec sk = getSK(clau);
        // Encrypt.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, sk, ivParams);
        byte[] encriptat = cipher.doFinal(msgBytes);
        // Combinar IV i part xifrada.
        byte[] bIvIMsgXifrat = new byte[iv.length + encriptat.length];
        System.arraycopy(iv, 0, bIvIMsgXifrat, 0, 16);
        System.arraycopy(encriptat, 0, bIvIMsgXifrat, 16, encriptat.length);
        // return iv+msgxifrat
        return bIvIMsgXifrat;
    }

    private String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        // Extreure l'IV.
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, 16);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        // Extreure la part xifrada.
        byte[] MsgXifrat = new byte[bIvIMsgXifrat.length-16];
        System.arraycopy(bIvIMsgXifrat, 16, MsgXifrat, 0, MsgXifrat.length);
        // Fer hash de la clau
        SecretKeySpec sk = getSK(clau);
        // Desxifrar.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, sk, ivParams);
        byte[] desxifrat = cipher.doFinal(MsgXifrat);
        // return String desxifrat
        return new String(desxifrat);
    }

    private IvParameterSpec insertRandomIV() {
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private SecretKeySpec getSK(String clau) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        md.update(clau.getBytes("UTF-8"));
        byte[] digest = md.digest();
        SecretKeySpec sk = new SecretKeySpec(digest, ALGORISME_XIFRAT);
        return sk;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        
        try {
            return new TextXifrat(xifraAES(msg, clau));
        } catch (Exception e) {
            throw new ClauNoSuportada(e.getMessage());
        }

    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e) {
            throw new ClauNoSuportada(e.getMessage());
        }
    }
}
