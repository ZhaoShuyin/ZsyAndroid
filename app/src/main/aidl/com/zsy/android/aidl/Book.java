package com.zsy.android.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:数据Bean类实现Parcelable接口,在Aidl跨进程通信中使用
 * </p>
 *
 * @author Zsy
 * @date 2019/7/16 14:25
 */

public class Book implements Parcelable {

    public int id;
    public String name;

    public Book() {
    }

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return " <id=" + id + " name=" + name+"> ";
    }

    public void readFromParcel(Parcel dest) {
        this.id = dest.readInt();
        this.name = dest.readString();
    }
}
