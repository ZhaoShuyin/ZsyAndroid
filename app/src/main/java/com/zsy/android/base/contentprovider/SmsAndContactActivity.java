package com.zsy.android.base.contentprovider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zsy.android.R;
import com.zsy.android.base.contentprovider.bean.Contact;
import com.zsy.android.base.contentprovider.bean.Sms;

import java.util.ArrayList;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:通过contentprovider执行短信,联系人操作
 * </p>
 *
 * @author Zsy
 * @date 2019/7/17 13:30
 */

public class SmsAndContactActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_contect);
        textView = findViewById(R.id.tv_sms_contect_show);
    }


    public void querysms(View v) {
        ArrayList<Sms> sms_list = new ArrayList<Sms>();
        //获取contentresolver  内容解析者
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms");
        //通过内容解析者 查询短息数据库 获取 date(短信收到的时间) address(短信发送人) body(短信内容)
        Cursor cursor = resolver.query(uri, new String[]{"date", "address", "body"}, null, null, null);
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String date = cursor.getString(0);
            String address = cursor.getString(1);
            String body = cursor.getString(2);
            Sms sms = new Sms();
            sms.date = date;
            sms.address = address;
            sms.body = body;
            sms_list.add(sms);
            builder.append(sms.toString() + "\n");
        }
        textView.setText(builder.toString());
    }

    public void savesms(View v) {
       /* //获取xml序列化器对象
        XmlSerializer serializer = Xml.newSerializer();
        FileOutputStream fos;
        try {
            fos = openFileOutput("sms.xml", MODE_PRIVATE);
            serializer.setOutput(fos, "utf-8");
            //先写开始标记<?xml version="1.0" encoding="utf-8"?> standalone true说明不依赖其他文档
            serializer.startDocument("utf-8", true);
            //开始写<SmsList>标签  整个文档的根标签
            serializer.startTag(null, "SmsList");
            for (Sms sms : sms_list) {
                //写<Sms>
                serializer.startTag(null, "Sms");

                //写address节点
                serializer.startTag(null, "address");
                serializer.text(sms.address);
                serializer.endTag(null, "address");

                //写body节点
                serializer.startTag(null, "body");
                serializer.text(sms.body);
                serializer.endTag(null, "body");

                //写date节点
                serializer.startTag(null, "date");
                serializer.text(sms.date);
                serializer.endTag(null, "date");

                //写</Sms>
                serializer.endTag(null, "Sms");
            }

            //写</SmsList>标签
            serializer.endTag(null, "SmsList");
            serializer.endDocument();
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

    }


    /**
     * 系统默认的短信应用保存一下，方便后面还原
     */
    private String defaultSmsApp;

    /**
     * https://blog.csdn.net/liu931851190/article/details/78287813
     * 只有设置为短信默认程序才可以插入短信
     *
     * @param v
     */
    public void insertsms(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String myPackageName = getPackageName();
            defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(SmsAndContactActivity.this);
            if (!defaultSmsApp.equals(myPackageName)) {
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName);
                startActivityForResult(intent, 101);
                return;
            }
        }
        //获取内容解析者 contentresolver
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms");
        ContentValues values = new ContentValues();
        values.put("address", "10086");
        values.put("type", 1);
        values.put("date", System.currentTimeMillis());
        values.put("body", "短信信息");
        Uri insert = resolver.insert(uri, values);
        textView.setText("新建短信 result=" + insert);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            //设置默认短信成功
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                final String myPackageName = getPackageName();
                if (Telephony.Sms.getDefaultSmsPackage(SmsAndContactActivity.this).equals(myPackageName)) {
                    recoverySms();
                }
            }
        }
    }

    private void recoverySms() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String myPackageName = getPackageName();
            if (Telephony.Sms.getDefaultSmsPackage(SmsAndContactActivity.this)
                    .equals(myPackageName)) {
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                //这里的defaultSmsApp是前面保存的
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, defaultSmsApp);
                startActivity(intent);
            }
        }
    }

    /**
     * 获取手机所有联系人
     * 需要<>android.permission.READ_CONTACTS</>权限
     */
    public void querycontact(View v) {
        ArrayList<Contact> contact_list = new ArrayList<Contact>();
        ContentResolver resolver = this.getContentResolver();
        //raw_contacts表对应的uri
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        //data表对应的uri(contact_id 值去data表中查询对应的联系人数据)
        Uri data_uri = Uri.parse("content://com.android.contacts/data");
        //通过内容解析者查询 raw_contacts表 获取 contact_id值
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        int columnCount = cursor.getColumnCount();
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String contact_id = cursor.getString(0);
            if (contact_id == null) {
                continue;
            }
            builder.append(" ************************** \n");
            builder.append(" {  ");
            for (int i = 0; i < columnCount; i++) {
                String columnName = cursor.getColumnName(i);
                String string = cursor.getString(i);
                builder.append(" < " + columnName + "=" + string + " > ");
            }
            builder.append(" --> ");
            String selection = "raw_contact_id =?";
            String[] selectionArgs = new String[]{contact_id};
            //筛选项
            String[] projection = {"mimetype", "data1"};
            Cursor data_cursor = resolver.query(data_uri, projection, selection, selectionArgs, null);
            int count = data_cursor.getColumnCount();
            while (data_cursor.moveToNext()) {
                for (int j = 0; j < count; j++) {
                    String columnName = data_cursor.getColumnName(j);
                    String string = data_cursor.getString(j);
                    builder.append(" (" + columnName + "=" + string + ") ");
                }
            }
            builder.append(" } \n");
            builder.append(" ******************************** \n");
        }
        textView.setText(builder.toString());
    }


    /**
     * 新建联系人
     * 需要<>android.permission.WRITE_CONTACTS</>权限
     *
     * @param v
     */
    public void insertContect(View v) {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri data_uri = Uri.parse("content://com.android.contacts/data");

        // 先确定要插入到raw_contacts contact_id的值
        // 首先查询表 看有多少个返回值 把返回值数目+1 就是要插入的具体数据
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        // 获取cursor返回的行数目 +1
        int count = cursor.getCount() + 1;
        ContentValues contact_id = new ContentValues();
        contact_id.put("contact_id", count);
        resolver.insert(uri, contact_id);

        // 插入跟姓名相关的一行记录
        ContentValues name_values = new ContentValues();
        name_values.put("raw_contact_id", count);
        name_values.put("data1", "王五");
        name_values.put("mimetype", "vnd.android.cursor.item/name");
        // 操作data表
        resolver.insert(data_uri, name_values);

        // 插入跟电话相关的一行记录
        ContentValues phone_values = new ContentValues();
        phone_values.put("raw_contact_id", count);
        phone_values.put("data1", "10086");
        phone_values.put("mimetype", "vnd.android.cursor.item/phone_v2");
        // 操作data表
        resolver.insert(data_uri, phone_values);

        // 插入email相关的一行记录
        ContentValues email_values = new ContentValues();
        email_values.put("raw_contact_id", count);
        email_values.put("data1", "abc@abc.com");
        email_values.put("mimetype", "vnd.android.cursor.item/email_v2");
        // 操作data表
        resolver.insert(data_uri, email_values);

        // 插入跟姓名相关的一行记录
        ContentValues address_values = new ContentValues();
        address_values.put("raw_contact_id", count);
        address_values.put("data1", "地址信息");
        address_values.put("mimetype", "vnd.android.cursor.item/postal-address_v2");
        // 操作data表
        resolver.insert(data_uri, address_values);
    }

}
