package zsy.cn.rx.stream;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Created by Zsy on 2019/3/10 9:14
 * 流Stream转换数据只能使用一次便会释放
 * IllegalStateException: stream has already been operated upon or closed
 */
public class RXStream {

    @TargetApi(Build.VERSION_CODES.N)
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
//        List<Integer> list = Arrays.asList(6, 7, 8, 9, 10);
       /* for (int i = 0; i < list.size(); i++) {
            System.out.println("原始数据" + list.get(i));
        }*/
//        filterA(list);

        map(list);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static void map(List<Integer> list) {
        Stream<Integer> stream = list.stream();
        Stream<String> stringStream = stream.map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return "apply方法转换:" + String.valueOf(integer * 2);
            }
        });
        Object[] objects = stringStream.toArray();
        for (int i = 0; i < objects.length; i++) {
            System.out.println("toArray " + objects[i]);
        }
        stringStream.forEach(new Consumer<String>() {
            @Override
            public void accept(String string) {
                System.out.println(string);
            }
        });
//        stringStream.forEach((string) -> System.out.println(string));
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static void filterA(List<Integer> list) {
        //把数据源转换为流
        Stream<Integer> stream = list.stream();
        Stream<Integer> integerStream = stream.filter(new Predicate<Integer>() {
            //传入的为数据而非索引
            @Override
            public boolean test(Integer integer) {
                boolean b = integer % 2 == 0;
                System.out.println("第" + integer + "个为 " + b);
                return b;
            }
        });
        integerStream.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("通过流过滤为偶数->" + integer);
            }
        });

        //Lamda表达式简写
        list.stream().filter(i -> i % 2 == 0).forEach(i -> System.out.println(i));
    }
}
