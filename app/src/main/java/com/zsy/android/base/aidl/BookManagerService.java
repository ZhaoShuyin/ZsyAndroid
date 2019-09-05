package com.zsy.android.base.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zsy.android.aidl.Book;
import com.zsy.android.aidl.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Title: ZsyAndroid
 * <p>
 * Description:服务端的服务类,实现远程被调用后的操作
 * </p>
 *
 * @author Zsy
 * @date 2019/7/16 11:25
 */
public class BookManagerService extends Service {
    private CopyOnWriteArrayList list = new CopyOnWriteArrayList();
    private IBinder mBinder = new IBookManager.Stub() {

        @Override
        public Book addInInBook(Book book) throws RemoteException {
            book.name = book.name + " in-in";
            list.add(book);
            return book;
        }

        @Override
        public Book addInOutBook(Book book) throws RemoteException {
            book.name = book.name + " in-out";
            list.add(book);
            return book;
        }

        @Override
        public Book addInInOutBook(Book book) throws RemoteException {
            book.name = book.name + " in-inout";
            list.add(book);
            return book;
        }

        @Override
        public Book addOutInBook(Book book) throws RemoteException {
            book.name = book.name + " out-in";
            list.add(book);
            return book;
        }

        @Override
        public Book addOutOutBook(Book book) throws RemoteException {
            book.name = book.name + " out-out";
            list.add(book);
            return book;
        }

        @Override
        public Book addOutInOutBook(Book book) throws RemoteException {
            book.name = book.name + " out-inout";
            list.add(book);
            return book;
        }

        @Override
        public Book addInoutInBook(Book book) throws RemoteException {
            book.name = book.name + " inout-in";
            list.add(book);
            return book;
        }

        @Override
        public Book addInoutOutBook(Book book) throws RemoteException {
            book.name = book.name + " inout-out";
            list.add(book);
            return book;
        }

        @Override
        public Book addInoutInOutBook(Book book) throws RemoteException {
            book.name = book.name + " inout-inout";
            list.add(book);
            return book;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return list;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("aidltest", "BookManagerService --onCreate: ");
    }
}
