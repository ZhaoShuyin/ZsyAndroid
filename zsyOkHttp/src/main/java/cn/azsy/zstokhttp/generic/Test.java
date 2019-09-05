package cn.azsy.zstokhttp.generic;

//import org.json.JSONArray;

/**
 * Created by zsy on 2017/8/8.
 */

public class Test {

    public static <T> void out(T t) {
        System.out.println(t);
    }

    public static void main(String[] args) {
//        out("findingsea");
//        out(123);
//        out(11.11);
//        out(true);
//        Object o = GenericUtils.get(new Dog());
//        Dog dog = (Dog) o;
//        dog.Call();


//        JSONObject Json = new JSONObject();
//        JSONArray JsonArray = new JSONArray();
//
//        Json.put("key", "value");//JSONObject对象中添加键值对
//        JsonArray.add(Json);//将JSONObject对象添加到Json数组中



    }


    public static <T> void out3(T... args) {
        for (T t : args) {
            System.out.println(t);
        }
    }

    public static void main3(String[] args) {
        out3("findingsea", 123, 11.11, true);
    }
   String s = "[{name1:{name2:{name3:'value1','name4':'value2'}}},{}]";

}
