package zsy.cn.rx.observe.ap;


/**
 * Created by zsy on 2016/12/10.
 */

public class TestObserver {
    public static void main(String[] arge) {
        Account account = new Account();
        Person person = new Person();

        account.addObserver(person);//把观察者注册到被观察者中

        account.setMoney(1000);
        account.setMoney(2000);
        account.setMoney(9000);
        account.setMoney(10000);

    }
}
