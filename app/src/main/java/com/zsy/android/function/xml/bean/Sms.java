package com.zsy.android.function.xml.bean;

public class Sms {
	//发送短信的号码
	public String from;
	//短信内容
	public String content;
	//收到短信的时间
	public String date;
	@Override
	public String toString() {
		return "Sms [from=" + from + ", content=" + content + ", date=" + date
				+ "]";
	}
}
