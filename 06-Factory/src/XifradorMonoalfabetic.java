import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic {

    // Abecedaris, el normal o Base i el permutat.
    private final static char[] ABCORIGINAL = "AÁÀÄBCÇDEÉÈËFIÍÌÏGHJKLMNÑOÓÒÖPQUÚÙÜRSTVWXYZ".toCharArray();
    private final static char[] ABC = permutaAlfabet();

    // Codi amb les proves.
    public static void main(String[] args) {
        String[] inicials = { "ABC", "xyz", "Hola, Mr. calçot", "Pedró, per tu què és?" };
        
        System.out.println("XIFRAT\n------------");

        String[] xifrades = new String[4];
        for (int i = 0; i < inicials.length; i++) {
            xifrades[i] = xifraMonoAlfa(inicials[i]);
            System.out.printf("%s => %s\n", inicials[i], xifrades[i]);
        }
        System.out.println();

        System.out.println("DESXIFRAT\n------------");

        String[] desxifrades = new String[4];
        for (int i = 0; i < xifrades.length; i++) {
            desxifrades[i] = desxifraMonoAlfa(xifrades[i]);
            System.out.printf("%s => %s\n", xifrades[i], desxifrades[i]);
        }
    }

    // Aplica el xifratge o el desxifratge de la cadena.
    //      Envia el char 'D' a <metode> per Desxifrar la cadena 
    //      qualsevol altre caracter xifrará la cadena.
    private static String aplicaMonoAlfa(String cadena, char metode) {
        char[] base = ABCORIGINAL;
        char[] codificació = ABC;
        if (metode == 'D') {
            base = ABC;
            codificació = ABCORIGINAL;
        }
        
        StringBuilder resoltat = new StringBuilder();

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

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
    public static String xifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, 'X');
    }

    // Desxifra la cadena amb xifratge MonoAlfabetic.
    public static String desxifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, 'D');
    }

    // Crea l'alfabet permutat per al xifratge. Retorna una char[].
    public static char[] permutaAlfabet() {
        List<Character> abcList = carregarABC();
        Collections.shuffle(abcList);

        char[] resoltat = new char[abcList.size()];
        for (int i = 0; i < resoltat.length; i++) {
            resoltat[i] = abcList.get(i);
        }

        return resoltat;
    }

    // Carrega en una List<Character> un char[]
    private static List<Character> carregarABC() {
        List<Character> abcList = new ArrayList<>();

        for (char c : ABCORIGINAL) {
            abcList.add(c);
        }

        return abcList;
    }
}
 