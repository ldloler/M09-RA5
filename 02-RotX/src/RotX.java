public class RotX {
    
    // Variables amb l'abecedari.
    final static char[] MINS = "aáàäbcçdeéèëfiíìïghjklmnñoóòöpquúùürstvwxyz".toCharArray();
    final static char[] MAYUS = "aáàäbcçdeéèëfiíìïghjklmnñoóòöpquúùürstvwxyz".toUpperCase().toCharArray();
    public static void main(String[] args) {
        String[] inicials = {"ABC","xyz","Hola, Mr. calçot","Pedró, per tu què és?"};
        
        System.out.println("XIFRAT\n------------");
        
        String[] xifrades = new String[4];
        for (int i = 0; i < inicials.length; i++) {
            xifrades[i] = xifraRotX(inicials[i], 2);
            System.out.printf("%s => %s\n", inicials[i], xifrades[i]);
        }
        System.out.println();

        System.out.println("DESXIFRAT\n------------");

        String[] desxifrades = new String[4];
        for (int i = 0; i < xifrades.length; i++) {
            desxifrades[i] = desxifraRotX(xifrades[i], 2);
            System.out.printf("%s => %s\n", xifrades[i], desxifrades[i]);
        }

        forçaBrutaRotX(xifrades[3]);
    }

    // Retorna la possicio d'un char en un char[]
    //      si no el troba retorna -1.
    public static int trobaIndex(char c, char[] abecedari) {
        for (int i = 0; i < abecedari.length; i++) {
            if (abecedari[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static String xifraRotX(String cadena, int rotacio) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isLowerCase(c)) {
                    resultat += MINS[(trobaIndex(c, MINS)+rotacio)%MINS.length];

                } else if (Character.isUpperCase(c)) {
                    resultat += MAYUS[(trobaIndex(c, MAYUS)+rotacio)%MAYUS.length];
                }
            } else {
                resultat += c;
            }
        }

        return resultat;
    }

    public static String desxifraRotX(String cadena, int rotacio) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isLowerCase(c)) {
                    int index = (trobaIndex(c, MINS)-rotacio);
                    if (index < 0) index = index + MINS.length;
                    resultat += MINS[index%MINS.length];

                } else if (Character.isUpperCase(c)) {
                    int index = (trobaIndex(c, MAYUS)-rotacio);
                    if (index < 0) index = index + MAYUS.length;
                    resultat += MAYUS[index%MAYUS.length];
                }
            } else {
                resultat += c;
            }
        }

        return resultat;
    }

    public static void forçaBrutaRotX(String cadenaXifrada) {
        for (int i = 0; i <= 28; i++) {
            System.out.printf("(%d) %s\n", i, desxifraRotX(cadenaXifrada, i));
        }
    }
}