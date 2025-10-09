// Marc Bertran.
// Fes un xifratge i desxifratge Polialfabetic.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {

    // Clau i objecte random.
    private static final Long KEY = (long) 12345;
    private static Random random;

    // Abecedaris, el normal o Base i el permutat.
    private final static char[] ABCORIGINAL = "AÁÀÄBCÇDEÉÈËFIÍÌÏGHJKLMNÑOÓÒÖPQUÚÙÜRSTVWXYZ".toCharArray();
    private static char[] abc;

    // Codi main proporcionat pel professor.
    public static void main(String[] args) {
        String msgs[] = { "Test 01 àrbitre, coixi, Perímetre",
                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila" };
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(KEY);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(KEY);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

     // Crea l'alfabet permutat per al xifratge. Retorna una char[].
    public static void permutaAlfabet() {
        List<Character> abcList = carregarABC();
        Collections.shuffle(abcList, random);

        abc = new char[abcList.size()];
        for (int i = 0; i < abc.length; i++) {
            abc[i] = abcList.get(i);
        }
        
    }

    // Aplica el xifratge o el desxifratge de la cadena.
    //      Envia el char 'D' a <metode> per Desxifrar la cadena 
    //      qualsevol altre caracter xifrará la cadena.
    private static String aplicaMonoAlfa(String cadena, char metode) {
        // Assigna quin es l'alfabet base i quin el codificat per a la traducció.
        //      Funciona depenent de com es crida la funcio. com s'ha explicat a la capçalera del metode.
        char[] base = ABCORIGINAL;
        char[] codificació = new char[1];
        if (metode == 'D') {
            codificació = ABCORIGINAL;
        }
        
        StringBuilder resoltat = new StringBuilder();

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            // Permutem l'alfabet per cada lletra.
            permutaAlfabet();

            // Assigna quin es l'alfabet base i quin el codificat per a la traducció.
            //      Funciona com al inici del metode.
            if (metode == 'D') { base = abc; }
            else { codificació = abc; }

            if (Character.isLetter(c)) {
                for (int index = 0; index < base.length; index++) {
                    char charBase = base[index];

                    if (charBase == c) {
                        resoltat.append(codificació[index]);
                        break;
                    }
                    else if (Character.toLowerCase(charBase) == c) {
                        resoltat.append(Character.toLowerCase(codificació[index]));
                        break;
                    }
                }
            } else {
                resoltat.append(c);
            }
        }

        return resoltat.toString();
    }

    // Xifra la cadena amb xifratge MonoAlfabetic.
    public static String xifraPoliAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, 'X');
    }

    // Desxifra la cadena amb xifratge MonoAlfabetic.
    public static String desxifraPoliAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, 'D');
    }

    // Carrega en una List<Character> un char[]
    private static List<Character> carregarABC() {
        List<Character> abcList = new ArrayList<>();

        for (char c : ABCORIGINAL) {
            abcList.add(c);
        }

        return abcList;
    }

    private static void initRandom(Long clau) {
        random = new Random(clau);
    }
}
