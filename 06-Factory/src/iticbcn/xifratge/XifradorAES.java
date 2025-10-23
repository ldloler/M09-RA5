package iticbcn.xifratge;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "Marc123";

    public static void main(String[] args) {
        String msgs[] = { "Lorem ipsum dicet",
                "Hola Andrés cómo está tu cuñado",
                "Àgora illa Ótto" };

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            }

            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static byte[] xifraAES(String msg, String clau) throws Exception {
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

    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
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

    private static IvParameterSpec insertRandomIV() {
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private static SecretKeySpec getSK(String clau) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        md.update(clau.getBytes("UTF-8"));
        byte[] digest = md.digest();
        SecretKeySpec sk = new SecretKeySpec(digest, ALGORISME_XIFRAT);
        return sk;
    }
}
