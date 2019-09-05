package com.zsy.android.function.xml.bean;

public class Book {
	public String title;
	public String author;
	public String price;
	@Override
	public String toString() {
		return "{ Book title=" + title + ", author=" + author + ", price="+ price + " }";
	}
	
}
