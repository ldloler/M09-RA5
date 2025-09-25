public class Rot13 {
    
    // Variables amb l'abecedari.
    final static char[] MINS = "aáàäbcçdeéèëfiíìïghjklmnñoóòöpquúùürstvwxyz".toCharArray();
    final static char[] MAYUS = "aáàäbcçdeéèëfiíìïghjklmnñoóòöpquúùürstvwxyz".toUpperCase().toCharArray();
    public static void main(String[] args) {
        String[] inicials = {"ABC","xyz","Hola, Mr. calçot","Pedró, per tu què és?"};
        
        System.out.println("XIFRAT\n------------");
        
        String[] xifrades = new String[4];
        for (int i = 0; i < inicials.length; i++) {
            xifrades[i] = xifraRot13(inicials[i]);
            System.out.printf("%s => %s\n", inicials[i], xifrades[i]);
        }
        System.out.println();

        System.out.println("DESXIFRAT\n------------");

        String[] desxifrades = new String[4];
        for (int i = 0; i < xifrades.length; i++) {
            desxifrades[i] = desxifraRot13(xifrades[i]);
            System.out.printf("%s => %s\n", xifrades[i], desxifrades[i]);
        }
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

    public static String xifraRot13(String cadena) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isLowerCase(c)) {
                    resultat += MINS[(trobaIndex(c, MINS)+13)%MINS.length];

                } else if (Character.isUpperCase(c)) {
                    resultat += MAYUS[(trobaIndex(c, MAYUS)+13)%MAYUS.length];
                }
            } else {
                resultat += c;
            }
        }

        return resultat;
    }

    public static String desxifraRot13(String cadena) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isLowerCase(c)) {
                    int index = (trobaIndex(c, MINS)-13);
                    if (index < 0) index = index + MINS.length;
                    resultat += MINS[index%MINS.length];

                } else if (Character.isUpperCase(c)) {
                    int index = (trobaIndex(c, MAYUS)-13);
                    if (index < 0) index = index + MAYUS.length;
                    resultat += MAYUS[index%MAYUS.length];
                }
            } else {
                resultat += c;
            }
        }

        return resultat;
    }
}