package zsy.cn.rx.scheduler;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import rx.Observable;

import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Zsy on 2019/3/10 11:24
 * 调度者
 */
public class SchedulerTest {

    public static void main(String[] args) {

        observer();

//        observerFilter();
    }

    private static void observerFilter() {
        MySubscribe mySubscribe = new MySubscribe();

        Observable<String> lisObservable = Observable.create(mySubscribe);

        lisObservable.throttleFirst(2, TimeUnit.SECONDS);
        lisObservable.subscribeOn(Schedulers.newThread());
//        lisObservable.observeOn(Schedulers.computation());
        lisObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("  观察者 <" + Thread.currentThread().getName() + "> onNext:  " + s);
            }
        });
        //

        Listener listener = mySubscribe.getListener();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
                System.out.println("被观察者 <" + Thread.currentThread().getName() + "> call:  " + i);
                listener.callLis(i + " 秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();*/
    }

    static interface Listener {
        void callLis(String s);
    }

    static class MySubscribe implements Observable.OnSubscribe<String> {
        public Listener getListener() {
            return listener;
        }

        Listener listener;

        @Override
        public void call(Subscriber<? super String> subscriber) {
            listener = new Listener() {
                @Override
                public void callLis(String s) {
                    System.out.println("Subscribe <" + Thread.currentThread().getName() + "> call:  " + s);
                    subscriber.onNext(s);
                }
            };
        }
    }

    private static void observer() {
        //创建订阅器
        Observable.OnSubscribe<String> call = new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //被观察者进行具体操作然后使用观察者subscriber引用进行回调处理
                System.out.println("Subscribe <" + Thread.currentThread().getName() + "> call:  ");
                subscriber.onNext("call");
            }
        };
        //被观察者
        Observable<String> observable = Observable.create(call);
        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String s) {
                //运行在主线程
                System.out.println("onNext ::: " + "Observer <" + Thread.currentThread().getName() + ">" + s);

            }
        };
        //签署,订阅
        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.computation())
                .subscribe(observer);
    }


}
