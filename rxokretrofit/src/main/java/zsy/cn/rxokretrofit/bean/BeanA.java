package zsy.cn.rxokretrofit.bean;

/**
 * Created by Zsy on 2019/3/10 17:33
 */
public class BeanA
{
    /**
     * code : 200
     * state : succes
     */

    private int code;
    private String state;
    /**
     * name : zhangsan
     * age : 18
     * type : get
     */

    private String name;
    private String age;
    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BeanA{" +
                "code=" + code +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
