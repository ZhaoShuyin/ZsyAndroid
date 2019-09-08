package zsy.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import zsy.myapp.view.RedBlackTreeView;

/**
 * Created by Zsy on 2019/9/5.
 */

public class RedBlackActivity extends Activity {

    EditText editText;
    RedBlackTreeView treeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_black);
        treeView = findViewById(R.id.rb_view);
        editText = findViewById(R.id.et_rb);
        treeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RedBlackActivity.this, "刷新", Toast.LENGTH_LONG).show();
                treeView.invalidate();
            }
        });
    }

    public void prev(View view) {
        treeView.preOrder();
    }

    public void mid(View view) {
        treeView.inOrder();
    }

    public void sub(View view) {
        treeView.postOrder();
    }


    public void addNode(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        int integer = Integer.valueOf(s);
        treeView.addNode(integer);
    }


    public void findNode(View view){
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        int integer = Integer.valueOf(s);
        treeView.find(integer);
    }

    public void delNode(View view){
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        int integer = Integer.valueOf(s);
        treeView.deleteNode(integer);
    }

    //1
    public void a(View view) {
       treeView.addNode(1);
    }

    //2
    public void b(View view) {
       treeView.addNode(2);
    }

    //3
    public void c(View view) {
       treeView.addNode(3);
    }

    //4
    public void d(View view) {
       treeView.addNode(4);
    }

    //5
    public void e(View view) {
       treeView.addNode(5);
    }

    //6
    public void f(View view) {
       treeView.addNode(6);
    }

    //7
    public void g(View view) {
       treeView.addNode(7);
    }

    //8
    public void h(View view) {
       treeView.addNode(8);
    }

    //9
    public void i(View view) {
       treeView.addNode(9);
    }

    //10
    public void j(View view) {
       treeView.addNode(10);
    }
}
