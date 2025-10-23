package iticbcn.xifratge.factory;

import iticbcn.xifratge.Xifrador;
import iticbcn.xifratge.XifradorPolialfabetic;

public class AlgorismePolialfabetic  extends AlgorismeFactory{

    @Override
    public Xifrador creaXifrador() {
        return new XifradorPolialfabetic();
    }

}
