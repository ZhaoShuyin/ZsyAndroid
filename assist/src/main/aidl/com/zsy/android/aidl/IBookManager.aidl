// IBookManager.aidl
package com.zsy.android.aidl;
import com.zsy.android.aidl.Book;
// Declare any non-default types here with import statements

//客服端,调用者
interface IBookManager {
    //
    Book addInInBook(in Book book);
    //
    Book addInOutBook(in Book book);
    //
    Book addInInOutBook(in Book book);
    //
    Book addOutInBook(out Book book);
    //
    Book addOutOutBook(out Book book);
    //
    Book addOutInOutBook(out Book book);
    //
    Book addInoutInBook(inout Book book);
    //
    Book addInoutOutBook(inout Book book);
    //
    Book addInoutInOutBook(inout Book book);
    //
    List<Book> getBookList();
}
