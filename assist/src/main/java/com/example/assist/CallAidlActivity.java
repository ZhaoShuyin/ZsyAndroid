package com.example.assist;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.zsy.android.Book;

import com.zsy.android.aidl.Book;
import com.zsy.android.aidl.IBookManager;

import java.util.List;

import cn.zsy.aidl.IAidl;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:其他应用通过Aidl操作
 * </p>
 * 参考1.https://www.2cto.com/kf/201610/560397.html (Parcelable数据Bean使用)
 * 2.https://www.jianshu.com/p/ddbb40c7a251 (in out inout关键字)
 *
 * @author Zsy
 * @date 2019/7/16 9:41
 */

public class CallAidlActivity extends Activity {

    private TextView tvShow;
    private IAidl iService;
    private IBookManager bookManager;
    private EditText etId;
    private EditText etName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_aidl);
        tvShow = findViewById(R.id.tv_aidl_show);
        etId = findViewById(R.id.et_aidl_id);
        etName = findViewById(R.id.et_aidl_name);
    }


    public void initAidlSerice(View view) {
        Intent service = new Intent();
        service.setAction("cn.zsy.aidl");
        service.setPackage("com.zsy.android");
        boolean bindService = bindService(service, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iService = IAidl.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
        tvShow.setText(System.currentTimeMillis() + "\n简单初始化 - " + bindService);
    }


    public void aidlcall(View v) {
        try {
            iService.basicTypes(1, 2, true, 3, 4, "哈哈");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void initBookService(View view) {
        Intent service = new Intent();
        service.setAction("cn.zsy.aidl.book");
        service.setPackage("com.zsy.android");
        boolean b = bindService(service, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bookManager = IBookManager.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        }, BIND_AUTO_CREATE);
        tvShow.setText(System.currentTimeMillis() + "\nParcelable初始化 - " + b);
    }


    public void in_in(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addInInBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void in_out(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addInOutBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void in_inout(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addInInOutBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void out_in(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addOutInBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void out_out(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addOutOutBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void out_inout(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addOutInOutBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void inout_in(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addInoutInBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void inout_out(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addInoutOutBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void inout_inout(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "need init", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Book book = getBook();
            Book bookResult = bookManager.addInoutInOutBook(book);
            tvShow.setText("in== " + book.toString() + "\n" +
                    "result== " + bookResult.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    int i = 0;
    private Book getBook() {
//        String s1 = etId.getText().toString();
//        String s2 = etName.getText().toString();
//        int id = Integer.valueOf(s1.length() == 0 ? "0" : s1);
        i+=1;
        Book book = new Book(i, ""+i);
        return book;
    }


    public void getAidl(View view) {
        if (bookManager == null) {
            Toast.makeText(this, "请先初始化", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            List<Book> bookList = bookManager.getBookList();
            tvShow.setText(bookList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
