package zsy.cn.rx.lamda;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class Lamda {

    public static void main(String[] args) {
        LamdaA();
//        LamdaB();
//        LamdaC();
    }

    // 使用Lamda表达式使用内部类
    public static void LamdaA() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable");
            }
        }).start();

        new Thread(() -> System.out.println("LamdaA")) {
        }.start();
    }

    //
    public static void LamdaB() {
        MyListenerA a = new MyListenerA();
        a.addLisenter(new ListenerB() {
            @Override
            public void onLisenter(String s) {
                System.out.println(s);
            }
        });
        a.bbb("a调用接口回调,call,执行被实现的方法");

        a.addLisenter((s) -> System.out.println("lamda: " + s));
//        a.addLisenter((String s) -> System.out.println("lamda: " + s));
        a.bbb("lamda传入的内部类及执行实现方法,\na调用接口回调,call执行");
        //

        a.setLisenter(new ListenerC() {
            @Override
            public String onLisenter(String s) {
                String res = s + " \n使用普通内部类获取参数";
                return res;
            }
        });
        System.out.println(a.ccc("ccc"));

        //返回值简单的话可以直接写
        a.setLisenter((String s) -> s + "\n这是通过Lamda实现的内部类处理的返回值");
        System.out.println(a.ccc("ccc"));
        //内部类复杂方法返回值的写在 { } 中
        a.setLisenter((String s) -> {
            String Res = s + "\n这是通过Lamda实现的内部类复杂的方法处理的返回值";
            return Res;
        });
        System.out.println(a.ccc("ccc"));
        Context context = null;

        new View(context).setOnClickListener((view) -> Toast.makeText(context, "内部类方法", Toast.LENGTH_SHORT).show());
    }

    //Lamda表达式的方法引用
    //当某个方法的传入参数和返回参数和内部类一致时,可以使用方法引用
    public static void LamdaC() {
        MyListenerA a = new MyListenerA();
        a.setLisenter(new ListenerC() {
            @Override
            public String onLisenter(String s) {
                String res = s + " \n使用普通内部类ListenerC获取参数";
                return res;
            }
        });
        System.out.println(a.ccc("ccc: "));

        a.setLisenter(Lamda::reference);
        System.out.println(a.ccc("ccc: "));
    }

    public static String reference(String s) {
        String res = s + "\n 这是可以被引用的方法执行的返回值";
        return res;
    }

    /**
     *
     */
    static class MyListenerA {
        ListenerB b;
        ListenerC c;

        public void addLisenter(ListenerB b) {
            this.b = b;
        }

        public void setLisenter(ListenerC c) {
            this.c = c;
        }

        public void bbb(String s) {
            if (b != null)
                b.onLisenter(s);
        }

        public String ccc(String s) {
            if (c != null) {
                String s1 = c.onLisenter(s);
                return s1;
            }
            return "null";
        }

    }

    /**
     * 回调内部类(使用lamda表达式时只能有一个内部方法)
     */
    public interface ListenerB {
        void onLisenter(String s);
    }

    public interface ListenerC {
        String onLisenter(String s);
    }
}
