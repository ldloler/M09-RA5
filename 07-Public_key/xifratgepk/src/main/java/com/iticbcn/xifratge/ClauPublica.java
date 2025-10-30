package com.iticbcn.xifratge;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;

public class ClauPublica {

    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048, new SecureRandom());
        KeyPair kp = kpg.generateKeyPair();
        return kp;
    }

    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception {
         // Obtenir els bytes de l'String
        byte[] msgBytes = msg.getBytes("UTF-8");
        
        // Encrypt.
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, clauPublica);
        byte[] encriptat = cipher.doFinal(msgBytes);
        
        // return iv+msgxifrat
        return encriptat;
    }

    public String desxifraRSA(byte[] msgXifrat, PrivateKey clauPrivada) throws Exception {
        // Desxifrar.
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, clauPrivada);
        byte[] desxifrat = cipher.doFinal(msgXifrat);
        // return String desxifrat
        return new String(desxifrat);
    }
}
