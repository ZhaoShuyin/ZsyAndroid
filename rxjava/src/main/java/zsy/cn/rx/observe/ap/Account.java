package zsy.cn.rx.observe.ap;

import java.util.Observable;

/**
 * 被观察者.
 */

public class Account extends Observable {

    public int money = 1;

    public void  setMoney(int money){
        this.money = money;
        setChanged();   //需要设置chang为true
        notifyObservers();
    };


}
