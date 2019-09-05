package com.zsy.test;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/31 14:20
 */

public class Test {

    public static void main(String[] args) {

        A a = new A();
        B b = new B();
        A ba = new B();
        System.out.println("1-------------");
        System.out.println(A.class.isAssignableFrom(a.getClass()));
        System.out.println(B.class.isAssignableFrom(b.getClass()));
        System.out.println(A.class.isAssignableFrom(b.getClass()));
        System.out.println(B.class.isAssignableFrom(a.getClass()));
        System.out.println(A.class.isAssignableFrom(ba.getClass()));
        System.out.println(B.class.isAssignableFrom(ba.getClass()));
        System.out.println("2-------------");
        System.out.println(a.getClass().isAssignableFrom(A.class));
        System.out.println(b.getClass().isAssignableFrom(B.class));
        System.out.println(a.getClass().isAssignableFrom(B.class));
        System.out.println(b.getClass().isAssignableFrom(A.class));
        System.out.println(ba.getClass().isAssignableFrom(A.class));
        System.out.println(ba.getClass().isAssignableFrom(B.class));
        System.out.println("3-------------");
        System.out.println(Object.class.isAssignableFrom(b.getClass()));
        System.out.println(Object.class.isAssignableFrom("abc".getClass()));
        System.out.println("4-------------");
        System.out.println("a".getClass().isAssignableFrom(Object.class));
        System.out.println("abc".getClass().isAssignableFrom(Object.class));
    }


    static class A {
    }

    static class B extends A {
    }

}
