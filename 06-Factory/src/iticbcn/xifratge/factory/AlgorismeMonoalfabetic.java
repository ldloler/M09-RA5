package iticbcn.xifratge.factory;

import iticbcn.xifratge.Xifrador;
import iticbcn.xifratge.XifradorMonoalfabetic;

public class AlgorismeMonoalfabetic extends AlgorismeFactory {

    @Override
    public Xifrador creaXifrador() {
        XifradorMonoalfabetic xifrador = new XifradorMonoalfabetic();
        xifrador.permutaAlfabet();
        return xifrador;
    }

}
