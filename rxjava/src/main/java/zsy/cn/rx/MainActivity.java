package zsy.cn.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zsy.cn.rx.scheduler.SchedulerTest;

public class MainActivity extends AppCompatActivity {

    private String TAG = "RxTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void rxTest(View view) {
        observerFilter();
    }


    public void Test() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.e(TAG, "===create: " + Thread.currentThread().getName());
                        subscriber.onNext("1");
                    }
                })
                //流的数据转换
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        Log.e(TAG, "===String -> Integer: " + Thread.currentThread().getName());
                        return Integer.valueOf(s);
                    }
                })
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(final Integer integer) {
                        Log.e(TAG, "===Integer->Observable: " + Thread.currentThread().getName());
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                Log.e(TAG, "===Observable<String> call: " + Thread.currentThread().getName());
                                for (int i = 0; i < integer; i++) {
                                    subscriber.onNext(i + "");
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .map(new Func1<String, Long>() {
                    @Override
                    public Long call(String s) {
                        Log.e(TAG, "===String->Long: " + Thread.currentThread().getName());
                        return Long.parseLong(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "===onNext: " + Thread.currentThread().getName());
                    }
                });
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
                System.out.println("onNext ::: " + "Observer <" + Thread.currentThread().getName() + ">" + s);

            }
        };
        //签署,订阅
        observable
//                .subscribeOn(Schedulers.newThread())//被观察者运行在子线程//不设置则运行在主线程
//                .observeOn(AndroidSchedulers.mainThread())//观察者运行在主线程,不设置则运行在子线程
                .subscribe(observer);
    }


    private static void observerFilter() {
        MySubscribe mySubscribe = new MySubscribe();

        Observable<String> observable = Observable.create(mySubscribe);

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
                System.out.println("<" + Thread.currentThread().getName() + ">  (" + s + ")");

            }
        };
        System.out.println("observer.toString() == " + observer.toString());
        //签署,订阅
        observable
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        System.out.println("<" + Thread.currentThread().getName() + "> (转换 >>>>>>>> " + s + ")");
                        return s + "   已转换";
                    }
                })
                .throttleFirst(2, TimeUnit.SECONDS)//在这里三秒内响应一次
                .subscribeOn(Schedulers.newThread())//被观察者运行在子线程//不设置则运行在主线程(map方法也会在子线程运行)
                .observeOn(AndroidSchedulers.mainThread())//观察者运行在主线程,不设置则运行在子线程
                .subscribe(observer);
        //

        /*Listener listener = mySubscribe.getListener();
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(1000);
//                System.out.println("被观察者 <" + Thread.currentThread().getName() + "> call:  " + i);
                listener.callLis(i + " 秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
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
        Subscriber subscriber;

        public Listener getListener() {
            return listener;
        }

        Listener listener;

        public MySubscribe() {
            listener = new Listener() {
                @Override
                public void callLis(String s) {
                    if (subscriber != null)
                        subscriber.onNext(s);
                }
            };
        }

        @Override
        public void call(Subscriber<? super String> subscriber) {
            this.subscriber = subscriber;
            //被观察者进行具体操作然后使用观察者subscriber引用进行回调处理
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                    String t = "<" + Thread.currentThread().getName() + "> (" + i + " 秒)";
                    System.out.println(t);
                    subscriber.onNext(i + " 秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
