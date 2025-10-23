package iticbcn.xifratge.factory;

import iticbcn.xifratge.Xifrador;
import iticbcn.xifratge.XifradorAES;

public class AlgorismeAES  extends AlgorismeFactory{

    @Override
    public Xifrador creaXifrador() {
        return new XifradorAES();
    }

}
