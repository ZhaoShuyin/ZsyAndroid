package com.zsy.socket;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.zsy.socket.netty.SocketClient;
import com.zsy.socket.util.DataUtil;
import com.zsy.zlib.view.ConsoleView;

/**
 * @Title com.zsy.socket
 * @date 2021/4/16
 * @autor Zsy
 */

public class SocketActivity extends Activity {

    private ConsoleView consoleView;

    private SocketClient socketClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        consoleView = findViewById(R.id.console_view);
        socketClient = new SocketClient();
    }

    public void connect(View view) {
        socketClient.connect("39.101.177.253", 9000, new SocketClient.ClientListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void message(String message) {
                consoleView.show(message);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void senddata(View view) {
        byte[] bytes = DataUtil.getBytes();
        boolean b = socketClient.sendBytes(bytes);
        consoleView.show("发送数据:" + bytes.length + " ==> " + b);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void disconnect(View view) {
//        socketClient.disConnect();
        consoleView.show("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");
    }
}
