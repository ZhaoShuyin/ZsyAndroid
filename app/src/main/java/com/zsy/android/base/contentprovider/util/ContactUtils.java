package com.zsy.android.base.contentprovider.util;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.zsy.android.base.contentprovider.bean.Contact;

public class ContactUtils {

	/**
	 * 查询联系人表返回集合
	 * @param context
	 * @return
	 */
	public static ArrayList<Contact> getContacts(Context context){
		ArrayList<Contact> contact_list = new ArrayList<Contact>();
		ContentResolver resolver = context.getContentResolver();
		//raw_contacts表对应的uri
		Uri uri= Uri.parse("content://com.android.contacts/raw_contacts");
		//data表对应的uri
		Uri data_uri= Uri.parse("content://com.android.contacts/data");
		//通过内容解析者查询 raw_contacts表 获取 contact_id值
		Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
		while (cursor.moveToNext()) {
			//每一个contact_id值对应一组联系人数据
			String contact_id = cursor.getString(0);
			if(contact_id == null){
				continue;
			}
			System.out.println("contact_id="+contact_id);

			String selection = "raw_contact_id =?";
			String[] selectionArgs = new String[]{contact_id};
//			Cursor cursor2 = resolver.query(data_uri, null, selection, selectionArgs, null);
//			for( int i = 0;i<cursor2.getColumnCount();i++){
//				String name = cursor2.getColumnName(i);
//				if(name.startsWith("mime")){
//					System.out.println(name);
//				}
//			}
			//创建联系人对象
			Contact contact = new Contact();
			//拿着查询到的contact_id 值去data表中查询对应的联系人数据
			Cursor data_cursor = resolver.query(data_uri, new String[]{"mimetype","data1"}, selection, selectionArgs, null);
			while (data_cursor.moveToNext()) {
				//如果mimetype 返回是email_v2 说明data中保存的是email
				String mimetype = data_cursor.getString(0);
				String data = data_cursor.getString(1);
				if("vnd.android.cursor.item/email_v2".equals(mimetype)){
					contact.email = data;
				}else if("vnd.android.cursor.item/name".equals(mimetype)){
					contact.name = data;
				}else if("vnd.android.cursor.item/postal-address_v2".equals(mimetype)){
					contact.address = data;
				}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
					contact.phone = data;
				}
				System.out.println("mimetype="+mimetype+"====data="+data);
			}
			//把联系人对象添加到集合中
			contact_list.add(contact);
		}
		return contact_list;
	}
}
