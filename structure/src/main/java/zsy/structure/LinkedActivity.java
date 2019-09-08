package zsy.structure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import zsy.structure.view.LinkedView;

/**
 * Title 链表数据结构
 *
 * @author Zsy
 * @date 2019/9/8.
 */

public class LinkedActivity extends Activity {

    EditText editText;
    LinkedView linkedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked);
        editText = findViewById(R.id.et_linked);
        linkedView = findViewById(R.id.linkedview);
        linkedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedView.invalidate();
                Toast.makeText(LinkedActivity.this, "刷新", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertLinked(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        int integer = Integer.valueOf(s);
        linkedView.insertNode(integer);
    }

    public void addLinked(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        int integer = Integer.valueOf(s);
        linkedView.addLinked(integer);
    }

    public void findLinked(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        int integer = Integer.valueOf(s);
        linkedView.findLinked(integer);
    }

    public void delLinked(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        int integer = Integer.valueOf(s);
        linkedView.delLinked(integer);
    }


    //1
    public void a(View view) {
        linkedView.addLinked(1);
    }

    //2
    public void b(View view) {
        linkedView.addLinked(2);
    }

    //3
    public void c(View view) {
        linkedView.addLinked(3);
    }

    //4
    public void d(View view) {
        linkedView.addLinked(4);
    }

    //5
    public void e(View view) {
        linkedView.addLinked(5);
    }

    //6
    public void f(View view) {
        linkedView.addLinked(6);
    }

    //7
    public void g(View view) {
        linkedView.addLinked(7);
    }

    //8
    public void h(View view) {
        linkedView.addLinked(8);
    }

    //9
    public void i(View view) {
        linkedView.addLinked(9);
    }

    //10
    public void j(View view) {
        linkedView.addLinked(10);
    }
    
}
