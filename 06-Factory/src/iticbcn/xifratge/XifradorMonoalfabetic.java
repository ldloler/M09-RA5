package iticbcn.xifratge;

import java.util.List;

import iticbcn.xifratge.errors.ClauNoSuportada;

import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {

    // Abecedaris, el normal o Base i el permutat.
    private final char[] ABCORIGINAL = "AÁÀÄBCÇDEÉÈËFIÍÌÏGHJKLMNÑOÓÒÖPQUÚÙÜRSTVWXYZ".toCharArray();
    private final char[] ABC = permutaAlfabet();

    // Aplica el xifratge o el desxifratge de la cadena.
    // Envia el char 'D' a <metode> per Desxifrar la cadena
    // qualsevol altre caracter xifrará la cadena.
    private String aplicaMonoAlfa(String cadena, char metode) {
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
                    } else if (Character.toLowerCase(charBase) == c) {
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
    private String xifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, 'X');
    }

    // Desxifra la cadena amb xifratge MonoAlfabetic.
    private String desxifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, 'D');
    }

    // Crea l'alfabet permutat per al xifratge. Retorna una char[].
    public char[] permutaAlfabet() {
        List<Character> abcList = carregarABC();
        Collections.shuffle(abcList);

        char[] resoltat = new char[abcList.size()];
        for (int i = 0; i < resoltat.length; i++) {
            resoltat[i] = abcList.get(i);
        }

        return resoltat;
    }

    // Carrega en una List<Character> un char[]
    private List<Character> carregarABC() {
        List<Character> abcList = new ArrayList<>();

        for (char c : ABCORIGINAL) {
            abcList.add(c);
        }

        return abcList;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {

        if (clau != null) throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");

        return new TextXifrat(xifraMonoAlfa(msg).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {

        if (clau != null) throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");

        return desxifraMonoAlfa(xifrat.toString());
    }
}
