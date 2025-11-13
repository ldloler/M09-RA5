import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;

public class Hashes {

    int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.update(salt.getBytes());
        byte[] encodedhash = digest.digest(pw.getBytes(StandardCharsets.UTF_8));
        HexFormat hex = HexFormat.of();
        return hex.formatHex(encodedhash);
    }

    public String getPBKDF2AmbSalt(String pw, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash_bytes = factory.generateSecret(spec).getEncoded();
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash_bytes);
    }

    public String forcaBruta(String alg, String hash, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] charset = "abcdefABCDEF0123456789!".toCharArray();

        char[] trypass = new char[6];

        npass = 0;

        String pw;
        for (int j = 0; j < charset.length; j++) {
            if ((pw = pwTrobat(alg, trypass, 0, charset[j], hash, salt)) != null)
                return pw;
            for (int k = 0; k < charset.length; k++) {
                if ((pw = pwTrobat(alg, trypass, 1, charset[k], hash, salt)) != null)
                    return pw;
                for (int k2 = 0; k2 < charset.length; k2++) {
                    if ((pw = pwTrobat(alg, trypass, 2, charset[k2], hash, salt)) != null)
                        return pw;
                    for (int l = 0; l < charset.length; l++) {
                        if ((pw = pwTrobat(alg, trypass, 3, charset[l], hash, salt)) != null)
                            return pw;
                        for (int l2 = 0; l2 < charset.length; l2++) {
                            if ((pw = pwTrobat(alg, trypass, 4, charset[l2], hash, salt)) != null)
                                return pw;
                            for (int m = 0; m < charset.length; m++) {
                                npass++;
                                if ((pw = pwTrobat(alg, trypass, 5, charset[m], hash, salt)) != null)
                                    return pw;
                            }
                        }
                    }
                }
            }
        }

        return "";
    }

    private String pwTrobat(String alg, char[] trypass, int pos, char charset, String hash, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        trypass[pos] = charset;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= pos; i++) {
            sb.append(trypass[i]);
        }

        String s = sb.toString();

        String try_hash = "";
        if (alg.equals("SHA-512"))
            try_hash = getSHA512AmbSalt(s, salt);
        else
            try_hash = getPBKDF2AmbSalt(s, salt);

        npass++;

        if (try_hash.equals(hash))
            return s;
        return null;
    }

    private String getInterval(long t1, long t2) {
        long diferencia = t2 - t1;

        // long millis;
        // long segons = TimeUnit.MILLISECONDS.toSeconds(diferencia);
        // long minuts = TimeUnit.MILLISECONDS.toMinutes(diferencia);
        // long hores = TimeUnit.MILLISECONDS.toHours(diferencia);
        // long dies = TimeUnit.MILLISECONDS.toDays(diferencia);

        // String toString = String.format("%d dies / %d hores / %d minuts / %d segons /
        // %d millis", dies, hores, minuts,
        // segons, millis);

        Duration duration = Duration.ofMillis(diferencia);

        String formattedElapsedTime = String.format(
                "%d dies / %d hores / %d minuts / %d segons / %d millis", duration.toDays(),
                duration.toHours() % 24, duration.toMinutes() % 60, duration.toSeconds() % 60,
                duration.toMillis() % 1000);

        return formattedElapsedTime;
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algoritmes = { "SHA-512", "PBKDF2" };
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("=================================\n");
            System.out.printf("Algorisme: %s\n", algoritmes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("---------------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algoritmes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------------\n\n");
        }
    }
}