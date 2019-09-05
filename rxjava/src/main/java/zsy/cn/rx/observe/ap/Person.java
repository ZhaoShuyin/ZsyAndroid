package zsy.cn.rx.observe.ap;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zsy on 2016/12/10.
 *  观察者
 */

public class Person implements Observer {


    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof Account){
            Account account = (Account) observable;
            System.out.println("观察到账户变化<"+account.money+">");
        }
    }
}
