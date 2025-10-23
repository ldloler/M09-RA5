package iticbcn.xifratge;

import iticbcn.xifratge.errors.ClauNoSuportada;

public interface Xifrador {

    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada;

    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada;
}
