package cn.azsy.zstokhttp.customswipe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zsy on 2017/6/23.
 */

public class Test {

        public static void main(String[] args) {
            Timer timer = new Timer();// 实例化Timer类
            timer.schedule(new TimerTask() {
                public void run() {
                    System.out.println("退出");
                    this.cancel();
                }
            }, 15000);// 这里百毫秒
            System.out.println("本程序存在15秒后自动退出");
        }

}
