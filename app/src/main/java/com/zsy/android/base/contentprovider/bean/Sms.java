package com.zsy.android.base.contentprovider.bean;

public class Sms {
	/**
	 * 短信收到是的时间
	 */
	public String date;
	/**
	 * 短信发送人
	 */
	public String address;
	/**
	 * 短信的内容
	 */
	public String body;
	@Override
	public String toString() {
		return "Sms [date=" + date + ", address=" + address + ", body=" + body
				+ "]";
	}

}
