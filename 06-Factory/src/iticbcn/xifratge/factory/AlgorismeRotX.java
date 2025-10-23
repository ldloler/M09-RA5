package iticbcn.xifratge.factory;

import iticbcn.xifratge.Xifrador;
import iticbcn.xifratge.XifradorRotX;

public class AlgorismeRotX  extends AlgorismeFactory{

    @Override
    public Xifrador creaXifrador() {
        return new XifradorRotX();
    }

}
