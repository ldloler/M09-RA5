import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {

    final static char[] ABCORIGINAL = "AÁÀÄBCÇDEÉÈËFIÍÌÏGHJKLMNÑOÓÒÖPQUÚÙÜRSTVWXYZ".toCharArray();
    final static char[] ABC = permutaAlfabet();

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

    public static String xifraMonoAlfa(String cadena) {
        StringBuilder resoltat = new StringBuilder();

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            if (Character.isLetter(c)) {
                for (int index = 0; index < ABCORIGINAL.length; index++) {
                    char charABCO = ABCORIGINAL[index];

                    if (charABCO == c) {
                        resoltat.append(ABC[index]);
                        break;
                    }
                    else if (Character.toLowerCase(charABCO) == c) {
                        resoltat.append(Character.toLowerCase(ABC[index]));
                        break;
                    }
                }
            } else {
                resoltat.append(c);
            }
        }

        return resoltat.toString();
    }

    public static String desxifraMonoAlfa(String cadena) {
        StringBuilder resoltat = new StringBuilder();

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            
            if (Character.isLetter(c)) {
                for (int index = 0; index < ABC.length; index++) {
                    char charABC = ABC[index];

                    if (charABC == c) {
                        resoltat.append(ABCORIGINAL[index]);
                        break;
                    }
                    else if (Character.toLowerCase(charABC) == c) {
                        resoltat.append(Character.toLowerCase(ABCORIGINAL[index]));
                        break;
                    }
                }
            } else {
                resoltat.append(c);
            }
        }

        return resoltat.toString();
    }

    public static char[] permutaAlfabet() {
        List<Character> abcList = carregarABC();
        Collections.shuffle(abcList);

        char[] resoltat = new char[abcList.size()];
        for (int i = 0; i < resoltat.length; i++) {
            resoltat[i] = abcList.get(i);
        }

        return resoltat;
    }

    private static List<Character> carregarABC() {
        List<Character> abcList = new ArrayList<>();

        for (char c : ABCORIGINAL) {
            abcList.add(c);
        }

        return abcList;
    }
}
