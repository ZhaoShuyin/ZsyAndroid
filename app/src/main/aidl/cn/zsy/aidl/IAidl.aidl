// IAidlInterface.aidl
package cn.zsy.aidl;

// 支持的数据类型
// 1.八种基本数据类型；
// 2.String、CharSequence；
// 3. List、Map，它们中的数据类型也应该是AIDL支持的；
// 4.实现Parcelabel的引用类型

//编译后会生成IAidl.Stub抽象类
//Stub在开启服务时获取的
interface IAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
