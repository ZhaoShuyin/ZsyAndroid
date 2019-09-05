package zsy.cn.rx.scheduler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

//import butterknife.BindView;
//import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 调度器和防止重复点击
 */

public class RxScheduler extends Activity {
//    @BindView(R.id.tv_rxtest)
    TextView tvRxtest;
//    @BindView(R.id.bt_bt)
    Button btBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rx);
//        ButterKnife.bind(this);

       // scheduleMethod();

        preventRepeat();
    }


    /**
     * 调度器的方法
     */
    private void scheduleMethod() {
        Observable.just(Environment.getExternalStorageDirectory().getAbsolutePath() + "cyx.jpg")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        Bitmap bitmap = BitmapFactory.decodeFile(s);
                        System.out.println("Observable" + Thread.currentThread().getName());
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())// 执行在子线程()

                .observeOn(AndroidSchedulers.mainThread())//执行在主线程
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        System.out.println("call" + Thread.currentThread().getName());
                        tvRxtest.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                });
    }

    /**
     * 按钮防止重复点击的方法
     */
    private void preventRepeat() {
        Observable.create(new MyOnSubscribe(btBt))

                .throttleFirst(2, TimeUnit.SECONDS)//在这里三秒内响应一次

                //注册 观察者
                .subscribe(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        System.out.println("点击了按钮,并响应了");
                    }
                });
    }

    /**
     *
     */
    class MyOnSubscribe implements Observable.OnSubscribe<View> {
        private Subscriber subscriber;

        public MyOnSubscribe(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("点击了按钮");
                    subscriber.onNext(view);

                }
            });
        }

        @Override
        public void call(Subscriber<? super View> subscriber) {
            this.subscriber = subscriber;
        }
    }


}
