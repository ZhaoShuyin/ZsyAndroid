// IBookManager.aidl
package com.zsy.android.aidl;
import com.zsy.android.aidl.Book;
// Declare any non-default types here with import statements

//服务端,被调用者
interface IBookManager {
       //
       Book addInInBook(in Book book);
       //
       Book addInOutBook(out Book book);
       //
       Book addInInOutBook(inout Book book);
       //
       Book addOutInBook(in Book book);
       //
       Book addOutOutBook(out Book book);
       //
       Book addOutInOutBook(inout Book book);
       //
       Book addInoutInBook(in Book book);
       //
       Book addInoutOutBook(out Book book);
       //
       Book addInoutInOutBook(inout Book book);
       //
       List<Book> getBookList();
}
