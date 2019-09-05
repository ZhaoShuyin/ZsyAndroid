package zsy.cn.rx.scheduler;


import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * RxJava的观察者模式
 * compile 'io.reactivex:rxjava:1.1.6'
 * compile 'io.reactivex:rxandroid:1.2.1'
 */

public class TestRxJava {

    public static void main(String[] args) {

        testRxObserver();           //第一种
        // testRxObserverSimple();    //第二种
        //testRxObserverJust();       //第三种整体数据一次调用
        //testRxObseverFilter();     //第四种
//        testRxObserverFlatMap();       //第五种
    }


    /**
     * 观察者模式,  第一种
     */
    private static void testRxObserver() {
        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {//subscriber 就是注册进来的 观察者
                //被观察者开始发送事件
                subscriber.onNext("aa");
                subscriber.onNext("bb");
                subscriber.onNext("cc");
                //如果发生异常市处理的方法
                subscriber.onError(new Throwable());
                //结束观察的方法
                subscriber.onCompleted();
            }
        });
        //创建观察者
       /* Subscription subscribe = stringObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onNext(String s) {}
        });*/
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted方法调用接收参数为无参");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError方法调用接收参数为" + e);
            }

            @Override
            public void onNext(String s) {
                System.out.println("OnNext方法调用接收参数为" + s);
            }
        };
        //关联观察者和被观察者
        stringObservable.subscribe(observer);
    }

    /**
     * 通过RxJava的简易式观察者写法
     * 通过被观察者调用
     */
    private static void testRxObserverSimple() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Observable.from(list)      //相当于调用三次OnNext
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {//相当于OnNext方法
                        System.out.println(integer);
                    }
                });
    }

    /**
     * 只调一次的数据的方法
     */
    private static void testRxObserverJust() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Observable.just(list)      //相当一次OnNext
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        System.out.println(integers);
                    }
                });
    }

    /**
     * 进行数据过滤处理的方法
     */
    private static void testRxObseverFilter() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Observable.from(list)
                .filter(new Func1<Integer, Boolean>() {//在中间过程中过滤筛选数据
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 3;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    /**
     *一对多转换
     */
    private static void testRxObserverFlatMap() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        List<Integer> list3 = Arrays.asList(7, 8);
        Observable.just(list,list2,list3)      //相当一次OnNext

//                .flatMap()
//                 整合成一个流,由原来的返回三个变成一个Observable对象
                .flatMap(new Func1<List<Integer>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Integer> integers) {
                        return Observable.from(integers);
                    }
                })
                //接收转换后的object
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        System.out.println(o);//在这里输出1,2,3,4,5,6,7,8
                    }
                });

    }

    private  static void testRxObserverFrom() {

    }


}
