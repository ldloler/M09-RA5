public class RotX {
    
    // Variables amb l'abecedari.
    final static char[] MINS = "aáàäbcçdeéèëfiíìïghjklmnñoóòöpquúùürstvwxyz".toCharArray();
    final static char[] MAYUS = "aáàäbcçdeéèëfiíìïghjklmnñoóòöpquúùürstvwxyz".toUpperCase().toCharArray();
    public static void main(String[] args) {
        String[] inicials = {"ABC","xyz","Hola, Mr. calçot","Pedró, per tu què és?"};
        
        System.out.println("XIFRAT\n------------");
        String[] xifrades = new String[4];
        for (int i = 0; i < inicials.length; i++) {
            xifrades[i] = xifraRotX(inicials[i], i*2);
            System.out.printf("(%d)-%s => %s\n", i*2, inicials[i], xifrades[i]);
        }
        System.out.println();

        System.out.println("DESXIFRAT\n------------");

        String[] desxifrades = new String[4];
        for (int i = 0; i < xifrades.length; i++) {
            desxifrades[i] = desxifraRotX(xifrades[i], i*2);
            System.out.printf("(%d)-%s => %s\n", i*2, xifrades[i], desxifrades[i]);
        }
        System.out.println();

        String cadenaXForçar = "Úiüht, úiü wx ùxì ív?";
        System.out.println("Missatge xifrat: " + cadenaXForçar + "\n------------");
        forçaBrutaRotX(cadenaXForçar);
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

    private static String fesRotX(String cadena, int rotacio) {
        StringBuffer resultat = new StringBuffer();
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            boolean esMayu = false;

            // Trobar pos si no la troba a MINS, la busca a MAYUS
            int posChar = trobaIndex(c, MINS);
            if (posChar < 0) {
                posChar = trobaIndex(c, MAYUS);
                esMayu = true;
            }
            // Si no s'ha trobat a MAYUS, retornem el caracter actual.
            if (posChar < 0) {
                resultat.append(c);
                continue;
            }

            // Calcular la nova posicio
            int newPosChar = (posChar + rotacio) % MINS.length;
            if (newPosChar < 0) newPosChar = newPosChar + MINS.length;

            // Guardar, depen de si es Majuscula o Minuscula la lletra pertinent.
            if (esMayu) {
                resultat.append(MAYUS[newPosChar]);
            } else {
                resultat.append(MINS[newPosChar]);
            }
        }
        
        return resultat.toString();
    }

    public static String xifraRotX(String cadena, int rotacio) {
        return fesRotX(cadena, rotacio);
    }

    public static String desxifraRotX(String cadena, int rotacio) {
        return fesRotX(cadena, -rotacio);
    }

    public static void forçaBrutaRotX(String cadenaXifrada) {
        for (int i = 0; i < MINS.length; i++) {
            System.out.printf("(%d) %s\n", i, desxifraRotX(cadenaXifrada, i));
        }
    } 
}