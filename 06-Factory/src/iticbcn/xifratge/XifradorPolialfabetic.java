package iticbcn.xifratge;
// Marc Bertran.
// Fes un xifratge i desxifratge Polialfabetic.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import iticbcn.xifratge.errors.ClauNoSuportada;

public class XifradorPolialfabetic implements Xifrador{

    // Clau i objecte random.
    //private static final Long KEY = (long) 12345;
    private static Random random;

    // Abecedaris, el normal o Base i el permutat.
    private final static char[] ABCCEDARIORIGINAL = "AÁÀÄBCÇDEÉÈËFIÍÌÏGHJKLMNÑOÓÒÖPQUÚÙÜRSTVWXYZ".toCharArray();
    private static char[] abcedariPermutat;

     // Crea l'alfabet permutat per al xifratge. Retorna una char[].
    private void permutaAlfabet() {
        List<Character> abcList = carregarABC();
        Collections.shuffle(abcList, random);

        abcedariPermutat = new char[abcList.size()];
        for (int i = 0; i < abcedariPermutat.length; i++) {
            abcedariPermutat[i] = abcList.get(i);
        }
        
    }

    // Aplica el xifratge o el desxifratge de la cadena.
    //      Si esDeixifre es True, es deixifra la cadena
    //      Si esDeixifre es False, es xifra la cadena.
    private String aplicaPoliAlfa(String cadena, boolean esDeixifre) {
        // Assigna quin es l'alfabet base i quin el codificat per a la traducció.
        //      Funciona depenent de com es crida la funcio. Com s'ha explicat a la capçalera del metode.
        char[] base = ABCCEDARIORIGINAL;
        char[] codificació = new char[1];
        if (esDeixifre) {
            codificació = ABCCEDARIORIGINAL;
        }
        
        StringBuilder resoltat = new StringBuilder();

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            // Permutem l'alfabet per cada lletra.
            permutaAlfabet();

            // Assigna quin es l'alfabet base i quin el codificat per a la traducció.
            //      Funciona com al inici del metode.
            if (esDeixifre) { base = abcedariPermutat; }
            else { codificació = abcedariPermutat; }

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
    private String xifraPoliAlfa(String cadena) {
        return aplicaPoliAlfa(cadena, false);
    }

    // Desxifra la cadena amb xifratge MonoAlfabetic.
    private String desxifraPoliAlfa(String cadena) {
        return aplicaPoliAlfa(cadena, true);
    }

    // Carrega en una List<Character> un char[]
    private List<Character> carregarABC() {
        List<Character> abcList = new ArrayList<>();

        for (char c : ABCCEDARIORIGINAL) {
            abcList.add(c);
        }

        return abcList;
    }

    private void initRandom(Long clau) {
        random = new Random(clau);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            initRandom(Long.parseLong(clau));
            return new TextXifrat(xifraPoliAlfa(msg).getBytes());
        } catch (Exception e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            initRandom(Long.parseLong(clau));
            return desxifraPoliAlfa(xifrat.toString());
        } catch (Exception e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
    }
}