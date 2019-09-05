package com.example.assist;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        for (AEnum aEnum : AEnum.values()) {
            System.out.println(aEnum.name);
        }
        AEnum[] values = AEnum.values();
        for (int i = 0; i <values.length; i++) {

        }

    }


    public enum AEnum {
        A("aa"),

        B("bb"),

        C("cc");


        private String name;

        AEnum(String name) {
            this.name = name;
        }


    }

}